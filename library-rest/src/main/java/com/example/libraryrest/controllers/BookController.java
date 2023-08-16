package com.example.libraryrest.controllers;

import com.example.libraryrest.dto.requests.BookFilterRequest;
import com.example.libraryrest.dto.requests.BookRequest;
import com.example.libraryrest.dto.requests.PaginationRequest;
import com.example.libraryrest.dto.requests.SortRequest;
import com.example.libraryrest.dto.requests.UpdateAmountRequest;
import com.example.libraryrest.dto.requests.UpdatePublisherRequest;
import com.example.libraryrest.dto.requests.UpdateStatusRequest;
import com.example.libraryrest.dto.requests.UpdateYearRequest;
import com.example.libraryrest.dto.responses.BookFilterResponse;
import com.example.libraryrest.dto.responses.BookResponse;
import com.example.libraryrest.models.Student;
import com.example.libraryrest.services.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.UriSpec;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/student")
    ResponseEntity<Student> getStudent() throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.findAndRegisterModules();
        WebClient client=WebClient.create("http://localhost:8081");
        UriSpec<RequestBodySpec> uriSpec = client.method(HttpMethod.GET);
        RequestBodySpec bodySpec = uriSpec.uri("/student");
        Mono<String> response = bodySpec.retrieve().bodyToMono(String.class);
        Student student = objectMapper.readValue(response.block(),Student.class);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/books")
    ResponseEntity<BookFilterResponse> filterBooks (@Valid BookFilterRequest filterRequest,@Valid PaginationRequest paginationRequest, SortRequest sortRequest) {
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
