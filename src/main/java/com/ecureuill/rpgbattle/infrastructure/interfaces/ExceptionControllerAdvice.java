package com.ecureuill.rpgbattle.infrastructure.interfaces;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ecureuill.rpgbattle.application.exceptions.CharacterAlreadyExistException;
import com.ecureuill.rpgbattle.application.exceptions.CharacterNotFoundException;
import com.ecureuill.rpgbattle.application.exceptions.InvalidBattleParametersException;
import com.ecureuill.rpgbattle.application.exceptions.PlayerNotFoundException;

import jakarta.validation.ValidationException;

@RestControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
  @ExceptionHandler({
    NotFoundException.class,
    ValidationException.class,
    CharacterNotFoundException.class, 
    CharacterAlreadyExistException.class,
    PlayerNotFoundException.class,
    InvalidBattleParametersException.class
  })
  public ResponseEntity<String> handleBadRequestException(Exception e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler({
    DataIntegrityViolationException.class
  })
  public ResponseEntity<String> handleDataIntegrityViolationException(Exception e) {
    return ResponseEntity.badRequest().body("Duplicate entry is not allowed");
  }
  
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
    return ResponseEntity.internalServerError().body(e.getMessage());
  }
}