package com.example.backend.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentValidatorTest {
    private StudentValidator studentValidator;

    @BeforeEach
    public void init() {
        studentValidator = new StudentValidator();
    }

    @Test
    public void testValidateChangesToStudentBeforeUpdateWithValidStudent() {
        Student updatedStudent = new Student(1L,"John", "Doe", "john.doe@example.com", 20);
        assertDoesNotThrow(() -> studentValidator.validateChangesToStudentBeforeUpdate(updatedStudent));
    }

    @Test
    public void testValidateChangesToStudentBeforeUpdateWithInvalidStudent() {
        Student updatedStudent = new Student("", "", "invalid", 17);
        assertThrows(IllegalArgumentException.class, () -> studentValidator.validateChangesToStudentBeforeUpdate(updatedStudent));
    }

    @Test
    public void testValidatePotentialStudentWithValidStudent() {
        Student student = new Student(1L,"John", "Doe", "john.doe@example.com", 20);
        assertDoesNotThrow(() -> studentValidator.validatePotentialStudent(student));
    }

    @Test
    public void testValidatePotentialStudentWithInvalidStudent() {
        Student student = new Student("", "", "invalid", 17);
        assertThrows(IllegalArgumentException.class, () -> studentValidator.validatePotentialStudent(student));
    }
    @Test
    public void testValidateFIeldIsNull() {
        Student student = new Student("", "", "invalid", 17);
        assertThrows(IllegalArgumentException.class, () -> studentValidator.validatePotentialStudent(student));
    }
    @Test
    public void testValidateFIeldIsEmpty() {
        assertTrue(studentValidator.isFieldInvalid(null,null));
    }
    @Test
    public void testValidateFIeldIsEmptySecondWay() {
        assertTrue(studentValidator.isFieldInvalid(new Object(),"null"));
    }
}