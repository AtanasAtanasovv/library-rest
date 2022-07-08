package com.example.libraryrest.exceptions;

public class InvalidDeactivationReasonException extends RuntimeException{
    public InvalidDeactivationReasonException(String message){
        super(message);
    }
}
