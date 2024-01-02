package com.example.frontend.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EditStudentTestPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(tagName = "form")
    WebElement studentForm;

    @FindBy(id = "button")
    WebElement submitButton;

    public EditStudentTestPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public WebElement getStudentForm() {
        waitForPageLoad();
        wait.until(ExpectedConditions.visibilityOf(studentForm));
        return studentForm;
    }

    public WebElement getSubmitButton() {
        waitForPageLoad();
        wait.until(ExpectedConditions.visibilityOf(submitButton));
        return submitButton;
    }

    private void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(1)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }
}