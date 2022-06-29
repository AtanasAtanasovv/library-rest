package com.example.libraryrest.mappers;

import com.example.libraryrest.dto.requests.BookRequest;
import com.example.libraryrest.dto.responses.BookResponse;
import com.example.libraryrest.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookMapper {

    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private GenreMapper genreMapper;

    public Book requestToEntity(BookRequest request) {
        Book book = new Book();
        book.setAmount(request.getAmount());
        book.setIsbn(request.getIsbn());
        book.setPublisher(request.getPublisher());
        book.setTitle(request.getTitle());
        book.setYear(request.getYear());

        return book;
    }

    public BookResponse entityToResponse(Book book) {
        return new BookResponse() {{
            this.setId(book.getId());
            this.setAmount(book.getAmount());
            this.setIsbn(book.getIsbn());
            this.setTitle(book.getTitle());
            this.setPublisher(book.getPublisher());
            this.setYear(book.getYear());
            this.setAuthors(book.getAuthors().stream().map(author -> authorMapper.entityToResponse(author)).collect(Collectors.toList()));
            this.setGenres(book.getGenres().stream().map(genre -> genreMapper.entityToResponse(genre)).collect(Collectors.toList()));
        }};
    }

}
