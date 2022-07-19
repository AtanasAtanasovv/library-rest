package com.example.libraryrest.dto.responses;

import com.example.libraryrest.dto.requests.BookFilterRequest;
import com.example.libraryrest.dto.requests.PaginationRequest;
import com.example.libraryrest.dto.requests.SortRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookFilterResponse {

    private BookFilterRequest bookFilterRequest;
    private PaginationRequest paginationRequest;
    private SortRequest sortRequest;
    private List<BookResponse> bookResponses;

}
