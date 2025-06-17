package TestCases;

import TestComponent.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.IOException;
import java.time.Duration;

public class LoginPage extends BaseTest {

    @Test(priority = 1, description = "Successful login with valid credentials")
    public void successfulLogin() throws IOException, InterruptedException {
        // 1. Navigate and login
        login.goTo(LOGIN_URL);
        login.loginNow(VALID_EMAIL, VALID_PASSWORD);

        // 2. Wait and verify (using your existing wait approach)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/home"));

        // 3. Assertion
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/home"),
                "Expected to contain '/home' but got: " + currentUrl);
    }

    @Test(priority = 2, description = "Verify login with invalid credentials")
    public void invalidLogin() throws IOException {
        // 1. Navigate and login with invalid creds
        login.goTo(LOGIN_URL);
        login.loginNow("wrong@email.com", "wrongPassword");

        // 2. Verify error (using your existing approach)
        String error = login.getErrorMessage();
        Assert.assertFalse(error.isEmpty(), "No error message displayed");
        Assert.assertTrue(error.toLowerCase().contains("invalid") ||
                        error.toLowerCase().contains("incorrect"),
                "Unexpected error message: " + error);
    }
    @Test(priority = 3, description = "press Login with empty credentials")
    public void emptyLogin() throws IOException {
        // 1. Navigate and login with empty creds
        login.goTo(LOGIN_URL);
        login.loginNow("", "");

        // 2. Verify error (using your existing approach)
        String currentUrl = driver.getCurrentUrl();
        Assert.assertFalse(currentUrl.contains("/home"));
    }
    @Test(priority = 4, description = "Verify password visibility toggle functionality")
    public void testPasswordVisibilityToggle() throws Exception {
        // 1. Navigate to login page
        login.goTo(LOGIN_URL);

        // 2. Locate password field and eye icon
        WebElement passwordField = driver.findElement(By.id("auth-login-v2-password"));
        WebElement visibilityToggle = driver.findElement(
                By.xpath("//*[local-name()='svg' and contains(@class,'iconify--mdi')]"));

        // 3. Verify password is masked by default
        passwordField.sendKeys(VALID_PASSWORD);
        Assert.assertEquals(passwordField.getAttribute("type"), "password",
                "Password field should be masked by default");

        // 4. Click the visibility toggle
        visibilityToggle.click();
        Thread.sleep(2000); // Optional: Wait for the toggle to take effect

        // 5. Verify password becomes visible
        Assert.assertEquals(passwordField.getAttribute("type"), "text",
                "Password field should be visible after clicking toggle");

        // 6. Optional: Click again to verify it can be masked again
        visibilityToggle.click();
        Assert.assertEquals(passwordField.getAttribute("type"), "password",
                "Password field should be masked again after second click");
    }
}