package com.example.libraryrest.mappers;

import com.example.libraryrest.dto.requests.AuthorRequest;
import com.example.libraryrest.dto.responses.AuthorResponse;
import com.example.libraryrest.models.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {



    public Author requestToEntity (AuthorRequest request){
        Author author=new Author();
        author.setFirstName(request.getFirstName());
        author.setLastName(request.getLastName());
        return author;
    }

    public AuthorResponse entityToResponse (Author author){
        return new AuthorResponse() {
            {
                this.setId(author.getId());
                this.setFirstName(author.getFirstName());
                this.setLastName(author.getLastName());
            }
        };
    }
}
