package com.example.backend.student;


import com.example.backend.exceptions.NoStudentsInDBException;
import com.example.backend.exceptions.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentValidator studentValidator;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentValidator studentValidator) {
        this.studentRepository = studentRepository;
        this.studentValidator = studentValidator;
    }

    public Student create(Student student) {
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Student with email " + student.getEmail() + " already exists");
        }
        studentValidator.validatePotentialStudent(student);
        return studentRepository.save(student);
    }

    public Student read(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + id + " not found"));
    }

    public Student readByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new StudentNotFoundException("Student with email " + email + " does not exist"));
    }

    public List<Student> readAll() {
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            throw new NoStudentsInDBException("There are no students in DB");
        }
        return students;
    }

    @Transactional
    public Student put(Student updatedStudent) {
        Long id = updatedStudent.getId();
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + id + " does not exist"));

        studentRepository.findByEmail(updatedStudent.getEmail())
                .ifPresent(existingStudent -> {
                    if (!existingStudent.getId().equals(updatedStudent.getId())) {
                        throw new IllegalArgumentException("Email " + updatedStudent.getEmail() + " is already in use");}});
        studentValidator.validateChangesToStudentBeforeUpdate(updatedStudent);
        student.setName(updatedStudent.getName());
        student.setSurname(updatedStudent.getSurname());
        student.setEmail(updatedStudent.getEmail());
        student.setAge(updatedStudent.getAge());
        return studentRepository.save(student);
    }

    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student with id " + id + " does not exists");
        }
        studentRepository.deleteById(id);
    }
}