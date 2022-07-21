package com.example.libraryrest.repositories;

import com.example.libraryrest.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookDAO extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

    Book findByIsbn(String isbn);

}
