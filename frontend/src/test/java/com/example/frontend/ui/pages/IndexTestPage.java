package com.example.frontend.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.time.Duration;
import java.util.List;

public class IndexTestPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = ".editButton")
    List<WebElement> editButtons;


    @FindBy(id = "deleteForm")
    WebElement deleteForm;

    public IndexTestPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public WebElement getCreateStudentLink() {
        return driver.findElement(By.linkText("Create Student"));
    }

    public List<WebElement> getStudents() {
        return driver.findElements(By.cssSelector("table tr"));
    }

    public WebElement getEditButton(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(editButtons));
        return editButtons.get(index);
    }

    public WebElement getDeleteButton() {
        wait.until(ExpectedConditions.visibilityOf(deleteForm));
        return deleteForm.findElement(By.cssSelector("input[type='submit']"));
    }
}