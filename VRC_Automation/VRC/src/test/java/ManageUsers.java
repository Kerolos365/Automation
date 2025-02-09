//package com.vrc.test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public class ManageUsers {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(ManageUsers.class);

    @BeforeMethod
    public void setUp() {
        // Set up WebDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        logger.info("Browser launched and maximized.");

        // Navigate to the VRC website login page
        driver.get("http://vrc.genesiscreations.co:3000/login/?returnUrl=%2Forganizations%2F663cbce3660b94e9321dca97%2F");
        logger.info("Navigated to VRC website login page.");

        // Wait for the page to load completely
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        logger.info("Page loaded completely.");

        // Perform login by clicking the "Log In" button (no email/password input required)
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Log In')]")
        ));
        loginButton.click();
        logger.info("Clicked the 'Log In' button.");

        // Navigate to "Manage Users" after login
        WebElement manageUsersLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//p[contains(@class, 'MuiTypography-body1') and contains(text(), 'Manage Users')]")
        ));
        manageUsersLink.click();
        logger.info("Navigated to 'Manage Users' section.");
    }

    @Test(priority = 1)
    public void testExportButton() {
        logger.info("Starting testExportButton...");

        // Click on Export button
        WebElement exportButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Export')]")
        ));
        exportButton.click();
        logger.info("Clicked on the 'Export' button.");
    }

    @Test(priority = 2)
    public void testDateRangeFilter() {
        logger.info("Starting testDateRangeFilter...");

        // Click on "Date Range" button
        WebElement dateRangeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Date Range']")
        ));
        dateRangeButton.click();
        logger.info("Clicked on 'Date Range' button.");

        // Click on "Set" button
        WebElement setDateButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Set']")
        ));
        setDateButton.click();
        logger.info("Clicked on 'Set' button.");

        // Verify the message "No users found for the selected date range" is displayed
        WebElement noUsersMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='MuiBox-root css-3wt5m6' and contains(text(), 'No users found for the selected date range')]")
        ));
        Assert.assertTrue(noUsersMessage.isDisplayed(), "Expected message not displayed.");
        logger.info("Message 'No users found for the selected date range' is displayed.");
    }

    @Test(priority = 3)
    public void testSelectUserAccess() {
        logger.info("Starting testSelectUserAccess...");

        // Click on the "Select User Access" dropdown
        WebElement userAccessDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@aria-labelledby='access-select' and @role='button' and contains(@class, 'MuiSelect-select')]")
        ));
        userAccessDropdown.click();
        logger.info("Clicked on the 'Select User Access' dropdown.");

        // Select the "Active" option from the dropdown
        WebElement activeOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@data-value='active' and contains(text(), 'Active')]")
        ));
        activeOption.click();
        logger.info("Selected 'Active' from the dropdown.");

        // Verify that the "Activated" chip is displayed
        WebElement activatedChip = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'MuiChip-root') and contains(@class, 'MuiChip-colorSuccess')]//span[contains(text(), 'Activated')]")
        ));
        Assert.assertTrue(activatedChip.isDisplayed(), "'Activated' chip not found!");
        logger.info("'Activated' chip is displayed successfully.");
    }

    @Test(priority = 4)
    public void testSelectUserVerification() {
        logger.info("Starting testSelectUserVerification...");

        // Click on the "Select User Verification" dropdown
        WebElement userVerificationDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@aria-labelledby='verification-select' and @role='button' and contains(@class, 'MuiSelect-select')]")
        ));
        userVerificationDropdown.click();
        logger.info("Clicked on the 'Select User Verification' dropdown.");

        // Select the "Verified" option from the dropdown
        WebElement verifiedOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@data-value='verified' and contains(text(), 'Verified')]")
        ));
        verifiedOption.click();
        logger.info("Selected 'Verified' from the dropdown.");

        // Verify that the "Verified" chip is displayed
        WebElement verifiedChip = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(@class, 'MuiChip-label') and contains(text(), 'Verified')]")
        ));
        Assert.assertTrue(verifiedChip.isDisplayed(), "'Verified' chip not found!");
        logger.info("'Verified' chip is displayed successfully.");
    }

    @Test(priority = 5)
    public void testSearchUser() {
        logger.info("Starting testSearchUser...");

        // Click on the search textbox
        WebElement searchTextBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Search User' and @type='text']")
        ));
        searchTextBox.click();
        logger.info("Clicked on the search textbox.");

        // Clear any pre-filled text and enter "AV"
        searchTextBox.clear();
        searchTextBox.sendKeys("AV");
        logger.info("Entered 'AV' in the search textbox.");

        // Verify that the <p> element with text "avc@nwc.com" is displayed in the search results
        WebElement avEmailResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'MuiTypography-body2') and contains(text(), 'avc@nwc.com')]")
        ));
        Assert.assertTrue(avEmailResult.isDisplayed(), "'avc@nwc.com' result not found in search results!");
        logger.info("'avc@nwc.com' result is displayed in the search results.");
    }

    @Test(priority = 6)
    public void testAddUserRedirection() {
        logger.info("Starting testAddUserRedirection...");

        // Click on "Add User" button
        WebElement addUserButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Add User')]")
        ));
        addUserButton.click();
        logger.info("Clicked on 'Add User' button.");

        // Verify redirection to the "Account Information" page
        WebElement accountInfoTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h5[contains(@class, 'MuiTypography-h5') and contains(text(), 'Account Information')]")
        ));
        Assert.assertTrue(accountInfoTitle.isDisplayed(), "Redirect to 'Account Information' page failed.");
        logger.info("Redirected to the 'Account Information' page successfully.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }
}