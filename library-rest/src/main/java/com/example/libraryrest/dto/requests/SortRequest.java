package com.example.libraryrest.dto.requests;

import lombok.Data;

@Data
public class SortRequest {
    private String sortField="id";
    private String sortDirection="ASCENDING";
}
