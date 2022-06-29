package com.example.libraryrest.repositories;

import com.example.libraryrest.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenreDAO extends JpaRepository<Genre,Integer> {

    Genre findByName(String name);
    @Query("delete from Genre where name=?1")
    Genre deleteByName(String name);

}
