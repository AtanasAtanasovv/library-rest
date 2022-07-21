package com.example.libraryrest.dto.requests;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class BookFilterRequest {
    @Pattern(regexp = "^[A-Z][a-z]*([-|\s][A-Z]?[a-z]*)*$")
    private String publisher;
    @Pattern(regexp = "^[A-Z][a-z]*(([-|\\s][A-Z]?)?[a-z]+)*$")
    private String title;
    @Pattern(regexp = "^1\\d{3}|(20(1[0-9]|2[0-2]|(0[0-9])))$")
    private Short yearFrom;
    @Pattern(regexp = "^1\\d{3}|(20(1[0-9]|2[0-2]|(0[0-9])))$")
    private Short yearTo;
    @Pattern(regexp = "^[A-Z][a-z]*(?:-[A-Z])?[a-z]*$")
    private String authorFirstName;
    @Pattern(regexp = "^[A-Z][a-z]*(?:-[A-Z])?[a-z]*$")
    private String authorLastName;
    private String status;
}
