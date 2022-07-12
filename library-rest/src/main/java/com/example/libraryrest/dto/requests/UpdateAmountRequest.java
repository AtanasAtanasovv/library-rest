package com.example.libraryrest.dto.requests;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class UpdateAmountRequest {
    @Min(value = 1)
    private int amount;
}
