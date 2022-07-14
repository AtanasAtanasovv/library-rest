package com.example.libraryrest.controllers;

import com.example.libraryrest.dto.requests.BookFilterRequest;
import com.example.libraryrest.dto.requests.BookRequest;
import com.example.libraryrest.dto.requests.UpdateBookYearRequest;
import com.example.libraryrest.dto.responses.BookResponse;
import com.example.libraryrest.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    ResponseEntity<List<BookResponse>> filterBooks (BookFilterRequest request){
        return ResponseEntity.ok(bookService.getFiltered(request));
    }
    @PostMapping("/books")
    ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.create(request));
    }
    @PutMapping("/books/{id}")
    ResponseEntity<BookResponse> updateYear(@PathVariable int id, @RequestBody UpdateBookYearRequest request){
        return ResponseEntity.ok(bookService.updateYear(id,request));
    }
}
