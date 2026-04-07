package com.example.exception;

public class ComplaintNotFoundException extends RuntimeException {
    public ComplaintNotFoundException(String message) {
        super(message);
    }
}
