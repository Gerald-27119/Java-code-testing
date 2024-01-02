package com.example.frontend.exceptions;

public class NoStudentsInDBException extends RuntimeException {
    public NoStudentsInDBException(String message) {
        super(message);
    }

}
