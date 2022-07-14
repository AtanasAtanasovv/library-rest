package com.example.libraryrest.dto.requests;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class UpdatePublisherRequest {
    @Min(value = 1)
    private int id;
    @Pattern(regexp = "^[A-Z][a-z]*([-|\s][A-Z]?[a-z]*)*$")
    private String publisher;
}
