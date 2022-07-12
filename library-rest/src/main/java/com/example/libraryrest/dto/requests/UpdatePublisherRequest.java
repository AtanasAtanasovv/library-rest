package com.example.libraryrest.dto.requests;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UpdatePublisherRequest {
    @Pattern(regexp = "^[A-Z][a-z]*([-|\s][A-Z]?[a-z]*)*$")
    private String publisher;
}
