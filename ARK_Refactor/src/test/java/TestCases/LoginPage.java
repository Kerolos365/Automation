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
    @Test(priority = 5, description = "forgot password")
    public void forgotpassword() throws Exception {
        // 1. Navigate to login page
        login.goTo(LOGIN_URL);
        // 2. Locate and wait for forgot password link to be clickable
        By forgotPassLocator = By.xpath("//button[normalize-space()='Forgotten password?']");
        Thread.sleep(3000);
        login.waitingForElementToBeClickable(forgotPassLocator);

        // 3. Click the forgot password link
        WebElement forgotPassLink = driver.findElement(forgotPassLocator);
        forgotPassLink.click();

        // 4. Wait for and verify reset password text
        By resetPassTextLocator = By.xpath("//input[@placeholder='user@genesiscreations.co' and @type='text']");
        login.waitingForElementToBeVisible(resetPassTextLocator);

        //5. Enter email and click submit
        WebElement emailField = driver.findElement(By.id(":r0:"));
        emailField.sendKeys("kerolos.maged@genesiscreations.co");
        WebElement resetButton = driver.findElement(By.xpath("//button[normalize-space()='Reset Password']"));
        resetButton.click();
        //7. Wait for success message
        By successMessageLocator = By.cssSelector(".go2072408551.react-hot-toast");
        // Wait up to 10 seconds for message to appear
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(successMessageLocator));
        String successMessage = driver.findElement(successMessageLocator).getText();
        if (successMessage.contains("Password reset link sent to user email")) {
            System.out.println("Password reset link sent to user email");
        } else {
            System.out.println("You've requested too many reset links or used an invalid email");
        }

        // Add assertion for the success message
       Assert.assertTrue(successMessage.contains("sent") || successMessage.contains("email"),
              "Unexpected reset password response: " + successMessage);
        }
}