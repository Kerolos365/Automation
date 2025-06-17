package TestCases;

import TestComponent.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.time.Duration;

public class HomePage extends BaseTest {
    private WebDriverWait wait;
    @BeforeMethod
    public void loginFirst() throws IOException {
        // Initialize WebDriverWait first
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Perform login
        driver.get(LOGIN_URL);
        login.loginNow(VALID_EMAIL, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlContains("/home"));
    }

    @Test(priority = 1, description = "Verify profile page navigation")
    public void verifyProfileNavigation() {
        // Navigate to profile
        wait.until(ExpectedConditions.elementToBeClickable(profileButton)).click();
        wait.until(ExpectedConditions.urlContains("/users"));

        // Verify profile page
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(profileHeader));
        Assert.assertEquals(header.getText(), USER_NAME);
    }

    @Test(priority = 2, description = "Edit profile information")
    public void editProfileInformation() throws InterruptedException {
        // Navigate to profile
        verifyProfileNavigation();

        // Open edit form
        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(positionInput));

        // Update name
        Thread.sleep(2000);
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // Select all text
        input.sendKeys(Keys.DELETE);                   // Delete selected text
        Thread.sleep(2000);
        input.sendKeys(UPDATE_TEXT);
        wait.until(ExpectedConditions.elementToBeClickable(updateButton)).click();

        // Verify success
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(popUpMessage));
        Assert.assertEquals(message.getText(), "User updated successfully");

        // Verify update reflected
        Thread.sleep(3000);
        home.NavigateToHomePage();
        wait.until(ExpectedConditions.elementToBeClickable(profileButton)).click();
        Thread.sleep(3000);
        String updatedposition = wait.until(ExpectedConditions.visibilityOfElementLocated(positionHeader)).getText();
        Assert.assertEquals(updatedposition, UPDATE_TEXT);
        System.out.println("Updated Profile Name: " + updatedposition);

        // return position to QA only to make this case continue always in editing without failing
        Thread.sleep(3000);
        // Open edit form and update name
        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
        // Re-locate the input field after opening the edit form
        WebElement inputAgain = wait.until(ExpectedConditions.visibilityOfElementLocated(positionInput));
        Thread.sleep(2000);
        inputAgain.sendKeys(" KING");
        wait.until(ExpectedConditions.elementToBeClickable(updateButton)).click();
        WebElement updatedMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(popUpMessage));
        Assert.assertEquals(updatedMessage.getText(), "User updated successfully");
    }

    @Test(priority = 3, description = "Delete profile information")
    public void deleteProfileInformation() throws InterruptedException {
        // Navigate to profile
        verifyProfileNavigation();

        // Open edit form
        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(emailinput));
        WebElement input2 = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameinput));

        // delete email
        Thread.sleep(2000);
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // Select all text
        input.sendKeys(Keys.DELETE);                   // Delete selected text
        Thread.sleep(2000);
        input.sendKeys("");
        // delete username
        Thread.sleep(2000);
        input2.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // Select all text
        input2.sendKeys(Keys.DELETE);                   // Delete selected text
        Thread.sleep(2000);
        input2.sendKeys("");
        // Click update button
        wait.until(ExpectedConditions.elementToBeClickable(updateButton)).click();

        // Verify error message for mail textbox
        WebElement emailMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMailMessage));
        Assert.assertEquals(emailMessage.getText(), "Invalid email!");
        // Verify error message for mail textbox
        WebElement NameMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(errorNameMessage));
        Assert.assertEquals(NameMessage.getText(), "Username must be at least 2 characters.");
    }

    @Test(priority = 4, description = "Change Password")
    public void ChangePass() throws InterruptedException {
        // Navigate to profile
        verifyProfileNavigation();
        //open change password
        wait.until(ExpectedConditions.elementToBeClickable(changePassButton)).click();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(currentPass));
        WebElement input2 = wait.until(ExpectedConditions.visibilityOfElementLocated(newPass));
        WebElement input3 = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPass));

        // current password
        Thread.sleep(2000);
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // Select all text
        input.sendKeys(Keys.DELETE);                   // Delete selected text
        Thread.sleep(2000);
        input.sendKeys(CurrentPass);
        //visibility of  password
        WebElement visibleButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.MuiIconButton-root svg.iconify--mdi")));
        visibleButton.click();
        // new password
        Thread.sleep(2000);
        input2.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // Select all text
        input2.sendKeys(Keys.DELETE);                   // Delete selected text
        Thread.sleep(2000);
        input2.sendKeys(NewPass);
        //confirm password
        Thread.sleep(2000);
        input3.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // Select all text
        input3.sendKeys(Keys.DELETE);                   // Delete selected text
        Thread.sleep(2000);
        input3.sendKeys(NewPass);
        // Click Confirm button
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
        // Verify success message
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(popUpMessage));
        Assert.assertEquals(message.getText(), "Password changed successfully.");
        // Verify that the password has been changed
        login.loginNow(VALID_EMAIL, NewPass);
        wait.until(ExpectedConditions.urlContains("/change-password"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/change-password"), "User was not redirected to change-password page after login.");

        // Revert password to original for future tests
        // Re-locate the password fields after navigation!
        input = wait.until(ExpectedConditions.visibilityOfElementLocated(currentPass));
        input2 = wait.until(ExpectedConditions.visibilityOfElementLocated(newPass));
        input3 = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPass));
        // current password
        Thread.sleep(2000);
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // Select all text
        input.sendKeys(Keys.DELETE);                   // Delete selected text
        Thread.sleep(2000);
        input.sendKeys(NewPass);
        //visibility of  password
        visibleButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.MuiIconButton-root svg.iconify--mdi")));
        visibleButton.click();        // new password
        Thread.sleep(2000);
        input2.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // Select all text
        input2.sendKeys(Keys.DELETE);                   // Delete selected text
        Thread.sleep(2000);
        input2.sendKeys(CurrentPass);
        //confirm password
        Thread.sleep(2000);
        input3.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // Select all text
        input3.sendKeys(Keys.DELETE);                   // Delete selected text
        Thread.sleep(2000);
        input3.sendKeys(CurrentPass);
        // Click Confirm button
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
        // Verify success message
        WebElement message2 = wait.until(ExpectedConditions.visibilityOfElementLocated(popUpMessage));
        Assert.assertEquals(message2.getText(), "Password changed successfully.");
    }

    @Test(priority = 5, description = "use same Password")
    public void SamePass() throws InterruptedException {
        // Navigate to profile
        verifyProfileNavigation();
        //open change password
        wait.until(ExpectedConditions.elementToBeClickable(changePassButton)).click();
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(currentPass));
        WebElement input2 = wait.until(ExpectedConditions.visibilityOfElementLocated(newPass));
        WebElement input3 = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPass));

        // current password
        Thread.sleep(2000);
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // Select all text
        input.sendKeys(Keys.DELETE);                   // Delete selected text
        Thread.sleep(2000);
        input.sendKeys(CurrentPass);
        //visibility of  password
        WebElement visibleButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.MuiIconButton-root svg.iconify--mdi")));
        visibleButton.click();
        // new password
        Thread.sleep(2000);
        input2.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // Select all text
        input2.sendKeys(Keys.DELETE);                   // Delete selected text
        Thread.sleep(2000);
        input2.sendKeys(CurrentPass);
        //confirm password
        Thread.sleep(2000);
        input3.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // Select all text
        input3.sendKeys(Keys.DELETE);                   // Delete selected text
        Thread.sleep(2000);
        input3.sendKeys(CurrentPass);
        // Click Confirm button
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
        // Verify success message
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(popUpMessage));
        Assert.assertEquals(message.getText(), "New password cannot be the same as the current password.");
    }
    @Test(priority = 6, description = "Empty Password")
    public void EmptyPass() throws InterruptedException {
        // Navigate to profile
        verifyProfileNavigation();
        //open change password
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(changePassButton)).click();
        Thread.sleep(2000);
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(currentPass));
        WebElement input2 = wait.until(ExpectedConditions.visibilityOfElementLocated(newPass));
        WebElement input3 = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPass));

        // current password
        Thread.sleep(2000);
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // Select all text
        input.sendKeys(Keys.DELETE);                   // Delete selected text
        Thread.sleep(2000);
        input.sendKeys(CurrentPass);
        //visibility of  password
        WebElement visibleButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.MuiIconButton-root svg.iconify--mdi")));
        visibleButton.click();
        // new password
        Thread.sleep(2000);
        input2.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // Select all text
        input2.sendKeys(Keys.DELETE);                   // Delete selected text
        Thread.sleep(2000);
        input2.sendKeys("");
        //confirm password
        Thread.sleep(2000);
        input3.sendKeys(Keys.chord(Keys.CONTROL, "a"));  // Select all text
        input3.sendKeys(Keys.DELETE);                   // Delete selected text
        Thread.sleep(2000);
        input3.sendKeys("");
        // Click Confirm button
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
        // Verify success message
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(RedMissingErrorMessage));
        Assert.assertEquals(message.getText(), "Password must be at least 8 characters.");
    }
    }


