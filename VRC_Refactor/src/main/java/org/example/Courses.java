package org.example;
import AbstractClassForResuableCode.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Courses extends AbstractClass {
    // Locators
    public final By Coursesby = By.xpath("//div[@class='MuiBox-root css-164kvcj']/p[text()='Courses']");

    public Courses(WebDriver driver) {
        super(driver);
    }
    // Method to navigate to the Courses page
    public void NavigateToCoursesPage() {
        // Click on the "Courses" button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement CoursesButton = wait.until(ExpectedConditions.elementToBeClickable(Coursesby));
        CoursesButton.click();
    }
}

