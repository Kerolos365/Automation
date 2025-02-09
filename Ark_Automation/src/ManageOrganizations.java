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

public class ManageOrganizations {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(ManageOrganizations.class);

    @BeforeMethod
    public void setUp() {
        // Set up WebDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        logger.info("Browser launched and maximized.");

        // Navigate to the ARK website login page
        driver.get("https://ark.genesiscreations.co/login/?returnUrl=%2Forganizations%2Fcreate-organization%2F");
        logger.info("Navigated to ARK website login page.");

        // Wait for the page to load completely
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        logger.info("Page loaded completely.");

        // Wait for the email text box to be visible and enter email
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='user@genesiscreations.co']")
        ));
        emailField.sendKeys("kerolosmaged4@gmail.com");
        logger.info("Entered email address.");
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }

        // Wait for the password text box to be visible and enter password
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("auth-login-v2-password")));
        passwordField.clear();
        passwordField.sendKeys("Test123456");
        logger.info("Entered password.");
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }

        // Wait for the login button to be clickable and click it
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Log In']")));
        loginButton.click();
        logger.info("Clicked the login button.");
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }

        // Verify login success
        WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(),'Welcome')]")));
        Assert.assertTrue(welcomeMessage.isDisplayed(), "Login failed! Welcome message not displayed.");
        logger.info("Login successful. Welcome message is displayed.");
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }

        // Click on the "Manage Organizations" button
        WebElement manageOrganizationsButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, '/organizations/') and contains(@class, 'MuiListItemButton-root')]")
        ));
        manageOrganizationsButton.click();
        logger.info("Clicked on the 'Manage Organizations' button.");
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }

        // Verify that the user is on the "Manage Organizations" page
        //WebElement manageOrganizationsHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(), 'Manage Organizations')]")));
       // Assert.assertTrue(manageOrganizationsHeader.isDisplayed(), "User is not on the 'Manage Organizations' page.");
       // logger.info("User is on the 'Manage Organizations' page.");
    }

    @Test(priority = 1)
    public void testChooseIndustry() {
        logger.info("Starting testChooseIndustry...");

        // Click on the "Select Industry" dropdown
        WebElement industryDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@aria-labelledby='industry-select' and @role='button' and contains(@class, 'MuiSelect-select')]")
        ));
        industryDropdown.click();
        logger.info("Clicked on the 'Select Industry' dropdown.");
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }

        // Select the "Insurance" option from the dropdown
        WebElement insuranceOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@role='option' and @data-value='Insurance']")
        ));
        insuranceOption.click();
        logger.info("Selected 'Insurance' from the dropdown.");
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }

        // Verify if the "Genesis" organization is displayed
        WebElement genesisOrganization = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@class, 'css-732qq2') and contains(@href, '/organizations/670f9ee8345d3802c96ce855/') and contains(text(), 'Genesis')]")
        ));
        Assert.assertTrue(genesisOrganization.isDisplayed(), "Genesis organization not found!");
        logger.info("Genesis organization is displayed.");
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }
    }

    @Test(priority = 2)
    public void testExportButton() {
        logger.info("Starting testExportButton...");

        // Click on Export button
        WebElement exportButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Export')]")));
        exportButton.click();
        logger.info("Clicked on the 'Export' button.");
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }
    }

    @Test(priority = 3)
    public void testDateRangeFilter() {
        logger.info("Starting testDateRangeFilter...");

        // Click on "Date Range" button
        WebElement dateRangeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Date Range']")));
        dateRangeButton.click();
        logger.info("Clicked on 'Date Range' button.");
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }

        // Click on "Set Date" button
        WebElement setDateButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Set']")));
        setDateButton.click();
        logger.info("Clicked on 'Set Date' button.");
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }

        // Verify the message "No organizations found for the selected date range" is displayed
        WebElement noOrganizationsMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='MuiBox-root css-3wt5m6' and contains(text(), 'No organizations found for the selected date range')]")
        ));
        Assert.assertTrue(noOrganizationsMessage.isDisplayed(), "Expected message not displayed.");
        logger.info("Message 'No organizations found for the selected date range' is displayed.");
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }
    }

    @Test(priority = 4)
    public void testAddOrganization() {
        logger.info("Starting testAddOrganization...");

        // Click on "Add Organization" button
        WebElement addOrganizationButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Add Organization']")));
        addOrganizationButton.click();
        logger.info("Clicked on 'Add Organization' button.");
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }

        // Verify that the URL changes to the expected URL
        String expectedURL = "https://ark.genesiscreations.co/organizations/create-organization/";
        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, expectedURL, "Redirect URL does not match the expected URL.");
        logger.info("Redirected to the correct URL: " + currentURL);
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }

        // Verify that the "Organization Name" textbox exists
        WebElement organizationNameTextbox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[placeholder='Organization Name']")
        ));
        Assert.assertTrue(organizationNameTextbox.isDisplayed(), "Organization Name textbox not displayed.");
        logger.info("Organization Name textbox is displayed.");
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }
}