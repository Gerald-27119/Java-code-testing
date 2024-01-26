package com.example.frontend.ui;

import com.example.frontend.ui.pages.IndexTestPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IndexTest {
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


    @Test
    @Order(5)
    public void testTableHeaders() {
        List<WebElement> tableHeaders = indexTestPage.getTableHeaders();
        assertEquals(7, tableHeaders.size());
        assertEquals("Id", tableHeaders.get(0).getText());
        assertEquals("Name", tableHeaders.get(1).getText());
        assertEquals("Surname", tableHeaders.get(2).getText());
        assertEquals("Email", tableHeaders.get(3).getText());
        assertEquals("Age", tableHeaders.get(4).getText());
        assertEquals("Edit", tableHeaders.get(5).getText());
        assertEquals("Delete", tableHeaders.get(6).getText());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}