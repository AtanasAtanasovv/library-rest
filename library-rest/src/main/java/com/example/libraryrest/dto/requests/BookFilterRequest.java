package com.example.libraryrest.dto.requests;

import lombok.Data;

import java.util.List;

@Data
public class BookFilterRequest {
    private String publisher;
    private String title;
    private Short yearFrom;
    private Short yearTo;
    private String authorFirstName;
    private String authorLastName;
    private String status;
}
