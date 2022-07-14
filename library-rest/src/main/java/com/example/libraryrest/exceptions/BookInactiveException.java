package com.example.libraryrest.exceptions;

public class BookInactiveException extends RuntimeException {
    public BookInactiveException(String message) {
        super(message);
    }
}
