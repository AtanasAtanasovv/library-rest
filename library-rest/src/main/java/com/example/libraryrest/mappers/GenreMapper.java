package com.example.libraryrest.mappers;

import com.example.libraryrest.dto.responses.GenreResponse;
import com.example.libraryrest.models.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public GenreResponse entityToResponse(Genre genre) {

        GenreResponse response = new GenreResponse();
        response.setId(genre.getId());
        response.setName(genre.getName());

        return response;
    }
}
