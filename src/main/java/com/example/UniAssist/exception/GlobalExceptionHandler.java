package com.example.UniAssist.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.UniAssist.model.dto.ErrorResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ScheduleNotFound.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleScheduleNotFound(ScheduleNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleAuthException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(HttpStatus.UNAUTHORIZED, ex.getMessage()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleExpiredJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(HttpStatus.UNAUTHORIZED, "Token expired"));
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleUnsupportedJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(HttpStatus.UNAUTHORIZED, "Unsupported JWT token"));
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleMalformedJwtException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(HttpStatus.UNAUTHORIZED, "Malformed JWT token"));
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleSignatureException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(HttpStatus.UNAUTHORIZED, "Invalid signature"));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = "Database integrity violation: " + ex.getMostSpecificCause().getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST, message));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolation(ConstraintViolationException ex) {
        String message = "Validation error: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST, message));
    }

    @ExceptionHandler(TransactionSystemException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleTransactionException(TransactionSystemException ex) {
        String message = "Transaction error: " + (ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, message));
    }

    @ExceptionHandler(JpaSystemException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleJpaSystemException(JpaSystemException ex) {
        String message = "JPA error: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, message));
    }

    @ExceptionHandler(PersistenceException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handlePersistenceException(PersistenceException ex) {
        String message = "Database persistence error: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        String message = "Invalid arguments: " + ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(HttpStatus.BAD_REQUEST, message));
    }
}