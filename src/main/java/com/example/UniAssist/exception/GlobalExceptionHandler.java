package com.example.UniAssist.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.PersistenceException;
import jakarta.security.auth.message.AuthException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ScheduleNotFound.class)
    public ResponseEntity<String> handleScheduleNotFound(ScheduleNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(LessonNotFound.class)
    public ResponseEntity<String> handleLessonNotFound(LessonNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<String> handleAuthException(AuthException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<String> handleUnsupportedJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unsupported JWT token");
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> handleMalformedJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Malformed JWT token");
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<String> handleSignatureException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid signature");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "Database integrity violation: " + ex.getMostSpecificCause().getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        String message = "Validation error: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<String> handleTransactionException(TransactionSystemException ex) {
        String message = "Transaction error: " + (ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<String> handleJpaSystemException(JpaSystemException ex) {
        String message = "JPA error: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<String> handlePersistenceException(PersistenceException ex) {
        String message = "Database persistence error: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        String message = "Invalid arguments: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}