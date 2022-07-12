package com.example.libraryrest.dto.requests;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class UpdateStatusRequest {
    @Min(value = 1)
    private int id;
    private String deactivationDescription;
}
