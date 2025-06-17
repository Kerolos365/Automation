
package org.example;

import AbstractClassForResuableCode.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ManageLicenses extends AbstractClass {
    // Locators
    public final By ManageLicensesby = By.xpath("//a[contains(@href, '/licenses/') and contains(@class, 'MuiButtonBase-root')]");

    public ManageLicenses(WebDriver driver) {
        super(driver);
    }

    // Method to navigate to the Manage Licenses page
    public void NavigateToManageLicensesPage() {
        // Click on the "Manage Licenses" button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement manageLicensesButton = wait.until(ExpectedConditions.elementToBeClickable(ManageLicensesby));
        manageLicensesButton.click();
    }
}

