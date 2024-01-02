package com.example.frontend.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class IndexTestPage {
    private WebDriver driver;

    public IndexTestPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getCreateStudentLink() {
        return driver.findElement(By.linkText("Create Student"));
    }

    public List<WebElement> getStudents() {
        return driver.findElements(By.cssSelector("table tr"));
    }

    public WebElement getEditButton(WebElement studentRow) {
        return studentRow.findElement(By.linkText("Edit"));
    }

    public WebElement getDeleteButton(WebElement studentRow) {
        return studentRow.findElement(By.cssSelector("input[type='submit']"));
    }
}