package com.example.backend.student;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
class StudentRepositoryTest {
    private final Student student = new Student("John", "Doe", "joe.doe@example.com", 20);
    @Test
    void shouldFindStudentByEmail() {
        //given
        StudentRepository studentRepository = Mockito.mock(StudentRepository.class);
        given(studentRepository.findByEmail(student.getEmail())).willReturn(Optional.of(student));
        //when
        Optional<Student> studentOptional = studentRepository.findByEmail(student.getEmail());
        //then
        Assertions.assertThat(studentOptional).contains(student);
    }
}
