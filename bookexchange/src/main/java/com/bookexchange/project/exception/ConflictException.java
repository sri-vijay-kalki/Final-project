package com.bookexchange.project.exception;


public class ConflictException extends Exception {
    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictException() {
        super();
    }
}

