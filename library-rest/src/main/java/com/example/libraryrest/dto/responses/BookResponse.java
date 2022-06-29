package com.example.libraryrest.dto.responses;

import lombok.Data;

import java.util.List;

@Data
public class BookResponse {

    private int id;
    private int amount;
    private String title;
    private String isbn;
    private String year;
    private String publisher;
    private List<AuthorResponse> authors;
    private List<GenreResponse> genres;

}
