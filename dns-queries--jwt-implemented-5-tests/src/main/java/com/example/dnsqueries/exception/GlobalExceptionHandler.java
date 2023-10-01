package com.example.dnsqueries.exception;
import com.example.dnsqueries.exception.exceptions.CreationError;
import com.example.dnsqueries.exception.exceptions.DuplicateUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.net.ConnectException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        String errorMessage = "Duplicate entry. The value is already in use.";
        log.error("Duplicate Entry");
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CreationError.class)
    public ResponseEntity<String> handleCreationErrorException(CreationError ex) {
        String errorMessage = "An error occured while adding domain.";
        log.error("An error occured while adding domain.");
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<String> handleDuplicateUserException(DuplicateUserException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

}
