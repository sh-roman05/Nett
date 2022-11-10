package com.roman.nett.exception;

import java.util.Map;

public class RegisterUserErrorException extends RuntimeException {

    private final Map<Object, Object> errors;

    public Map<Object, Object> getErrors() {
        return errors;
    }

    public RegisterUserErrorException(Map<Object, Object> errors) {
        this.errors = errors;
    }
}
