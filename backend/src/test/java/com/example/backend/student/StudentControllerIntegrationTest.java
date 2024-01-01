package com.example.backend.student;


import com.example.backend.exceptions.StudentNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StudentControllerIntegrationTest {
    private static final String END_POINT_PATH = "/api/student/";
    @InjectMocks
    private StudentController studentController;
    @Captor
    private ArgumentCaptor<Student> studentArgumentCaptor;
    @Mock
    private StudentService studentService;
    @Mock
    private StudentExceptionHandler studentExceptionHandler;
    @Mock
    private StudentValidator studentValidator;
    private AutoCloseable openMocks;
    @ExtendWith(MockitoExtension.class)
    private MockMvc mockMvc;

    private Student student;
    private String studentJson;
    private Student secondStudent;
    private List<Student> allStudents;

    @BeforeEach
    public void init() throws JsonProcessingException {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new StudentExceptionHandler(), studentController).build();
        student = new Student("John", "Doe", "jone.doe@example.com", 20);
        secondStudent = new Student("Anna", "Doe", "jane.doe@example.com", 21);
        studentJson = new ObjectMapper().writeValueAsString(student);
        allStudents = Arrays.asList(student, secondStudent);
    }




    @Test
    void getStudentByIdShouldReturn404IfStudentWithGivenIdDoesntExist() throws Exception {
        // Given
        Long nonExistentId = 1L;
        given(studentService.read(nonExistentId)).willThrow(new StudentNotFoundException("Student not found"));
        // When & Then
        mockMvc.perform(get(END_POINT_PATH + "{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }
    @Test
    void getStudentByEmailShouldReturn404IfStudentWithGivenEmailDoesntExist() throws Exception {
        // Given
        String nonExistentEmail = "nonExistentEmail";
        given(studentService.readByEmail(nonExistentEmail)).willThrow(new StudentNotFoundException("Student not found"));
        // When & Then
        mockMvc.perform(get(END_POINT_PATH + "email/{email}", nonExistentEmail))
                .andExpect(status().isNotFound());
    }
    @Test
    void getAllStudentsShouldReturn404IfThereAreNoStudentsInDB() throws Exception {
        // Given
        given(studentService.readAll()).willThrow(new StudentNotFoundException("There are no students in DB"));
        // When & Then
        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isNotFound());
    }
    @Test
    void createStudentShouldReturn409IfStudentWithGivenEmailAlreadyExists() throws Exception {
        // Given
        given(studentService.create(student)).willThrow(new IllegalArgumentException("Student with email " + student.getEmail() + " already exists"));
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH)
                .contentType("application/json")
                .content(studentJson))
                .andExpect(status().isBadRequest());
    }
    @Test
    void createStudentShouldReturn400IfStudentDataIsInvalid() throws Exception {
        // Given
        given(studentService.create(student)).willThrow(new IllegalArgumentException("Student with email " + student.getEmail() + " already exists"));
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post(END_POINT_PATH)
                .contentType("application/json")
                .content(studentJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStudentShouldReturn409IfStudentWithGivenEmailAlreadyExists() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(END_POINT_PATH + "{id}", student.getId())
                .contentType("application/json")
                .content(studentJson))
                .andExpect(status().isMethodNotAllowed());
    }
    @Test
    void updateStudentShouldReturn400IfStudentDataIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(END_POINT_PATH + "{id}", student.getId())
                .contentType("application/json")
                .content(studentJson))
                .andExpect(status().is4xxClientError());
    }
    @Test
    void deleteStudentShouldReturn204() throws Exception {
        // Given
        Long nonExistentId = 1L;
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete(END_POINT_PATH + "{id}", nonExistentId))
                .andExpect(status().is2xxSuccessful());
    }



}
