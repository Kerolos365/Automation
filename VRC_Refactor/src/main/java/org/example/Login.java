package org.example;

import AbstractClassForResuableCode.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login extends AbstractClass {
    // Locators
    public final By userEmailBy = By.cssSelector("input[placeholder='admin@genesiscreations.co']");
    public final By userPasswordBy = By.id("auth-login-v2-password");
    public final By loginButtonBy = By.cssSelector("button[type='submit']");
    public final By errorMessageBy = By.cssSelector("div[role='status']");

    public Login(WebDriver driver) {
        super(driver);
    }

    public void loginNow(String email, String password) {
        // Clear fields first (even if they appear empty)
        clearField(userEmailBy);
        clearField(userPasswordBy);

        // Enter credentials
        driver.findElement(userEmailBy).sendKeys(email);
        driver.findElement(userPasswordBy).sendKeys(password);
        driver.findElement(loginButtonBy).click();
    }

    public void goTo(String url) {
        driver.get(url);
        waitingForElementToBeVisible(userEmailBy); // Wait for page load
    }

    public String getErrorMessage() {
        try {
            waitingWebElement(driver.findElement(errorMessageBy));
            return driver.findElement(errorMessageBy).getText();
        } catch (Exception e) {
            return "";
        }
    }

    private void clearField(By locator) {
        WebElement field = driver.findElement(locator);
        field.clear();
        field.sendKeys(Keys.CONTROL + "a");
        field.sendKeys(Keys.DELETE);
    }
}