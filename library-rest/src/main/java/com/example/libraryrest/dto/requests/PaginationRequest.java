package com.example.libraryrest.dto.requests;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class PaginationRequest {
    @Min(1)
    private int pageSize=3;
    @Min(0)
    private int page=0;
}
