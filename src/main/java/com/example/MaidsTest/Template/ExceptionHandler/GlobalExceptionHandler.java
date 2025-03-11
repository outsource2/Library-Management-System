package com.example.MaidsTest.Template.ExceptionHandler;

import com.example.MaidsTest.Base.Enum.EBase;
import com.example.MaidsTest.Base.Enum.EError;
import com.example.MaidsTest.Template.Exception.BookAlreadyBorrowedException;
import com.example.MaidsTest.Template.Exception.BookNotFoundException;
import com.example.MaidsTest.Template.Exception.BorrowingRecordNotFoundException;
import com.example.MaidsTest.Template.Exception.PatronNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global Exception Handler for managing different types of exceptions and returning appropriate responses.
 * This class handles application-specific exceptions as well as validation and database integrity violations.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the case when a borrowing record is not found.
     *
     * @param ex BorrowingRecordNotFoundException thrown exception
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(BorrowingRecordNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(BorrowingRecordNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put(EBase.STATUS_CODE.name(), 400);
        response.put(EBase.ERROR.name(), EError.BORROWING_RECORD_NOT_FOUND.name());
        response.put(EBase.ERROR_MESSAGE.name(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles the case when a book is not found.
     *
     * @param ex BookNotFoundException thrown exception
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(BookNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put(EBase.STATUS_CODE.name(), 400);
        response.put(EBase.ERROR.name(), EError.BOOK_NOT_FOUND);
        response.put(EBase.ERROR_MESSAGE.name(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles the case when a patron is not found.
     *
     * @param ex PatronNotFoundException thrown exception
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(PatronNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(PatronNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put(EBase.STATUS_CODE.name(), 400);
        response.put(EBase.ERROR.name(), EError.PATRON_NOT_FOUND);
        response.put(EBase.ERROR_MESSAGE.name(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles the case when a book has already been borrowed.
     *
     * @param ex BookAlreadyBorrowedException thrown exception
     * @return ResponseEntity with error details
     */
    @ExceptionHandler(BookAlreadyBorrowedException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(BookAlreadyBorrowedException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put(EBase.STATUS_CODE.name(), 400);
        response.put(EBase.ERROR.name(), EError.BOOK_IS_ALREADY_BORROWED);
        response.put(EBase.ERROR_MESSAGE.name(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles validation exceptions from invalid method arguments.
     *
     * @param ex MethodArgumentNotValidException thrown exception
     * @return ResponseEntity with detailed validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles constraint violation exceptions.
     *
     * @param ex ConstraintViolationException thrown exception
     * @return ResponseEntity with constraint violation errors
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles data integrity violations (e.g., foreign key constraint violations).
     *
     * @param ex DataIntegrityViolationException thrown exception
     * @return ResponseEntity with error details about the integrity violation
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put(EBase.STATUS_CODE.name(), 400);
        response.put(EBase.ERROR.name(), EBase.BAD_REQUEST.name());
        response.put(EBase.ERROR_MESSAGE.name(), "There is a foreign key constraint violation. Please ensure there are no dependent records.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles illegal argument exceptions.
     *
     * @param ex IllegalArgumentException thrown exception
     * @return ResponseEntity with error details about the illegal argument
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put(EBase.STATUS_CODE.name(), 422);
        response.put(EBase.ERROR.name(), EBase.UNPROCESSABLE_ENTITY.name());
        response.put(EBase.ERROR_MESSAGE.name(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    /**
     * Handles general exceptions.
     *
     * @param ex Exception thrown exception
     * @return ResponseEntity with error details for general exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put(EBase.STATUS_CODE.name(), 500);
        response.put(EBase.ERROR.name(), EBase.INTERNAL_SERVER_ERROR.name());
        response.put(EBase.ERROR_MESSAGE.name(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
