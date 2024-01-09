package com.example.frontend.web;

import com.example.frontend.model.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest
class WebServiceTest {
    MockServerRestTemplateCustomizer mockServerRestTemplateCustomizer = new MockServerRestTemplateCustomizer();
    RestTemplate restTemplate = new RestTemplate();
    WebService webservice;

    @BeforeEach
    void setUp() {
        mockServerRestTemplateCustomizer.customize(restTemplate);
        webservice = new WebService();
    }

    @Test
    void testGetStudentById() throws JsonProcessingException {
        // Create a Student object
        Student student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setSurname("Doe");
        student.setEmail("john.doe@example.com");

        // Convert the Student object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String studentJson = objectMapper.writeValueAsString(student);

        // Use the JSON in the withSuccess method
        mockServerRestTemplateCustomizer.getServer().expect(requestTo("/api/student/1"))
                .andRespond(withSuccess(studentJson, MediaType.APPLICATION_JSON));
    }

}
