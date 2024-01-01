package com.example.backend.student;


import com.example.backend.exceptions.EmailTakenException;
import com.example.backend.exceptions.NoStudentsInDBException;
import com.example.backend.exceptions.StudentNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentExceptionHandlerTest {

    private final StudentExceptionHandler studentExceptionHandler = new StudentExceptionHandler();

    @Test
    void shouldReturnNotFoundWhenNoStudentsInDBException() {
        // Given
        NoStudentsInDBException noStudentsInDBException = new NoStudentsInDBException("No students in DB");
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
        // When
        ResponseEntity<String> responseEntity = studentExceptionHandler.handleNoContentException(noStudentsInDBException);
        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    @Test
    void shouldReturnNotFoundWhenStudentNotFoundException() {
        // Given
        StudentNotFoundException studentNotFoundException = new StudentNotFoundException("Student not found");
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
        // When
        ResponseEntity<String> responseEntity = studentExceptionHandler.handleNoContentException(studentNotFoundException);
        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenIllegalArgumentException() {
        // Given
        IllegalArgumentException IllegalArgumentException = new IllegalArgumentException("Invalid name");
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
        // When
        ResponseEntity<String> responseEntity = studentExceptionHandler.handleInvalidDataException(IllegalArgumentException);
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    void shouldReturnBadRequestWhenInvalidSurnameException() {
        // Given
        IllegalArgumentException IllegalArgumentException = new IllegalArgumentException("Invalid surname");
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
        // When
        ResponseEntity<String> responseEntity = studentExceptionHandler.handleInvalidDataException(IllegalArgumentException);
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    void shouldReturnBadRequestWhenInvalidEmailException() {
        // Given
        IllegalArgumentException IllegalArgumentException = new IllegalArgumentException("Invalid email");
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
        // When
        ResponseEntity<String> responseEntity = studentExceptionHandler.handleInvalidDataException(IllegalArgumentException);
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    void shouldReturnBadRequestWhenInvalidAgeException() {
        // Given
        IllegalArgumentException IllegalArgumentException = new IllegalArgumentException("Invalid age");
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
        // When
        ResponseEntity<String> responseEntity = studentExceptionHandler.handleInvalidDataException(IllegalArgumentException);
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    void shouldReturnBadRequestWhenMissingStudentSurnameException() {
        // Given
        IllegalArgumentException IllegalArgumentException = new IllegalArgumentException("Missing student surname");
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
        // When
        ResponseEntity<String> responseEntity = studentExceptionHandler.handleInvalidDataException(IllegalArgumentException);
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    void shouldReturnBadRequestWhenMissingStudentEmailException() {
        // Given
        IllegalArgumentException IllegalArgumentException = new IllegalArgumentException("Missing student email");
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
        // When
        ResponseEntity<String> responseEntity = studentExceptionHandler.handleInvalidDataException(IllegalArgumentException);
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    void shouldReturnBadRequestWhenMissingStudentAgeException() {
        // Given
        IllegalArgumentException IllegalArgumentException = new IllegalArgumentException("Missing student age");
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
        // When
        ResponseEntity<String> responseEntity = studentExceptionHandler.handleInvalidDataException(IllegalArgumentException);
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    void shouldReturnBadRequestWhenMissingStudentNameException() {
        // Given
        IllegalArgumentException IllegalArgumentException = new IllegalArgumentException("Missing student name");
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
        // When
        ResponseEntity<String> responseEntity = studentExceptionHandler.handleInvalidDataException(IllegalArgumentException);
        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    @Test
    void shouldReturnBadRequestWhenInvalidDataException() {
        // Given
        IllegalArgumentException IllegalArgumentException = new IllegalArgumentException("Invalid name");
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());

        // When
        ResponseEntity<String> responseEntity = studentExceptionHandler.handleInvalidDataException(IllegalArgumentException);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}