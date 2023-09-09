package com.ecureuill.rpgbattle.infrastructure.interfaces;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ValidationException;

@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
  @ExceptionHandler({
    NotFoundException.class,
    ValidationException.class,
  })
  public ResponseEntity<String> handleBadRequestException(Exception e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }
  
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
    return ResponseEntity.internalServerError().body(e.getMessage());
  }
}