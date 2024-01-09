package com.example.backend.student;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

class StudentRestassuredTest {
    private static final String URI = "http://localhost:8080/api/student";

    @Test
    void testGetStudent1() {
        when()
                .get(URI + "/1")
                .then()
                .statusCode(200)
                .log()
                .body();
    }

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
                .body("email", equalTo("john.doe@example.com"))
                .body("age", equalTo(20))
                .log()
                .body();
    }

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

    @Test
    void testPostStudent1() {
        with()
                .body(new Student("Adam", "Doe", "adamdoe@gmail.com", 20))
                .contentType(ContentType.JSON)
                .post(URI)
                .then()
                .statusCode(200);
    }

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
}
