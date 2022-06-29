package com.example.libraryrest.dto.requests;

import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class BookRequest {

    @NotNull
    @Min(value = 0)
    private int amount;
    @NotBlank
    @Pattern(regexp="^[A-Z][a-z]*([-|\s][A-Z]?[a-z]*)*$")
    private String title;
    @NotBlank
    @ISBN
    private String isbn;
    @NotBlank
    @Pattern(regexp = "^1\\d{3}|(20(1[0-9]|2[0-2]|(0[0-9])))$")
    private String year;
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-z]*([-|\s][A-Z]?[a-z]*)*$")
    private String publisher;
    @NotEmpty
    //@Min(value = 1)
    private List<Integer> genres;
    @NotEmpty
    private List<AuthorRequest> authors;

}