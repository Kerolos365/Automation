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

public class Roles {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(Roles.class);

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

        // Navigate to "Roles" after login
        WebElement rolesLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='MuiBox-root css-164kvcj']/p[text()='Roles']")
        ));
        rolesLink.click();
        logger.info("Navigated to 'Roles' section.");
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
    public void testSelectOrganizationAndVerifyUser() {
        logger.info("Starting testSelectOrganizationAndVerifyUser...");

        // Click on the "Select Organization" dropdown
        WebElement organizationDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@aria-labelledby='select-organization' and @role='button' and contains(@class, 'MuiSelect-select')]")
        ));
        organizationDropdown.click();
        logger.info("Clicked on the 'Select Organization' dropdown.");

        // Select "NW Telepharmacy" from the dropdown
        WebElement nwTelepharmacyOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@data-value='663cbce3660b94e9321dca97' and contains(text(), 'NW Telepharmacy')]")
        ));
        nwTelepharmacyOption.click();
        logger.info("Selected 'NW Telepharmacy' from the dropdown.");

        // Verify that "jtotton@northwest.ca" is displayed
        WebElement userEmailAddress = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'MuiTypography-body2') and contains(text(), 'jtotton@northwest.ca')]")
        ));
        Assert.assertTrue(userEmailAddress.isDisplayed(), "'jtotton@northwest.ca' not found!");
        logger.info("'jtotton@northwest.ca' is displayed successfully.");
    }

    @Test(priority = 3)
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

    @Test(priority = 4)
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

        // Verify that "avc@nwc.com" is displayed in the search results
        WebElement avEmailResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'MuiTypography-body2') and contains(text(), 'avc@nwc.com')]")
        ));
        Assert.assertTrue(avEmailResult.isDisplayed(), "'avc@nwc.com' result not found in search results!");
        logger.info("'avc@nwc.com' result is displayed in the search results.");
    }

    @Test(priority = 5)
    public void testNavigateToUserDetails() {
        logger.info("Starting testNavigateToUserDetails...");

        // Click on the AV user link
        WebElement avUserLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@class, 'css-732qq2')]/div[contains(@class, 'MuiAvatar-root')]")
        ));
        avUserLink.click();
        logger.info("Clicked on the AV user link.");

        // Verify redirection to the user details page and check if "AVC" is displayed
        WebElement avName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'MuiTypography-body1') and contains(text(), 'AVC')]")
        ));
        Assert.assertTrue(avName.isDisplayed(), "'AVC' name not found on the user details page!");
        logger.info("Redirected to the user details page and 'AVC' name is displayed.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }
}
