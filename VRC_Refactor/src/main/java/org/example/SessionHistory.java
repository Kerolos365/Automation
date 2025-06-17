package org.example;

import AbstractClassForResuableCode.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SessionHistory extends AbstractClass {
    // Locators
    public final By SessionHistoryby = By.xpath("//p[contains(@class, 'MuiTypography-body1') and contains(text(), 'Sessions History')]");
    public SessionHistory(WebDriver driver) {
        super(driver);
    }

    // Method to navigate to the Session History page
    public void NavigateToSessionHistoryPage() {
        // Click on the "Session History" button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement SessionHistoryButton = wait.until(ExpectedConditions.elementToBeClickable(SessionHistoryby));
        SessionHistoryButton.click();
    }
}

