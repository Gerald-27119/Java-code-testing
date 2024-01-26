package com.example.backend.student;

import com.example.backend.BackendApplication;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

class StudentRestassuredTest {
    private static final String URI = "http://localhost:8080/api/student";
    private static ConfigurableApplicationContext context;

    @BeforeAll
    static void start() {
        context = SpringApplication.run(BackendApplication.class);
    }
    @AfterEach
    void resetStudentName() {
        Long idToUpdate = 1L;
        Student originalStudent = new Student("John", "Doe", "john.doe@gmail.com", 20);
        originalStudent.setId(idToUpdate);

        given()
                .contentType(ContentType.JSON)
                .body(originalStudent)
                .when()
                .put(URI + "/" + idToUpdate);
    }
    @AfterAll
    static void stop() {
        if (context != null) {
            context.close();
        }
    }
    @Order(1)
    @Test
    void testGetStudent1() {
        when()
                .get(URI + "/1")
                .then()
                .statusCode(200)
                .log()
                .body();
    }
    @Order(2)
    @Test
    void testGetStudent2() {
        when()
                .get(URI + "/1")
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(1))
                .body("name", equalTo("John"))
                .body("surname", equalTo("Doe"))
                .body("email", equalTo("john.doe@gmail.com"))
                .body("age", equalTo(20))
                .log()
                .body();
    }
    @Order(3)
    @Test
    void testGetStudent3() {
        Student student = when()
                .get(URI + "/1")
                .then()
                .statusCode(200)
                .extract()
                .as(Student.class);

        Assertions.assertEquals(1L, student.getId());
        Assertions.assertEquals("John", student.getName());

    }
    @Order(4)
    @Test
    void testPostStudent1() {
        with()
                .body(new Student("Adam", "Doe", "adamdoe@gmail.com", 20))
                .contentType(ContentType.JSON)
                .post(URI)
                .then()
                .statusCode(200);
    }
    @Order(5)
    @Test
    void testPostStudent2() {
        with()
                .body(new Student("Adamm", "Doe", "adammdoe@gmail.com", 20))
                .contentType(ContentType.JSON)
                .post(URI)
                .then()
                .assertThat()
                .body("id", greaterThan(4))
                .body("name", equalTo("Adamm"))
                .statusCode(200);
    }
    @Order(6)
    @Test
    void testGetAllStudents() {
        when()
                .get(URI)
                .then()
                .statusCode(200)
                .assertThat()
                .body("size()", greaterThan(0))
                .log()
                .body();
    }
    @Order(7)
    @Test
    void testDeleteStudent() {
        long idToDelete = 1L;
        when()
                .delete(URI + "/" + idToDelete)
                .then()
                .statusCode(204);
    }
    @Order(8)
    @Test
    void testUpdateStudentName() {
        Long idToUpdate = 1L;
        String newName = "UpdatedName";
        Student updatedStudent = new Student(newName, "Doe", "johndoe@gmail.com", 20);
        updatedStudent.setId(idToUpdate);

        given()
                .contentType(ContentType.JSON)
                .body(updatedStudent)
                .when()
                .put(URI + "/" + idToUpdate)
                .then()
                .statusCode(200)
                .assertThat()
                .body("name", equalTo(newName));
    }
}
