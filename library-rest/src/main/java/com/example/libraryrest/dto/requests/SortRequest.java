package com.example.libraryrest.dto.requests;

import lombok.Data;

@Data
public class SortRequest {
    private String sortField;
    private String sortDirection="ASCENDING";
}
