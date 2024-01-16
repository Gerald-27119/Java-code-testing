package com.example.frontend.web;


import com.example.frontend.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WebService {
    @Autowired
    private final RestTemplate restTemplate;
    private final String studentControllerBaseUrl = "http://localhost:8080/api/student";


    public WebService(RestTemplate restTemplate){
        this.restTemplate = new RestTemplate();
    }

    public void createStudent(Student student) {
        restTemplate.postForEntity(studentControllerBaseUrl, student, Student.class);
    }

    public void updateStudent(Student updatedStudent) {
        restTemplate.put(studentControllerBaseUrl + "/" + updatedStudent.getId(), updatedStudent);
    }

    public void deleteStudent(Long id) {
        restTemplate.delete(studentControllerBaseUrl + "/" + id);
    }

    public List getAllStudents() {
        return restTemplate.getForObject(studentControllerBaseUrl, List.class);
    }

    public Student getStudentById(Long id) {
        return restTemplate.getForObject(studentControllerBaseUrl + "/" + id, Student.class);
    }
}