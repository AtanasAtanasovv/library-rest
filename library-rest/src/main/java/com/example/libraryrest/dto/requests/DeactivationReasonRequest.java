package com.example.libraryrest.dto.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeactivationReasonRequest {

    @NotBlank
    private String name;

}
