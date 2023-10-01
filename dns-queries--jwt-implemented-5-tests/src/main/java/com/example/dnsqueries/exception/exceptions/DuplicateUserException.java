package com.example.dnsqueries.exception.exceptions;

import lombok.RequiredArgsConstructor;


public class DuplicateUserException extends Exception {

    public DuplicateUserException() {
        super("User with the same name already exists.");
    }

    public DuplicateUserException(String message) {
        super(message);
    }

    public DuplicateUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
