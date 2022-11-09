package com.roman.nett.exception;

import com.roman.nett.dto.ValidErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleException(MethodArgumentNotValidException exception) {
        var errors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> ValidErrorDto.builder()
                        .field(fieldError.getField())
                        .error(fieldError.getDefaultMessage())
                        .build()).toList();
        var response = new LinkedHashMap<>();
        response.put("errors", errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(BadCredentialsException exception) {
        var error = new LinkedHashMap<>();
        error.put("error", exception.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler
    public ResponseEntity<?> handleException(UsernameNotFoundException exception) {
        var error = new LinkedHashMap<>();
        error.put("error", exception.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    //


}
