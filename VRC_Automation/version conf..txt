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

public class VersionConfigurations {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(VersionConfigurations.class);

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

        // Navigate to "Version Configurations" after login
        WebElement versionConfigurationsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//p[contains(@class, 'MuiTypography-body1') and contains(text(), 'Version Configurations')]")
        ));
        versionConfigurationsLink.click();
        logger.info("Navigated to 'Version Configurations' section.");
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
    public void testSearchConfiguration() {
        logger.info("Starting testSearchConfiguration...");

        // Click on the search textbox
        WebElement searchTextBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Search Configuration' and @type='text']")
        ));
        searchTextBox.click();
        logger.info("Clicked on the search textbox.");

        // Clear any pre-filled text and enter "PPE"
        searchTextBox.clear();
        searchTextBox.sendKeys("PPE");
        logger.info("Entered 'PPE' in the search textbox.");

        // Verify that "PPE" version is displayed in the search results
        WebElement ppeVersionResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@class, 'MuiTypography-body1') and contains(text(), 'PPE')]")
        ));
        Assert.assertTrue(ppeVersionResult.isDisplayed(), "'PPE' version not found in search results!");
        logger.info("'PPE' version is displayed in the search results.");
    }

    @Test(priority = 3)
    public void testSelectVersionType() {
        logger.info("Starting testSelectVersionType...");

        // Click on the "Select Version Type" dropdown
        WebElement versionTypeDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@aria-labelledby='type-select' and @role='button' and contains(@class, 'MuiSelect-select')]")
        ));
        versionTypeDropdown.click();
        logger.info("Clicked on the 'Select Version Type' dropdown.");

        // Select "Training" from the dropdown
        WebElement trainingOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@data-value='training' and contains(text(), 'Training')]")
        ));
        trainingOption.click();
        logger.info("Selected 'Training' from the dropdown.");

        // Verify that "Training" versions are displayed
        WebElement trainingVersion = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'MuiTypography-body2') and contains(text(), 'Training')]")
        ));
        Assert.assertTrue(trainingVersion.isDisplayed(), "'Training' versions not found!");
        logger.info("'Training' versions are displayed successfully.");
    }

    @Test(priority = 4)
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

        // Verify the message "No versions found for the selected date range" is displayed
        WebElement noVersionsMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='MuiBox-root css-3wt5m6' and contains(text(), 'No versions found for the selected date range')]")
        ));
        Assert.assertTrue(noVersionsMessage.isDisplayed(), "Expected message not displayed.");
        logger.info("Message 'No versions found for the selected date range' is displayed.");
    }

    @Test(priority = 5)
    public void testAddVersionConfiguration() {
        logger.info("Starting testAddVersionConfiguration...");

        // Click on "Add Version Configuration" button
        WebElement addVersionButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Add Version Configuration')]")
        ));
        addVersionButton.click();
        logger.info("Clicked on 'Add Version Configuration' button.");

        // Verify redirection to the "Create New Version Configuration" page
        WebElement createVersionTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h5[contains(@class, 'MuiTypography-h5') and contains(text(), 'Create New Version Configuration')]")
        ));
        Assert.assertTrue(createVersionTitle.isDisplayed(), "Redirect to 'Create New Version Configuration' page failed.");
        logger.info("Redirected to the 'Create New Version Configuration' page successfully.");
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }
}