package com.example.backend.exceptions;

public class NoStudentsInDBException extends RuntimeException {
    public NoStudentsInDBException(String message) {
        super(message);
    }

}
