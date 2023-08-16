package com.example.libraryrest.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookFilterResponse {

    private int page;
    private int pageSize;
    private Long filteredElements;
    private List<BookResponse> bookResponses;

}
