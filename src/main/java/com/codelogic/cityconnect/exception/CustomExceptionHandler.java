package com.codelogic.cityconnect.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<CustomException> handlerAllExceptions(Exception e){
//        CustomException response = new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }

    @ExceptionHandler(BusinessExcetion.class)
    public ResponseEntity<CustomException> handlerBusinessExcetion(BusinessExcetion e){
        CustomException response = new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomException> handlerResourceNotFoundException(ResourceNotFoundException e){
        CustomException response = new CustomException(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomException> handlerBadCredentialsException(BadCredentialsException e){
        CustomException response = new CustomException(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        CustomException response = new CustomException(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(), HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<CustomException> handleValidationException(MethodArgumentNotValidException e) {
//        CustomException response = new CustomException(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage(), HttpStatus.UNAUTHORIZED.value());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }

}
