package com.example.libraryrest.mappers;

import com.example.libraryrest.dto.requests.BookRequest;
import com.example.libraryrest.dto.responses.BookResponse;
import com.example.libraryrest.models.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookMapper {

    private final Logger logger = LoggerFactory.getLogger(BookMapper.class);

    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;

    @Autowired
    public BookMapper(AuthorMapper authorMapper, GenreMapper genreMapper) {
        this.authorMapper = authorMapper;
        this.genreMapper = genreMapper;
    }

    public Book requestToEntity(BookRequest request) {
        logger.info("Request to entity method.");
        Book book = new Book();
        book.setAmount(request.getAmount());
        book.setIsbn(request.getIsbn());
        book.setPublisher(request.getPublisher());
        book.setTitle(request.getTitle());
        book.setYear(request.getYear());
        book.setLanguage(request.getLanguage());

        return book;
    }

    public BookResponse entityToResponse(Book book) {
        logger.info("Entity to response method.");
        BookResponse response = new BookResponse();
        response.setId(book.getId());
        response.setAmount(book.getAmount());
        response.setIsbn(book.getIsbn());
        response.setTitle(book.getTitle());
        response.setPublisher(book.getPublisher());
        response.setYear(book.getYear());
        response.setLanguage(book.getLanguage());
        response.setDateAdded(book.getDateAdded());
        response.setAuthors(book.getAuthors().stream().map(author -> authorMapper.entityToResponse(author)).collect(Collectors.toList()));
        response.setGenres(book.getGenres().stream().map(genre -> genreMapper.entityToResponse(genre)).collect(Collectors.toList()));

        return response;
    }

}
