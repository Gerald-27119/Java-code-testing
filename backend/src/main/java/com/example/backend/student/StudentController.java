package com.example.backend.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> create(@RequestBody Student student) {
        Student createdStudent = studentService.create(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Student> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(studentService.read(id));
    }

    @GetMapping(path = "email/{email}")
    public ResponseEntity<Student> readByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(studentService.readByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<Student>> readAll() {
        return ResponseEntity.ok(studentService.readAll());
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Student> put(@PathVariable("id") Long id, @RequestBody Student updatedStudent) {
        updatedStudent.setId(id);
        Student updated = studentService.put(updatedStudent);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}