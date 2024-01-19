package com.example.frontend.web;
import com.example.frontend.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebService webService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setSurname("Doe");
        student.setEmail("john.doe@example.com");
        student.setAge(20);
    }
    @Test
    void testGetIndexView() throws Exception {
        when(webService.getAllStudents()).thenReturn(Collections.singletonList(student));

        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
    @Test
    void testShowAllStudents() throws Exception {
        when(webService.getAllStudents()).thenReturn(Collections.singletonList(student));

        mockMvc.perform(get("/welcome"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("students", hasSize(1)))
                .andExpect(model().attribute("students", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("name", is("John")),
                                hasProperty("surname", is("Doe")),
                                hasProperty("email", is("john.doe@example.com")),
                                hasProperty("age", is(20))
                        )
                )));
    }
    @Test
    void testGetEditStudent() throws Exception {
        mockMvc.perform(get("/create-student"))
                .andExpect(status().isOk())
                .andExpect(view().name("/create-student"));
    }

    @Test
    void testDeleteStudent() throws Exception {
        doNothing().when(webService).deleteStudent(anyLong());

        mockMvc.perform(post("/delete")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }

    @Test
    void testGetEditStudentView() throws Exception {
        when(webService.getStudentById(anyLong())).thenReturn(student);

        mockMvc.perform(get("/edit-student")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-student"))
                .andExpect(model().attribute("student", hasProperty("id", is(1L))))
                .andExpect(model().attribute("student", hasProperty("name", is("John"))))
                .andExpect(model().attribute("student", hasProperty("surname", is("Doe"))))
                .andExpect(model().attribute("student", hasProperty("email", is("john.doe@example.com"))))
                .andExpect(model().attribute("student", hasProperty("age", is(20))));
    }
    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("John");
        student.setSurname("Doe");
        student.setEmail("john.doe@example.com");
        student.setAge(20);


        mockMvc.perform(post("/create")
                        .flashAttr("student", student))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }

    @Test
    void testPutStudent() throws Exception {

        mockMvc.perform(post("/update")
                        .param("id", "1")
                        .param("name", "John")
                        .param("surname", "Doe")
                        .param("email", "john.doe@example.com")
                        .param("age", "20"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }


}
