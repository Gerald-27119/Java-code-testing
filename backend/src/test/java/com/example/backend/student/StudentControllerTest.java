package com.example.backend.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;
    @Captor
    private ArgumentCaptor<Student> studentArgumentCaptor;
    @Mock
    private StudentService studentService;
    @Mock
    private StudentValidator studentValidator;
    private AutoCloseable openMocks;
    private Student student;
    private Student secondStudent;
    private List<Student> allStudents;

    @BeforeEach
    public void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        student = new Student("John", "Doe", "john.doe@example.com", 20);
        secondStudent = new Student("Anna", "Doe", "jane.doe@example.com", 20);
        allStudents = Arrays.asList(student, secondStudent);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldReturnAllStudents() {
        // Given
        given(studentService.readAll()).willReturn(allStudents);

        // When
        List<Student> actualStudents = studentController.readAll().getBody();

        // Then
        assertEquals(allStudents, actualStudents);
        Mockito.verify(studentService).readAll();
    }

    @Test
    void shouldReturnStudentById() {
        // Given
        given(studentService.read(1L)).willReturn(student);

        // When
        Student actualStudent = studentController.read(1L).getBody();

        // Then
        assertEquals(student, actualStudent);
        Mockito.verify(studentService).read(1L);
    }

    @Test
    void shouldReturnStudentByEmail() {
        // Given
        String existingEmail = student.getEmail();
        given(studentService.readByEmail(existingEmail)).willReturn(student);

        // When
        Student actualStudent = studentController.readByEmail(existingEmail).getBody();

        // Then
        assertEquals(student, actualStudent);
        Mockito.verify(studentService).readByEmail(existingEmail);
    }

    @Test
    void shouldAddNewStudent() {
        // Given
        // When
        studentService.create(student);

        // Then
        verify(studentService).create(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        assertEquals(student, capturedStudent);
    }



    @Test
    void shouldDeleteStudentById() {
        // Given
        Long existingId = student.getId();
        given(studentService.read(existingId)).willReturn(student);

        // When
        studentController.delete(existingId);

        // Then
        verify(studentService).delete(existingId);
    }

    @Test
    void shouldCreateNewStudent() {
        // given
        Student newStudent = new Student(null, "John", "Doe", "john.doe@example.com", 20);
        Student createdStudent = new Student(1L, "John", "Doe", "john.doe@example.com", 20);
        given(studentService.create(newStudent)).willReturn(createdStudent);

        // when
        ResponseEntity<Student> response = studentController.create(newStudent);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdStudent, response.getBody());
        verify(studentService).create(newStudent);
    }
    @Test
    void shouldUpdateExistingStudent() {
        // given
        Long existingId = 1L;
        Student updatedStudent = new Student(existingId, "John", "Doe", "john.doe@example.com", 21);
        given(studentService.put(updatedStudent)).willReturn(updatedStudent);

        // when
        ResponseEntity<Student> response = studentController.put(existingId, updatedStudent);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedStudent, response.getBody());
        verify(studentService).put(updatedStudent);
    }
}
