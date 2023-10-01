package com.example.dnsqueries.exception.exceptions;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreationError extends RuntimeException {
    public CreationError(String message) {
        super(message);
    }

    public CreationError(String message, Throwable cause) {
        super(message, cause);
    }
}
