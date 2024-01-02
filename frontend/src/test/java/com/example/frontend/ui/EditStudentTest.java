package com.example.frontend.ui;

import com.example.frontend.model.Student;
import com.example.frontend.ui.pages.EditStudentTestPage;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EditStudentTest {
    private WebDriver driver;
    private EditStudentTestPage editStudentTestPage;
    private Student mockStudent;

    @BeforeEach
    public void setUp() {
        mockStudent = Mockito.mock(Student.class);
        when(mockStudent.getId()).thenReturn(1L);
        driver = new ChromeDriver();
        driver.get("http://localhost:8081/edit-student?id=" + mockStudent.getId());
        editStudentTestPage = new EditStudentTestPage(driver);
    }

    @Test
    @Order(1)
    public void testStudentForm() {
        WebElement studentForm = editStudentTestPage.getStudentForm();
        assertTrue(studentForm.isDisplayed());
    }

    @Test
    @Order(2)
    public void testSubmitButton() {
        WebElement submitButton = editStudentTestPage.getSubmitButton();
        assertTrue(submitButton.isDisplayed());
    }
    @Test
    @Order(3)
    public void testFormSubmissionWithNewData() {

        WebElement nameInput = driver.findElement(By.id("name"));
        WebElement surnameInput = driver.findElement(By.id("surname"));
        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement ageInput = driver.findElement(By.id("age"));

        nameInput.clear();
        surnameInput.clear();
        emailInput.clear();
        ageInput.clear();
        nameInput.sendKeys("John");
        surnameInput.sendKeys("Doe");
        emailInput.sendKeys("john.doe@example.com");
        ageInput.sendKeys("20");

        editStudentTestPage.getSubmitButton().click();

        String expectedUrl = "http://localhost:8081/index";
        String actualUrl = driver.getCurrentUrl();
        assertEquals(expectedUrl, actualUrl);
    }
    @Test
    @Order(4)
    public void testFormSubmission() {

        editStudentTestPage.getSubmitButton().click();

        String expectedUrl = "http://localhost:8081/index";
        String actualUrl = driver.getCurrentUrl();
        assertEquals(expectedUrl, actualUrl);
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}