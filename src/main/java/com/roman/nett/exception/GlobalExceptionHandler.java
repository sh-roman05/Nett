package com.roman.nett.exception;

import com.roman.nett.dto.ValidErrorDto;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleException(MethodArgumentNotValidException exception) {

        var errors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> ValidErrorDto.builder()
                        .field(fieldError.getField())
                        .error(fieldError.getDefaultMessage())
                        .build()).toList();

//        var errors = exception.getBindingResult().getFieldErrors().stream()
//                .map(fieldError -> {
//                    return ValidErrorDto.builder()
//                            .field(fieldError.getField())
//                            .error(fieldError.getDefaultMessage())
//                            .build();
//                }).toList();

//        var errors = exception.getBindingResult().getFieldErrors().stream()
//                .map(DefaultMessageSourceResolvable::getDefaultMessage)
//                .filter(Objects::nonNull)
//                .toList();

        var response = new LinkedHashMap<>();
        response.put("errors", errors);
        return ResponseEntity.badRequest().body(response);
    }


}
