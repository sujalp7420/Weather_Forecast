package com.weather.exception;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(404)
                                .error("Not Found")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build()
                );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse>
    handleUserExists(
            UserAlreadyExistsException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(409)
                                .error("Conflict")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build()
                );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse>
    handleCredentials(
            InvalidCredentialsException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(401)
                                .error("Unauthorized")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>
    handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField()
                                + ": "
                                + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest()
                .body(
                        ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(400)
                                .error("Validation Error")
                                .message(errors)
                                .path(request.getRequestURI())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleGeneral(
            Exception ex,
            HttpServletRequest request) {

        return ResponseEntity.status(
                        HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(500)
                                .error("Internal Server Error")
                                .message(ex.getMessage())
                                .path(request.getRequestURI())
                                .build()
                );
    }
}