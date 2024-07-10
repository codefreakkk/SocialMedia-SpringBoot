package com.example.devsinfo.exceptions;

import java.io.IOException;

public class FileUploadException extends IOException {
    public FileUploadException() {
        super();
    }

    public FileUploadException(String message) {
        super(message);
    }
}
