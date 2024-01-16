package com.example.frontend.web;

import com.example.frontend.model.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestTemplateCustomizer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
//https://www.baeldung.com/spring-boot-testresttemplate
class WebServiceTest {
    MockServerRestTemplateCustomizer mockServerRestTemplateCustomizer = new MockServerRestTemplateCustomizer();
    RestTemplate restTemplate = new RestTemplate();
//    RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    private final static String studentControllerBaseUrl = "http://localhost:8080/api/student";
    WebService webservice;

    @BeforeEach
    void setUp() {
        mockServerRestTemplateCustomizer.customize(restTemplate);
        webservice = new WebService(restTemplate);
    }

    @Test
    void testGetStudentById() throws JsonProcessingException {
        Student student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setSurname("Doe");
        student.setEmail("john.doe@example.com");
        ObjectMapper objectMapper = new ObjectMapper();
        String studentJson = objectMapper.writeValueAsString(student);

        mockServerRestTemplateCustomizer.getServer().expect(requestTo("http://localhost:8080/api/student/1"))
                .andRespond(withSuccess(studentJson, MediaType.APPLICATION_JSON));
        Student studentDto = webservice.getStudentById(1L);
        assertEquals(studentDto.getName(), student.getName());
    }


}
