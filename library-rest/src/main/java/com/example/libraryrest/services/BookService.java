package com.example.libraryrest.services;

import com.example.libraryrest.dto.requests.AuthorRequest;
import com.example.libraryrest.dto.requests.BookFilterRequest;
import com.example.libraryrest.dto.requests.BookRequest;
import com.example.libraryrest.dto.requests.UpdateBookYearRequest;
import com.example.libraryrest.dto.responses.BookResponse;
import com.example.libraryrest.enums.Status;
import com.example.libraryrest.exceptions.BookAlreadyExistsException;
import com.example.libraryrest.exceptions.BookNotFoundException;
import com.example.libraryrest.exceptions.NoSuchGenreException;
import com.example.libraryrest.mappers.AuthorMapper;
import com.example.libraryrest.mappers.BookMapper;
import com.example.libraryrest.mappers.GenreMapper;
import com.example.libraryrest.models.Author;
import com.example.libraryrest.models.Book;
import com.example.libraryrest.repositories.AuthorDAO;
import com.example.libraryrest.repositories.BookDAO;
import com.example.libraryrest.repositories.GenreDAO;
import com.example.libraryrest.specifications.BookSpecifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.swing.plaf.basic.BasicViewportUI;
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

    @Autowired
    public BookService(BookDAO bookDAO, AuthorDAO authorDAO, AuthorMapper authorMapper, BookMapper bookMapper, GenreMapper genreMapper, GenreDAO genreDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
        this.genreDAO = genreDAO;
    }

    public List<BookResponse> getFiltered(BookFilterRequest request){

        List<Specification<Book>> specifications=new ArrayList<>();

        if (request.getPublisher()!=null){
            specifications.add(BookSpecifications.publisherLike(request.getPublisher()));
        }
        if (request.getTitle()!=null){
            specifications.add(BookSpecifications.titleLike(request.getTitle()));
        }
        if (request.getStatus()!=null){
            specifications.add(BookSpecifications.checkStatus(request.getStatus()));
        }
        if (request.getYearFrom()!=null&&request.getYearTo()!=null){
            specifications.add(BookSpecifications.betweenYears(request.getYearFrom(), request.getYearTo()));
        }
        else {
            if (request.getYearFrom()!=null){
                specifications.add(BookSpecifications.yearFrom(request.getYearFrom()));
            }
            if (request.getYearTo()!=null){
                specifications.add(BookSpecifications.yearTo(request.getYearTo()));
            }
        }
        if (request.getAuthor()!=null){
            String firstName= request.getAuthor().trim().split(" ")[0];
            String lastName = request.getAuthor().trim().split(" ")[1];
            specifications.add(BookSpecifications.authorName(firstName,1));
            specifications.add(BookSpecifications.authorName(lastName,2));
        }


        Specification<Book> spec = null;
        if (!specifications.isEmpty()) {
            spec = specifications.get(0);
            for (int i = 1; i < specifications.size(); i++) {
                spec=spec.and(specifications.get(i));
            }
        }
        List<BookResponse> response= bookDAO.findAll(spec).stream().map(bookMapper::entityToResponse).toList();

        return response;
    }

    public BookResponse create(BookRequest bookRequest) {
        logger.info("Create book method.");
        Book book = bookDAO.findByIsbn(bookRequest.getIsbn());
        if (book != null) {
            logger.error("This book with isbn: {} already exists!", bookRequest.getIsbn());
            throw new BookAlreadyExistsException("This book already exists!");
        }

        book=bookMapper.requestToEntity(bookRequest);
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

    public BookResponse updateYear(int id, UpdateBookYearRequest request){
        Book book = bookDAO.findById(id).orElseThrow(()->new BookNotFoundException("Book has not been found!"));
        book.setYear(Short.parseShort(request.getYear()));
        BookResponse response=bookMapper.entityToResponse(bookDAO.save(book));
        return response;
    }
}
