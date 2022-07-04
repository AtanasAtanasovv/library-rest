package com.example.libraryrest.repositories;

import com.example.libraryrest.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenreDAO extends JpaRepository<Genre, Integer> {

    @Query("delete from Genre where name=?1")
    void deleteByName(String name);

}
