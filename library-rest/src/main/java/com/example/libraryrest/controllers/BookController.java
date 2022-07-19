package com.example.libraryrest.controllers;

import com.example.libraryrest.dto.requests.BookFilterRequest;
import com.example.libraryrest.dto.requests.BookRequest;
import com.example.libraryrest.dto.requests.PaginationRequest;
import com.example.libraryrest.dto.requests.SortRequest;
import com.example.libraryrest.dto.requests.UpdateAmountRequest;
import com.example.libraryrest.dto.requests.UpdateStatusRequest;
import com.example.libraryrest.dto.requests.UpdateYearRequest;
import com.example.libraryrest.dto.requests.UpdatePublisherRequest;
import com.example.libraryrest.dto.responses.BookFilterResponse;
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
    ResponseEntity<BookFilterResponse> filterBooks (BookFilterRequest filterRequest, PaginationRequest paginationRequest, SortRequest sortRequest){
        return ResponseEntity.ok(bookService.getFiltered(filterRequest,paginationRequest,sortRequest));
    }
    @PostMapping("/books")
    ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.create(request));
    }

    @PutMapping("/books/year/{id}")
    ResponseEntity<BookResponse> updateYear(@PathVariable int id, @Valid @RequestBody UpdateYearRequest request) {
        return ResponseEntity.ok(bookService.updateYear(id, request));
    }

    @PutMapping("/books/publisher/{id}")
    ResponseEntity<BookResponse> updatePublisher(@PathVariable int id, @Valid @RequestBody UpdatePublisherRequest request) {
        return ResponseEntity.ok(bookService.updatePublisher(id, request));
    }

    @PutMapping("/books/amount/{id}")
    ResponseEntity<BookResponse> updateAmount(@PathVariable int id, @Valid @RequestBody UpdateAmountRequest request) {
        return ResponseEntity.ok(bookService.updateAmount(id, request));
    }

    @PutMapping("books/deactivate/{id}")
    ResponseEntity<BookResponse> deactivateBook(@PathVariable int id, @Valid @RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok(bookService.deactivateBook(id, request));
    }
}
