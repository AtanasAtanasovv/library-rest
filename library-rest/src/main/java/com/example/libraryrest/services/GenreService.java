package com.example.libraryrest.services;

import com.example.libraryrest.models.Genre;
import com.example.libraryrest.repositories.GenreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreDAO genreDAO;

    @Autowired
    public GenreService(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    public List<Genre> findAll(){
        return genreDAO.findAll();
    }
    public Genre findById(int id){

        return genreDAO.findById(id).orElseThrow();
    }
    public void deleteByName(String name){
        genreDAO.deleteByName(name);
    }

}
