package com.example.libraryrest.repositories;

import com.example.libraryrest.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDAO extends JpaRepository<Book,Integer> {
}
