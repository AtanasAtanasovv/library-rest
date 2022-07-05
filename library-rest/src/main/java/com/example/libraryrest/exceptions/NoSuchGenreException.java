package com.example.libraryrest.exceptions;

public class NoSuchGenreException extends RuntimeException{
    public NoSuchGenreException(String message){
        super(message);
    }
}
