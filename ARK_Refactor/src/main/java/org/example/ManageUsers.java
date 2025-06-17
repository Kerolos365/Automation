package org.example;

import AbstractClassForResuableCode.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ManageUsers extends AbstractClass {
    // Locators
    public final By ManageUsersby = By.xpath("//a[contains(@href, '/users/') and contains(@class, 'MuiListItemButton-root')]");

    public ManageUsers(WebDriver driver) {
        super(driver);
    }

    // Method to navigate to the Manage Organizations page
    public void NavigateToManageUsersPage() {
        // Click on the "Manage Users" button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement manageUsersButton = wait.until(ExpectedConditions.elementToBeClickable(ManageUsersby));
        manageUsersButton.click();
    }
}

