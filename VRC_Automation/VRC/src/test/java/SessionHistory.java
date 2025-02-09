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

public class SessionHistory {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(SessionHistory.class);

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

        // Navigate to "Sessions History" after login
        WebElement sessionsHistoryLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//p[contains(@class, 'MuiTypography-body1') and contains(text(), 'Sessions History')]")
        ));
        sessionsHistoryLink.click();
        logger.info("Navigated to 'Sessions History' section.");
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
    public void testSearchSession() {
        logger.info("Starting testSearchSession...");

        // Click on the search textbox
        WebElement searchTextBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Search History' and @type='text']")
        ));
        searchTextBox.click();
        logger.info("Clicked on the search textbox.");

        // Clear any pre-filled text and enter "PPE"
        searchTextBox.clear();
        searchTextBox.sendKeys("PPE");
        logger.info("Entered 'PPE' in the search textbox.");

        // Verify that "PPE" session is displayed in the search results
        WebElement ppeSessionResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'MuiTypography-body2')]/a[contains(text(), 'PPE')]")
        ));
        Assert.assertTrue(ppeSessionResult.isDisplayed(), "'PPE' session not found in search results!");
        logger.info("'PPE' session is displayed in the search results.");
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

        // Verify the message "No sessions found for the selected date range" is displayed
        WebElement noSessionsMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='MuiBox-root css-3wt5m6' and contains(text(), 'No sessions found for the selected date range')]")
        ));
        Assert.assertTrue(noSessionsMessage.isDisplayed(), "Expected message not displayed.");
        logger.info("Message 'No sessions found for the selected date range' is displayed.");
    }


    @Test(priority = 4)
    public void testSelectOrganization() {
        logger.info("Starting testSelectOrganization...");

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

        // Verify that sessions of "NW Telepharmacy" are displayed
        WebElement nwSessionResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@class, 'css-12ni5s9') and contains(text(), 'RebeccaLynch')]")
        ));
        Assert.assertTrue(nwSessionResult.isDisplayed(), "'NW Telepharmacy' sessions not found!");
        logger.info("'NW Telepharmacy' sessions are displayed successfully.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }
}