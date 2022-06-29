package com.example.libraryrest.repositories;

import com.example.libraryrest.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthorDAO extends JpaRepository<Author,Integer> {

    @Query(value = "SELECT a FROM Author a where a.firstName = ?1 and a.lastName = ?2")
    Author findByName (String firstName,String lastName);

}
