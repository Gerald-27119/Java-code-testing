package com.example.frontend.ui;

import com.example.frontend.ui.pages.CreateStudentTestPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateStudentTest {
    private WebDriver driver;
    private CreateStudentTestPage createStudentTestPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("http://localhost:8081/create-student");
        createStudentTestPage = new CreateStudentTestPage(driver);
    }

    @Test
    @Order(1)
    public void testStudentForm() {
        WebElement studentForm = createStudentTestPage.getStudentForm();
        assertTrue(studentForm.isDisplayed());
    }

    @Test
    @Order(2)
    public void testSubmitButton() {
        WebElement submitButton = createStudentTestPage.getSubmitButton();
        assertTrue(submitButton.isDisplayed());
    }

    @Test
    @Order(3)
    public void testInputFields() {
        WebElement nameInput = driver.findElement(By.id("name"));
        WebElement surnameInput = driver.findElement(By.id("surname"));
        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement ageInput = driver.findElement(By.id("age"));

        assertTrue(nameInput.isDisplayed());
        assertTrue(surnameInput.isDisplayed());
        assertTrue(emailInput.isDisplayed());
        assertTrue(ageInput.isDisplayed());
    }


    @Test
    @Order(4)
    public void testFormSubmission() {
        WebElement nameInput = driver.findElement(By.id("name"));
        WebElement surnameInput = driver.findElement(By.id("surname"));
        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement ageInput = driver.findElement(By.id("age"));

        nameInput.sendKeys("John");
        surnameInput.sendKeys("Doe");
        emailInput.sendKeys("johnn.doe@example.com");
        ageInput.sendKeys("20");

        createStudentTestPage.getSubmitButton().click();

        String expectedUrl = "http://localhost:8081/index";
        String actualUrl = driver.getCurrentUrl();
        assertEquals(expectedUrl, actualUrl);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}