package com.example.frontend.ui;

import com.example.frontend.ui.pages.IndexTestPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IndexTest {
    private WebDriver driver;
    private IndexTestPage indexTestPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("http://localhost:8081/welcome"); // replace with your application's URL
        indexTestPage = new IndexTestPage(driver);

    }

    @Test
    public void testCreateStudentLink() {
        WebElement createStudentLink = indexTestPage.getCreateStudentLink();
        assertTrue(createStudentLink.isDisplayed());
    }

    @Test
    public void testStudentsTable() {
        List<WebElement> students = indexTestPage.getStudents();
        assertTrue(students.size() > 0);
    }

    @Test
    public void testEditButton() {
        List<WebElement> students = indexTestPage.getStudents();
        WebElement firstStudent = students.get(0);
        WebElement editButton = indexTestPage.getEditButton(firstStudent);
        assertTrue(editButton.isDisplayed());
    }

    @Test
    public void testDeleteButton() {
        List<WebElement> students = indexTestPage.getStudents();
        WebElement firstStudent = students.get(0);
        WebElement deleteButton = indexTestPage.getDeleteButton(firstStudent);
        assertTrue(deleteButton.isDisplayed());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}