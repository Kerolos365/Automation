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

public class ManageLicenses {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(ManageLicenses.class);

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

        // Navigate to "Manage Licenses" after login
        WebElement manageLicensesLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//p[contains(@class, 'MuiTypography-body1') and contains(text(), 'Manage Licenses')]")
        ));
        manageLicensesLink.click();
        logger.info("Navigated to 'Manage Licenses' section.");
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
    public void testSearchLicense() {
        logger.info("Starting testSearchLicense...");

        // Click on the search textbox
        WebElement searchTextBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Search License' and @type='text']")
        ));
        searchTextBox.click();
        logger.info("Clicked on the search textbox.");

        // Clear any pre-filled text and enter "NW"
        searchTextBox.clear();
        searchTextBox.sendKeys("NW");
        logger.info("Entered 'NW' in the search textbox.");

        // Verify that "NW Telepharmacy" license is displayed in the search results
        WebElement nwLicenseResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@class, 'MuiTypography-body1') and contains(text(), 'NW Telepharmacy')]")
        ));
        Assert.assertTrue(nwLicenseResult.isDisplayed(), "'NW Telepharmacy' license not found in search results!");
        logger.info("'NW Telepharmacy' license is displayed in the search results.");
    }

    @Test(priority = 3)
    public void testSelectStatus() {
        logger.info("Starting testSelectStatus...");

        // Click on the "Select Status" dropdown
        WebElement statusDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@aria-labelledby='status-select' and @role='button' and contains(@class, 'MuiSelect-select')]")
        ));
        statusDropdown.click();
        logger.info("Clicked on the 'Select Status' dropdown.");

        // Select "Active" from the dropdown
        WebElement activeOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@data-value='active' and contains(text(), 'Active')]")
        ));
        activeOption.click();
        logger.info("Selected 'Active' from the dropdown.");

        // Verify that "NW Telepharmacy" license is displayed in the search results
        WebElement nwLicenseResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@class, 'MuiTypography-body1') and contains(text(), 'NW Telepharmacy')]")
        ));
        Assert.assertTrue(nwLicenseResult.isDisplayed(), "'NW Telepharmacy' license not found in search results!");
        logger.info("'NW Telepharmacy' license is displayed in the search results.");
    }
    @Test(priority = 4)
    public void testAddLicenseRedirection() {
        logger.info("Starting testAddLicenseRedirection...");

        // Click on "Add License" button
        WebElement addLicenseButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Add License')]")
        ));
        addLicenseButton.click();
        logger.info("Clicked on 'Add License' button.");

        // Verify redirection to the "Create New License" page
        WebElement createLicenseTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h5[contains(@class, 'MuiTypography-h5') and contains(text(), 'Create New License')]")
        ));
        Assert.assertTrue(createLicenseTitle.isDisplayed(), "Redirect to 'Create New License' page failed.");
        logger.info("Redirected to the 'Create New License' page successfully.");
    }

    @Test(priority = 5)
    public void testNavigateToLicenseDetails() {
        logger.info("Starting testNavigateToLicenseDetails...");

        // Click on "NW Telepharmacy" license
        WebElement nwLicenseLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@class, 'MuiTypography-body1') and contains(text(), 'NW Telepharmacy')]")
        ));
        nwLicenseLink.click();
        logger.info("Clicked on 'NW Telepharmacy' license.");

        // Verify redirection to the license details page
        WebElement licenseName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(text(), 'License Name: NW Telepharmacy')]")
        ));
        Assert.assertTrue(licenseName.isDisplayed(), "'NW Telepharmacy' license details not found!");
        logger.info("Redirected to 'NW Telepharmacy' license details page successfully.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }
}