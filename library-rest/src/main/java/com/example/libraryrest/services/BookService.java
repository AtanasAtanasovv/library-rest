package com.example.libraryrest.services;

import com.example.libraryrest.dto.requests.AuthorRequest;
import com.example.libraryrest.dto.requests.BookRequest;
import com.example.libraryrest.dto.responses.BookResponse;
import com.example.libraryrest.mappers.AuthorMapper;
import com.example.libraryrest.mappers.BookMapper;
import com.example.libraryrest.mappers.GenreMapper;
import com.example.libraryrest.models.Author;
import com.example.libraryrest.models.Book;
import com.example.libraryrest.repositories.AuthorDAO;
import com.example.libraryrest.repositories.BookDAO;
import com.example.libraryrest.repositories.GenreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;
    private final GenreMapper genreMapper;
    private final GenreDAO genreDAO;

    @Autowired
    public BookService(BookDAO bookDAO, AuthorDAO authorDAO, AuthorMapper authorMapper, BookMapper bookMapper, GenreMapper genreMapper, GenreDAO genreDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
        this.genreMapper = genreMapper;
        this.genreDAO = genreDAO;
    }

    public BookResponse create(BookRequest bookRequest){
        Book book=bookMapper.requestToEntity(bookRequest);

        book.setAuthors(bookRequest.getAuthors().stream()
                .map(this::getAuthor)
                .collect(Collectors.toList()));

        book.setGenres(bookRequest.getGenres().stream()
                .map(genreRequest-> genreDAO.findById(genreRequest))
                .map(genre -> genre.orElseThrow(RuntimeException::new))
                .collect(Collectors.toList()));
        bookDAO.saveAndFlush(book);
        return bookMapper.entityToResponse(book);
    }

    private Author getAuthor(AuthorRequest authorRequest) {
        Author entity = authorDAO.findByName(authorRequest.getFirstName(), authorRequest.getLastName());
        if (entity==null){
            Author request = authorMapper.requestToEntity(authorRequest);
            entity = authorDAO.saveAndFlush(request);
        }

        return entity;
    }

}
