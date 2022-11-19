package com.roman.nett.exception;

import com.roman.nett.dto.ValidErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Обработка ошибок при валидации через Hibernate Validator
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleException(MethodArgumentNotValidException exception) {
        var errors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> ValidErrorDto.builder()
                        .field(fieldError.getField())
                        .error(fieldError.getDefaultMessage())
                        .build()).toList();
        var response = new LinkedHashMap<>();
        response.put("errors", errors);
        return response;
    }

    //Запрашиваемый ресурс не найден
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object handleException(ResourceNotFoundException exception) {
        var error = new LinkedHashMap<>();
        error.put("error", exception.getMessage());
        return error;
    }

    //Нет разрешений на получение ресурса
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Object handleException(NoPermissionException exception) {
        var error = new LinkedHashMap<>();
        error.put("error", exception.getMessage());
        return error;
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

    @ExceptionHandler
    public ResponseEntity<?> handleException(RegisterUserErrorException exception) {
        //var error = new LinkedHashMap<>();
        //error.put("error", exception.getMessage());
        return ResponseEntity.badRequest().body(exception.getErrors());
    }


    @ExceptionHandler
    public ResponseEntity<?> handleException(NoEntityException exception) {
        return ResponseEntity.status(404).body("пост не найден " + exception.getMessage());
    }

    //


}
