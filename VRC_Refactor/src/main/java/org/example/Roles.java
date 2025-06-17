package org.example;

import AbstractClassForResuableCode.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Roles extends AbstractClass {
    // Locators
    public final By Rolesby = By.xpath("//a[contains(@href, '/roles/') and contains(@class, 'MuiListItemButton-root')]");


    public Roles(WebDriver driver) {
        super(driver);
    }

    // Method to navigate to the Roles page
    public void NavigateToRolesPage() {
        // Click on the "Roles" button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement RolesButton = wait.until(ExpectedConditions.elementToBeClickable(Rolesby));
        RolesButton.click();
    }
}

