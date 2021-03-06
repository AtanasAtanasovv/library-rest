package com.example.libraryrest.dto.requests;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class UpdateYearRequest {
    @Min(value = 1)
    private int id;
    @Pattern(regexp = "^1\\d{3}|(20(1[0-9]|2[0-2]|(0[0-9])))$")
    private String year;
}
