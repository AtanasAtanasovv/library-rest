package com.example.libraryrest.dto.requests;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class BookFilterRequest {
    @Pattern(regexp = "^[A-Z][a-z]*([-|\s][A-Z]?[a-z]*)*$")
    private String publisher;
    @Pattern(regexp = "^[A-Z][a-z]*(([-|\\s][A-Z]?)?[a-z]+)*$")
    private String title;
    @Min(0)
    private Short yearFrom;
    @Min(0)
    private Short yearTo;
    @Pattern(regexp = "^[A-Z][a-z]*(?:-[A-Z])?[a-z]*$")
    private String authorFirstName;
    @Pattern(regexp = "^[A-Z][a-z]*(?:-[A-Z])?[a-z]*$")
    private String authorLastName;
    private String status;
}
