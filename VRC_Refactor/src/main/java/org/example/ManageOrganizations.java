package org.example;

import AbstractClassForResuableCode.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

    public class ManageOrganizations extends AbstractClass {
        // Locators
        public final By Manageorganizationsby = By.xpath("//a[contains(@href, '/organizations/') and contains(@class, 'MuiListItemButton-root')]");

        public ManageOrganizations (WebDriver driver) {
            super(driver);
        }

        // Method to navigate to the Manage Organizations page
        public void NavigateToManageorganizationsPage() {
            // Click on the "Manage Users" button
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement manageUsersButton = wait.until(ExpectedConditions.elementToBeClickable(Manageorganizationsby));
            manageUsersButton.click();

        }
    }

