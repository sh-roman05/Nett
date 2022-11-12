package com.roman.nett.exception;

public class NoEntityException extends RuntimeException {
    public NoEntityException(Long id) {
        super(id.toString());
    }
}
