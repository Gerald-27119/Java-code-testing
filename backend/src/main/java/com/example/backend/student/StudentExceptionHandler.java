package com.example.backend.student;

import com.example.backend.exceptions.EmailTakenException;
import com.example.backend.exceptions.NoStudentsInDBException;
import com.example.backend.exceptions.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice

public class StudentExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NoStudentsInDBException.class, StudentNotFoundException.class})
    protected ResponseEntity<String> handleNoContentException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());//404 NOT FOUND
    }

    @ExceptionHandler(EmailTakenException.class)
    protected ResponseEntity<String> handleEmailTakenException(EmailTakenException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());//409 CONFLICT
    }

    @ExceptionHandler( IllegalArgumentException.class)
    protected ResponseEntity<String> handleInvalidDataException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());//400 BAD REQUEST
    }
}