package com.example.backend.student;

import org.springframework.stereotype.Component;

@Component
public class StudentValidator {

    public void validateChangesToStudentBeforeUpdate(Student updatedStudent) {
        validateField(updatedStudent.getName(), "name");
        validateField(updatedStudent.getSurname(), "surname");
        validateField(updatedStudent.getEmail(),  "email");
        validateField(updatedStudent.getAge(),  "age");
    }

    public void validatePotentialStudent(Student student) {
        validateField(student.getName(), "name");
        validateField(student.getSurname(), "surname");
        validateField(student.getEmail(), "email");
        validateField(student.getAge(), "age");
    }


    public void validateField(Object field, String fieldName) {
        if (field == null || isFieldInvalid(field, fieldName)) {
            throw new IllegalArgumentException("Invalid " + fieldName);
        }
    }

    public boolean isFieldInvalid(Object field, String fieldName) {
        if (field == null) {
            return true;
        }
        return switch (fieldName) {
            case "name", "surname" ->
                    field.toString().trim().isEmpty() || !field.toString().matches("\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+");
            case "email" ->
                    field.toString().trim().isEmpty() || !field.toString().matches("(?i)^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$");
            case "age" -> field.toString().trim().isEmpty() || (Integer) field < 18 || (Integer) field > 100;
            default -> true;
        };
    }

}