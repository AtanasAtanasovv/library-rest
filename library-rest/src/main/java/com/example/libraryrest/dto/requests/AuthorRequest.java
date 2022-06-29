package com.example.libraryrest.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class AuthorRequest {

    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]*(?:-[A-Z])?[a-z]*$")
    private String firstName;
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]*(?:-[A-Z])?[a-z]*$")
    private String lastName;

}
