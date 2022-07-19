package com.example.libraryrest.dto.requests;

import lombok.Data;

@Data
public class PaginationRequest {
    private int pageSize;
    private int page;
}
