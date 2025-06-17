package org.example;

import AbstractClassForResuableCode.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class VersionConfigurations extends AbstractClass {
    // Locators
    public final By VersionConfigurationsby = By.xpath("//p[contains(@class, 'MuiTypography-body1') and contains(text(), 'Version Configurations')]");
    public VersionConfigurations(WebDriver driver) {
        super(driver);
    }

    // Method to navigate to the Version Configurations page
    public void NavigateToVersionConfigurationsPage() {
        // Click on the "Version Configurations" button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement VersionConfigurationsButton = wait.until(ExpectedConditions.elementToBeClickable(VersionConfigurationsby));
        VersionConfigurationsButton.click();
    }
}

