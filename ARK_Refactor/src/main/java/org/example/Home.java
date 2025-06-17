package org.example;
import AbstractClassForResuableCode.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Home extends AbstractClass {
        // Locators
        public final By HomePageBy = By.xpath("//a[@href='/home/' and contains(., 'Home')]");

        public Home(WebDriver driver) {
            super(driver);
        }

        public void NavigateToHomePage() {
            // Click on the "Manage Users" button
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement manageUsersButton = wait.until(ExpectedConditions.elementToBeClickable(HomePageBy));
            manageUsersButton.click();
        }
    }