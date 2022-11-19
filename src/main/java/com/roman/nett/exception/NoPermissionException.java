package com.roman.nett.exception;

public class NoPermissionException extends RuntimeException {

    private final String message;

    public NoPermissionException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
