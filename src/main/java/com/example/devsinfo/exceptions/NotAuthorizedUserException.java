package com.example.devsinfo.exceptions;

public class NotAuthorizedUserException extends RuntimeException {
    public NotAuthorizedUserException() {
        super();
    }

    public NotAuthorizedUserException(String message) {
        super(message);
    }
}
