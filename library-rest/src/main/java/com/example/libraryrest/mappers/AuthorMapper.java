package com.example.libraryrest.mappers;

import com.example.libraryrest.dto.requests.AuthorRequest;
import com.example.libraryrest.dto.responses.AuthorResponse;
import com.example.libraryrest.models.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public Author requestToEntity(AuthorRequest request) {

        Author author = new Author();
        author.setFirstName(request.getFirstName());
        author.setLastName(request.getLastName());

        return author;
    }

    public AuthorResponse entityToResponse(Author author) {

        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setFirstName(author.getFirstName());
        response.setLastName(author.getLastName());

        return response;
    }
}
