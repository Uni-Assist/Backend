package com.example.UniAssist.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.UniAssist.model.dto.ErrorResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ErrorResponseDTO> buildError(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ErrorResponseDTO(status, message));
    }

    private ResponseEntity<ErrorResponseDTO> buildError(HttpStatus status, List<String> message) {
        return ResponseEntity.status(status).body(new ErrorResponseDTO(status, message));
    }

    @ExceptionHandler(ScheduleNotFound.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleScheduleNotFound(ScheduleNotFound ex) {
        logger.warn(ex.getMessage());
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleAuthException(AuthenticationException ex) {
        logger.warn("Authentication failed: {}", ex.getMessage());
        return buildError(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(SolutionAlreadyExists.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleSolutionAlreadyExists(SolutionAlreadyExists ex) {
        logger.warn(ex.getMessage());
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(SolutionNotFound.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleSolutionNotFound(SolutionNotFound ex) {
        logger.warn(ex.getMessage());
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(UpdateMarkFailed.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleUpdateMarkFailed(UpdateMarkFailed ex) {
        logger.error(ex.getMessage());
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleExpiredJwtException() {
        logger.warn("JWT token expired");
        return buildError(HttpStatus.UNAUTHORIZED, "Token expired");
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleUnsupportedJwtException() {
        logger.warn("Unsupported JWT token");
        return buildError(HttpStatus.UNAUTHORIZED, "Unsupported JWT token");
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleMalformedJwtException() {
        logger.warn("Malformed JWT token");
        return buildError(HttpStatus.UNAUTHORIZED, "Malformed JWT token");
    }

    @ExceptionHandler(SignatureException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleSignatureException() {
        logger.warn("Invalid JWT signature");
        return buildError(HttpStatus.UNAUTHORIZED, "Invalid signature");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Throwable rootCause = ex.getMostSpecificCause();
        String errorMessage = rootCause.getMessage();
        logger.error("Data integrity violation: {}", errorMessage, ex);
        return buildError(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());
        logger.warn("Validation errors: {}", errors);
        return buildError(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(TransactionSystemException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleTransactionException(TransactionSystemException ex) {
        String message = "Transaction error: " + (ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage());
        logger.error("Transaction system error: {}", message, ex);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler(JpaSystemException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleJpaSystemException(JpaSystemException ex) {
        String message = "JPA error: " + ex.getMessage();
        logger.error("JPA system error: {}", message, ex);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler(PersistenceException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handlePersistenceException(PersistenceException ex) {
        String message = "Database persistence error: " + ex.getMessage();
        logger.error("Persistence error: {}", message, ex);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        String message = "Invalid arguments: " + ex.getMessage();
        logger.warn("Illegal argument: {}", message);
        return buildError(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        String message = "Unexpected error occurred: " + ex.getMessage();
        logger.error("Unhandled exception: {}", message, ex);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}