package com.example.libraryrest.services;

import com.example.libraryrest.dto.requests.AuthorRequest;
import com.example.libraryrest.dto.requests.BookFilterRequest;
import com.example.libraryrest.dto.requests.BookRequest;
import com.example.libraryrest.dto.requests.PaginationRequest;
import com.example.libraryrest.dto.requests.SortRequest;
import com.example.libraryrest.dto.requests.UpdateAmountRequest;
import com.example.libraryrest.dto.requests.UpdatePublisherRequest;
import com.example.libraryrest.dto.requests.UpdateStatusRequest;
import com.example.libraryrest.dto.requests.UpdateYearRequest;
import com.example.libraryrest.dto.responses.BookFilterResponse;
import com.example.libraryrest.dto.responses.BookResponse;
import com.example.libraryrest.enums.Status;
import com.example.libraryrest.exceptions.BookAlreadyExistsException;
import com.example.libraryrest.exceptions.BookInactiveException;
import com.example.libraryrest.exceptions.BookNotFoundException;
import com.example.libraryrest.exceptions.InvalidDeactivationReasonException;
import com.example.libraryrest.exceptions.NoSuchGenreException;
import com.example.libraryrest.mappers.AuthorMapper;
import com.example.libraryrest.mappers.BookMapper;
import com.example.libraryrest.mappers.GenreMapper;
import com.example.libraryrest.models.Author;
import com.example.libraryrest.models.Book;
import com.example.libraryrest.models.DeactivationReason;
import com.example.libraryrest.repositories.AuthorDAO;
import com.example.libraryrest.repositories.BookDAO;
import com.example.libraryrest.repositories.DeactivationReasonDAO;
import com.example.libraryrest.repositories.GenreDAO;
import com.example.libraryrest.specifications.BookSpecifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;
    private final GenreDAO genreDAO;
    private final DeactivationReasonDAO deactivationReasonDAO;

    @Autowired
    public BookService(BookDAO bookDAO, AuthorDAO authorDAO, AuthorMapper authorMapper, BookMapper bookMapper, GenreMapper genreMapper, GenreDAO genreDAO, DeactivationReasonDAO deactivationReasonDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
        this.genreDAO = genreDAO;
        this.deactivationReasonDAO = deactivationReasonDAO;
    }

    public BookFilterResponse getFiltered(BookFilterRequest filterRequest, PaginationRequest paginationRequest, SortRequest sortRequest) {
        logger.info("Get filtered books method.");
        List<Specification<Book>> specifications = new ArrayList<>();

        if (filterRequest.getPublisher() != null) {
            specifications.add(BookSpecifications.publisherLike(filterRequest.getPublisher()));
        }
        if (filterRequest.getTitle() != null) {
            specifications.add(BookSpecifications.titleLike(filterRequest.getTitle()));
        }
        if (filterRequest.getStatus() != null) {
            specifications.add(BookSpecifications.checkStatus(filterRequest.getStatus()));
        }
        if (filterRequest.getYearFrom() != null) {
            specifications.add(BookSpecifications.yearFrom(filterRequest.getYearFrom()));
        }
        if (filterRequest.getYearTo() != null) {
            specifications.add(BookSpecifications.yearTo(filterRequest.getYearTo()));
        }
        if (filterRequest.getAuthorFirstName() != null) {
            specifications.add(BookSpecifications.authorName(filterRequest.getAuthorFirstName(), "firstName"));
        }
        if (filterRequest.getAuthorLastName() != null) {
            specifications.add(BookSpecifications.authorName(filterRequest.getAuthorLastName(), "lastName"));
        }


        Specification<Book> spec = null;
        if (!specifications.isEmpty()) {
            spec = specifications.get(0);
            for (int i = 1; i < specifications.size(); i++) {
                spec = spec.and(specifications.get(i));
            }
        }

        Pageable pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getPageSize(), createSort(sortRequest));
        Page<Book> page = bookDAO.findAll(spec, pageable);
        List<BookResponse> bookResponses = page.getContent().stream().map(bookMapper::entityToResponse).toList();
        BookFilterResponse bookFilterResponse = new BookFilterResponse(page.getNumber(), page.getSize(), page.getTotalElements(), bookResponses);

        return bookFilterResponse;
    }

    public BookResponse create(BookRequest bookRequest) {
        logger.info("Create book method.");
        Book book = bookDAO.findByIsbn(bookRequest.getIsbn());
        if (book != null) {
            logger.error("This book with isbn: {} already exists!", bookRequest.getIsbn());
            throw new BookAlreadyExistsException("This book already exists!");
        }

        book = bookMapper.requestToEntity(bookRequest);
        book.setDateAdded(LocalDateTime.now());
        book.setStatus(Status.ACTIVE);

        book.setAuthors(bookRequest.getAuthors().stream()
                .map(this::getAuthor)
                .collect(Collectors.toList()));

        book.setGenres(bookRequest.getGenres().stream()
                .map(genreDAO::findById)
                .map(genre -> genre.orElseThrow(() -> new NoSuchGenreException("There is no such genre!")))
                .collect(Collectors.toList()));
        bookDAO.saveAndFlush(book);

        return bookMapper.entityToResponse(book);
    }

    private Author getAuthor(AuthorRequest authorRequest) {
        logger.info("Get author method.");
        Author entity = authorDAO.findByName(authorRequest.getFirstName(), authorRequest.getLastName());
        if (entity == null) {
            Author request = authorMapper.requestToEntity(authorRequest);
            entity = authorDAO.saveAndFlush(request);
        }

        return entity;
    }

    public BookResponse updateYear(int id, UpdateYearRequest request) {
        logger.info("Update year method.");
        if (id != request.getId()) {
            logger.error("Discrepancy between ids.");
            throw new RuntimeException("Discrepancy between ids");
        }
        Book book = bookDAO.findById(id).orElseThrow(() -> new BookNotFoundException("Book has not been found!"));
        if (book.getStatus() != Status.ACTIVE) {
            throw new BookInactiveException("This book is not active!");
        }
        book.setYear(Short.parseShort(request.getYear()));
        BookResponse response = bookMapper.entityToResponse(bookDAO.save(book));

        return response;
    }

    public BookResponse updatePublisher(int id, UpdatePublisherRequest request) {
        logger.info("Update publisher method.");
        if (id != request.getId()) {
            logger.error("Discrepancy between ids.");
            throw new RuntimeException("Discrepancy between ids");
        }
        Book book = bookDAO.findById(id).orElseThrow(() -> new BookNotFoundException(("Book has not been found!")));
        if (book.getStatus() != Status.ACTIVE) {
            throw new BookInactiveException("This book is not active!");
        }
        book.setPublisher(request.getPublisher());
        BookResponse response = bookMapper.entityToResponse(bookDAO.save(book));

        return response;
    }

    public BookResponse updateAmount(int id, UpdateAmountRequest request) {
        logger.info("Update amount method.");
        if (id != request.getId()) {
            logger.error("Discrepancy between ids.");
            throw new RuntimeException("Discrepancy between ids");
        }
        Book book = bookDAO.findById(id).orElseThrow(() -> new BookNotFoundException(("Book has not been found!")));
        if (book.getStatus() != Status.ACTIVE) {
            logger.error("This book is not active.");
            throw new BookInactiveException("This book is not active!");
        }
        book.setAmount(request.getAmount());
        BookResponse response = bookMapper.entityToResponse(bookDAO.save(book));

        return response;
    }

    public BookResponse deactivateBook(int id, UpdateStatusRequest request) {
        logger.info("Deactivate book method.");
        if (id != request.getId()) {
            logger.error("Discrepancy between ids.");

            throw new RuntimeException("Discrepancy between ids");
        }
        Book book = bookDAO.findById(id).orElseThrow(() -> new BookNotFoundException(("Book has not been found!")));
        DeactivationReason deactivationReason = deactivationReasonDAO.findById(request.getId()).orElseThrow(() -> new InvalidDeactivationReasonException("Reason invalid!"));
        book.setDeactivationReason(deactivationReason);
        book.setDeactivationDescription(request.getDeactivationDescription());
        book.setStatus(Status.INACTIVE);
        book.setDeactivationDate(LocalDateTime.now());
        BookResponse response = bookMapper.entityToResponse(bookDAO.save(book));

        return response;
    }
    private Sort createSort (SortRequest sortRequest) {

        Sort sort;

        switch (sortRequest.getSortDirection()) {
            case "ASCENDING":
                sort = Sort.by(sortRequest.getSortField()).ascending();
                break;
            case "DESCENDING":
                sort = Sort.by(sortRequest.getSortField()).descending();
                break;
            default:
                logger.error("Invalid sort direction.");
                throw new IllegalArgumentException("Invalid sort direction!");
        }

        return sort;
    }
}
