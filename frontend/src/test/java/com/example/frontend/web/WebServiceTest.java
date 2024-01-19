package com.example.frontend.web;

import com.example.frontend.model.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class WebServiceTest {
    MockServerRestTemplateCustomizer mockServerRestTemplateCustomizer = new MockServerRestTemplateCustomizer();
    RestTemplate restTemplate = new RestTemplate();
    private final static String studentControllerBaseUrl = "http://localhost:8080/api/student";
    WebService webservice;
    Student student1;
    Student student2;
    String studentJson1;
    String studentJson2;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        mockServerRestTemplateCustomizer.customize(restTemplate);
        webservice = new WebService(restTemplate);

        student1 = new Student();
        student1.setId(1L);
        student1.setName("John");
        student1.setSurname("Doe");
        student1.setEmail("john.doe@example.com");
        student1.setAge(20);

        student2 = new Student();
        student2.setId(2L);
        student2.setName("Jane");
        student2.setSurname("Doe");
        student2.setEmail("jane.doe@example.com");
        student2.setAge(22);

        ObjectMapper objectMapper = new ObjectMapper();
        studentJson1 = objectMapper.writeValueAsString(student1);
        studentJson2 = objectMapper.writeValueAsString(student2);
    }

    @Test
    void testGetAllStudents() {
        // Given
        mockServerRestTemplateCustomizer.getServer().expect(requestTo(studentControllerBaseUrl))
                .andRespond(withSuccess("[" + studentJson1 + "," + studentJson2 + "]", MediaType.APPLICATION_JSON));

        // When
        var response = restTemplate.exchange(
                studentControllerBaseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });

        // Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        List<Student> students = response.getBody();
        assertTrue(students.containsAll(asList(student1, student2)));
    }

    @Test
    void testGetStudentById() {
        // given
        mockServerRestTemplateCustomizer.getServer().expect(requestTo(studentControllerBaseUrl))
                .andRespond(withSuccess(studentJson1, MediaType.APPLICATION_JSON));

        // When
        ResponseEntity<Student> response = restTemplate.getForEntity(studentControllerBaseUrl, Student.class);

        // Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(student1, response.getBody());
    }


    @Test
    void testPostStudent() {
        // Given
        mockServerRestTemplateCustomizer.getServer().expect(requestTo(studentControllerBaseUrl))
                .andRespond(withSuccess(studentJson1, MediaType.APPLICATION_JSON));

        // When
        ResponseEntity<Student> response = restTemplate.postForEntity(studentControllerBaseUrl, student1, Student.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student1, response.getBody());
    }



    @Test
    void testUpdateStudent() throws JsonProcessingException {
        // Given
        Student updatedStudent = new Student();
        updatedStudent.setId(student1.getId());
        updatedStudent.setName("Updated Name");
        updatedStudent.setSurname("Updated Surname");
        updatedStudent.setEmail("updated.email@example.com");
        updatedStudent.setAge(21);

        String updatedStudentJson = new ObjectMapper().writeValueAsString(updatedStudent);

        mockServerRestTemplateCustomizer.getServer().expect(requestTo(studentControllerBaseUrl + "/" + updatedStudent.getId()))
                .andRespond(withSuccess(updatedStudentJson, MediaType.APPLICATION_JSON));

        // When
        ResponseEntity<Student> response = restTemplate.exchange(
                studentControllerBaseUrl + "/" + updatedStudent.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(updatedStudent),
                Student.class);

        // Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(updatedStudent, response.getBody());
    }
    @Test
    void testDeleteStudent() {
        // Given
        mockServerRestTemplateCustomizer.getServer().expect(requestTo(studentControllerBaseUrl + "/" + student1.getId()))
                .andRespond(withSuccess());

        // When
        ResponseEntity<Void> response = restTemplate.exchange(
                studentControllerBaseUrl + "/" + student1.getId(),
                HttpMethod.DELETE,
                null,
                Void.class);

        // Then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}