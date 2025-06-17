package TestCases;

import TestComponent.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class RolesPage extends BaseTest {
    private WebDriverWait wait;

    @BeforeMethod
    public void loginFirst() throws IOException {
        // Initialize WebDriverWait first
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Perform login
        driver.get(LOGIN_URL);
        login.loginNow(VALID_EMAIL, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlContains("/home"));
        roles.NavigateToRolesPage();
    }

    @Test(priority = 1, description = "Check export button")
    public void CheckExport() throws IOException {
        // Check if the Export button is displayed
        boolean isExportButtonDisplayed = driver.findElement(RolesExportButtonBy).isDisplayed();
        System.out.println("Is Export button displayed: " + isExportButtonDisplayed);
        // Assert that the Export button is displayed
        Assert.assertTrue(isExportButtonDisplayed, "Export button is not displayed");
        // Click the Export button
        WebElement exportButton = driver.findElement(RolesExportButtonBy);
        // Wait for the Export button to be clickable and then click it
        wait.until(ExpectedConditions.elementToBeClickable(exportButton)).click();
    }

    @Test(priority = 2, description = "Search Users button")
    public void SearchUsers() throws IOException, InterruptedException {
        // 1. Check if the Search button is displayed
        boolean isSearchButtonDisplayed = driver.findElement(RolesSearchBy).isDisplayed();
        System.out.println("Is Search button displayed: " + isSearchButtonDisplayed);
        Assert.assertTrue(isSearchButtonDisplayed, "Search button is not displayed");

        // 2. Click the Search button
        WebElement searchButton = driver.findElement(RolesSearchBy);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        // 3. Enter search text
        searchButton.sendKeys(Search_User);
        Thread.sleep(2000); // Wait for the search results to load

        // 4. Wait for results and verify
        List<WebElement> Users = driver.findElements(RolesUsersColumnBy); // Using your predefined locator

        boolean isFound = false;
        for (WebElement org : Users) {
            if (org.getText().trim().contains(Search_User)) {
                isFound = true;
                break;
            }
        }
        Assert.assertTrue(isFound, "Search user '" + Search_User + "' was not found in the users list");
    }


    @Test(priority = 3, description = "Date Range button")
    public void DateRange() throws IOException, InterruptedException {

        // Open Date Range filter
        wait.until(ExpectedConditions.elementToBeClickable(DateRangeButtonBy)).click();

        // Apply today's date
        wait.until(ExpectedConditions.elementToBeClickable(SetDateButtonBy)).click();

        // Wait until the first element appears (table is not empty)
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(CreatedAtColumnBy));

        // Short delay to let the page stabilize
        Thread.sleep(1000);  // Add this to prevent background refresh glitches

        // Get fresh list after page settles
        List<WebElement> organizations = driver.findElements(CreatedAtColumnBy);

        Assert.assertFalse(organizations.isEmpty(), "❌ No organizations created for today's date.");

        String todayPrefix = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

        for (WebElement element : organizations) {
            String dateText = element.getText();
            Assert.assertFalse(
                    dateText.contains(todayPrefix),
                    "❌ Organization date doesn't match today's date! Found: " + dateText
            );
        }
    }
    @Test(priority = 4, description = "No Change in Privileges")
    public void NoChangeInPrivileges() throws IOException, InterruptedException {
        // Click on the privileges button
        Thread.sleep(2000); // Wait for the page to load
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 500);");
        Thread.sleep(2000); // Wait for the scroll to complete
        WebElement privilegesButton = driver.findElement(privilegesBy);
        wait.until(ExpectedConditions.elementToBeClickable(privilegesButton)).click();
        privilegesButton.click();
        Thread.sleep(2000); // Wait for the privileges modal to load

        // Wait for the privileges modal to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='MuiDialogContent-root css-ajkkxx'][.//span[text()='Edit User Privileges']]")));

        // Check if the privileges modal is displayed
        boolean isModalDisplayed = driver.findElement(By.xpath("//div[@role='dialog']")).isDisplayed();
        Assert.assertTrue(isModalDisplayed, "Privileges modal is not displayed");

        // Click the Submit button
        WebElement submitButton = driver.findElement(privilegesSubmitBy);
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        // Wait for the Submit message
        wait.until(ExpectedConditions.visibilityOfElementLocated(SubmitMessageBy));
        // Check if the Submit message is displayed
        boolean isSubmitMessageDisplayed = driver.findElement(SubmitMessageBy).isDisplayed();
        Assert.assertTrue(isSubmitMessageDisplayed, "Submit message is not displayed");
        // Get the text of the Submit message
        String submitMessageText = driver.findElement(SubmitMessageBy).getText();
        // Check if the Submit message contains "No Changes Were Made"
        Assert.assertTrue(submitMessageText.contains("No Changes Were Made"), "Submit message does not contain 'No changes'");

    }

    @Test(priority = 5, description = " Change in privileges")
    public void ChangeInPrivileges() throws IOException, InterruptedException {
        // Click on the privileges button
        Thread.sleep(2000); // Wait for the page to load
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 500);");
        Thread.sleep(2000); // Wait for the scroll to complete
        WebElement privilegesButton = driver.findElement(privilegesBy);
        wait.until(ExpectedConditions.elementToBeClickable(privilegesButton)).click();
        privilegesButton.click();
        Thread.sleep(2000); // Wait for the privileges modal to load

        // Wait for the privileges modal to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='MuiDialogContent-root css-ajkkxx'][.//span[text()='Edit User Privileges']]")));

        // Check if the privileges modal is displayed
        boolean isModalDisplayed = driver.findElement(By.xpath("//div[@role='dialog']")).isDisplayed();
        Assert.assertTrue(isModalDisplayed, "Privileges modal is not displayed");
        // Click the checkbox to change privileges
        WebElement privilegesChangeCheckbox = driver.findElement(privilegesChangeBy);
        privilegesChangeCheckbox.click();
        Thread.sleep(2000); // Wait for the checkbox to be checked

        // Click the Submit button
        WebElement submitButton = driver.findElement(privilegesSubmitBy);
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        // Wait for the Submit message
        wait.until(ExpectedConditions.visibilityOfElementLocated(SubmitMessageBy));
        // Check if the Submit message is displayed
        boolean isSubmitMessageDisplayed = driver.findElement(SubmitMessageBy).isDisplayed();
        Assert.assertTrue(isSubmitMessageDisplayed, "Submit message is not displayed");
        // Get the text of the Submit message
        String submitMessageText = driver.findElement(SubmitMessageBy).getText();
        // Check if the Submit message contains "Updated Successfully!"
        Assert.assertTrue(submitMessageText.contains("Updated Successfully!"), "Submit message does not contain 'No changes'");

    }
}
