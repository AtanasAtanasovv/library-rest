package com.example.libraryrest.mappers;

import com.example.libraryrest.dto.requests.GenreRequest;
import com.example.libraryrest.dto.responses.GenreResponse;
import com.example.libraryrest.models.Genre;
import com.example.libraryrest.repositories.GenreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    private final GenreDAO genreDAO;

    @Autowired
    public GenreMapper(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    public Genre requestToEntity (GenreRequest request){
        return genreDAO.findByName(request.getName());
    }

    public GenreResponse entityToResponse (Genre genre){

        GenreResponse response=new GenreResponse();
        response.setId(genre.getId());
        response.setName(genre.getName());

        return response;
    }
}
