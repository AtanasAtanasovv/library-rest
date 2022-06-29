package com.example.libraryrest.mappers;

import com.example.libraryrest.dto.requests.GenreRequest;
import com.example.libraryrest.dto.responses.GenreResponse;
import com.example.libraryrest.models.Genre;
import com.example.libraryrest.repositories.GenreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    @Autowired
    private GenreDAO genreDAO;

    public Genre requestToEntity (GenreRequest request){
        return genreDAO.findByName(request.getName());
    }

    public GenreResponse entityToResponse (Genre genre){
        return new GenreResponse(){{
            this.setId(genre.getId());
            this.setName(genre.getName());
        }};
    }
}
