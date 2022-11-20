package com.roman.nett.exception;

public class UnknownRepositoryException extends RuntimeException {
    private final String message;

    public UnknownRepositoryException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
