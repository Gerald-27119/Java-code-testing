package com.example.frontend.ui;

import com.example.frontend.ui.pages.IndexTestPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IndexTest {
    private WebDriver driver;
    private IndexTestPage indexTestPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("http://localhost:8081/welcome");
        indexTestPage = new IndexTestPage(driver);

    }

    @Test
    @Order(1)
    public void testCreateStudentLink() {
        WebElement createStudentLink = indexTestPage.getCreateStudentLink();
        assertTrue(createStudentLink.isDisplayed());
    }

    @Test
    @Order(2)
    public void testStudentsTable() {
        List<WebElement> students = indexTestPage.getStudents();
        assertFalse(students.isEmpty());
    }

    @Test
    @Order(3)
    public void testEditButton() {
        WebElement editButton = indexTestPage.getEditButton(0);
        assertTrue(editButton.isDisplayed());
    }

    @Test
    @Order(4)
    public void testDeleteButton() {
        WebElement deleteButton = indexTestPage.getDeleteButton();
        assertTrue(deleteButton.isDisplayed());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}