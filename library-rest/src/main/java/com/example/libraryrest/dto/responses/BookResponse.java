package com.example.libraryrest.dto.responses;

import com.example.libraryrest.enums.Status;
import com.example.libraryrest.models.DeactivationReason;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookResponse {

    private int id;
    private int amount;
    private String title;
    private String isbn;
    private String year;
    private String publisher;
    private List<AuthorResponse> authors;
    private List<GenreResponse> genres;
    private LocalDateTime dateAdded;
    private String language;
    private Status status;
    private LocalDateTime deactivationDate;
    private String deactivationDescription;
    private DeactivationReason deactivationReason;

}
