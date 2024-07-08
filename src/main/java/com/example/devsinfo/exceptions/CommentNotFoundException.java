package com.example.devsinfo.exceptions;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() {
        super();
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}
