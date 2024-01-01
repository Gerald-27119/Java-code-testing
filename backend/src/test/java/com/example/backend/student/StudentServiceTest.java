package com.example.backend.student;


import com.example.backend.exceptions.NoStudentsInDBException;
import com.example.backend.exceptions.StudentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentServiceTest {
    @InjectMocks
    private StudentService studentService;
    @Captor
    private ArgumentCaptor<Student> studentArgumentCaptor;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentValidator studentValidator;
    private AutoCloseable openMocks;
    private Student student;
    private Student secondStudent;
    private List<Student> allStudents;
    @BeforeEach
    public void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        student = new Student(1L,"John", "Doe", "john.doe@example.com", 20);
        secondStudent = new Student(1L,"Anna", "Doe", "jane.doe@example.com", 20);
        allStudents = Arrays.asList(student, secondStudent);
    }

    @AfterEach
    public void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldReadAllStudents() {
        //given
        given(studentRepository.findAll()).willReturn(allStudents);

        //when
        List<Student> students = studentService.readAll();

        //then
        verify(studentRepository).findAll();
        assertEquals(allStudents, students);
    }
    @Test
    void shouldReadStudentById() {
        //given
        given(studentRepository.findById(1L)).willReturn(Optional.of(student));

        //when
        Student studentById = studentService.read(1L);

        //then
        verify(studentRepository).findById(1L);
        assertEquals(student, studentById);
    }
    @Test
    void shouldReadStudentByEmail() {
        //given
        given(studentRepository.findByEmail(student.getEmail())).willReturn(Optional.of(student));

        //when
        Student studentByEmail = studentService.readByEmail(student.getEmail());

        //then
        verify(studentRepository).findByEmail(student.getEmail());
        assertEquals(student, studentByEmail);
    }

    @Test
    void shouldDeleteStudent() {
        //given
        given(studentRepository.existsById(student.getId())).willReturn(true);

        //when
        studentService.delete(student.getId());

        //then
        verify(studentRepository).existsById(student.getId());
        verify(studentRepository).deleteById(student.getId());
    }

    @Test
    void shouldCreateStudent() {
        //given
        given(studentRepository.save(student)).willReturn(student);

        //when
        studentService.create(student);

        //then
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        assertEquals(student, capturedStudent);
    }

    @Test
    void shouldThrowExceptionWhenStudentNotFound() {
        // given
        Long nonExistentId = 1L;
        Student updatedStudent = new Student(nonExistentId, "John", "Doe", "john.doe@example.com", 20);
        when(studentRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // then
        assertThrows(StudentNotFoundException.class, () -> studentService.put(updatedStudent));
    }

    @Test
    void shouldUpdateStudentWhenStudentExists() {
        // given
        Long existingId = 1L;
        Student existingStudent = new Student(existingId, "John", "Doe", "john.doe@example.com", 20);
        Student updatedStudent = new Student(existingId, "John", "Doe", "john.doe@example.com", 21);
        when(studentRepository.findById(existingId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(updatedStudent)).thenReturn(updatedStudent);

        // when
        Student result = studentService.put(updatedStudent);

        // then
        verify(studentRepository).save(updatedStudent);
        assertEquals(updatedStudent, result);
    }
    @Test
    void shouldThrowExceptionWhenEmailIsTaken() {
        // given
        Long existingId = 1L;
        Student existingStudent = new Student(existingId, "John", "Doe", "john.doe@example.com", 20);
        Student updatedStudent = new Student(existingId, "John", "Doe", "jane.doe@example.com", 21);
        Student anotherStudent = new Student(2L, "Jane", "Doe", "jane.doe@example.com", 22);
        when(studentRepository.findById(existingId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.findByEmail(updatedStudent.getEmail())).thenReturn(Optional.of(anotherStudent));

        // then
        assertThrows(IllegalArgumentException.class, () -> studentService.put(updatedStudent));
    }
    @Test
    void shouldThrowExceptionWhenDeletingNonExistentStudent() {
        // given
        Long nonExistentId = 1L;
        when(studentRepository.existsById(nonExistentId)).thenReturn(false);

        // then
        assertThrows(StudentNotFoundException.class, () -> studentService.delete(nonExistentId));
    }
    @Test
    void shouldThrowExceptionWhenEmailIsTakenDuringCreation() {
        // given
        Student newStudent = new Student(null, "John", "Doe", "john.doe@example.com", 20);
        Student existingStudent = new Student(1L, "Jane", "Doe", "john.doe@example.com", 22);
        when(studentRepository.findByEmail(newStudent.getEmail())).thenReturn(Optional.of(existingStudent));

        // then
        assertThrows(IllegalArgumentException.class, () -> studentService.create(newStudent));
    }
    @Test
    void shouldThrowExceptionWhenNoStudentsInDB() {
        // given
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());

        // then
        assertThrows(NoStudentsInDBException.class, studentService::readAll);
    }
}