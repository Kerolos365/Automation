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

        // Navigate to the ARK website login page
        driver.get("https://ark.genesiscreations.co/login/?returnUrl=%2Forganizations%2Fcreate-organization%2F");
        logger.info("Navigated to ARK website login page.");

        // Wait for the page to load completely
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        logger.info("Page loaded completely.");

        // Log in
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='user@genesiscreations.co']")
        ));
        emailField.sendKeys("kerolosmaged4@gmail.com");
        logger.info("Entered email address.");

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("auth-login-v2-password")));
        passwordField.clear();
        passwordField.sendKeys("Test123456");
        logger.info("Entered password.");

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Log In']")));
        loginButton.click();
        logger.info("Clicked the login button.");

        // Verify login success
        WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h5[contains(text(),'Welcome')]")));
        Assert.assertTrue(welcomeMessage.isDisplayed(), "Login failed! Welcome message not displayed.");
        logger.info("Login successful. Welcome message is displayed.");

        // Click on the "Manage Users" button
        WebElement manageUsersButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, '/users/') and contains(@class, 'MuiListItemButton-root')]")
        ));
        manageUsersButton.click();
        logger.info("Clicked on the 'Manage Users' button.");
        try {
            Thread.sleep(3000); // Pause for recording
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
        }
    }

    @Test(priority = 1)
    public void testExportButton() throws InterruptedException {
        logger.info("Starting testExportButton...");

        // Click on the Export button
        WebElement exportButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'css-12kry57') and contains(., 'Export')]")
        ));
        exportButton.click();
        logger.info("Clicked on the Export button.");
        Thread.sleep(3000); // Pause for 3 seconds
    }

    @Test(priority = 2)
    public void testDateRangeFilter() throws InterruptedException {
        logger.info("Starting testDateRangeFilter...");

        // Click on the Date Range button
        WebElement dateRangeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'css-1hfsram') and contains(., 'Date Range')]")
        ));
        dateRangeButton.click();
        logger.info("Clicked on the Date Range button.");
        Thread.sleep(3000); // Pause for 3 seconds

        // Click on the Set button
        WebElement setButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'css-1beqac6') and contains(., 'Set')]")
        ));
        setButton.click();
        logger.info("Clicked on the Set button.");
        Thread.sleep(3000); // Pause for 3 seconds
    }

    @Test(priority = 3)
    public void testSelectUserVerificationFilter() throws InterruptedException {
        logger.info("Starting testSelectUserVerificationFilter...");

        // Click on the Select User Verification dropdown
        WebElement verificationDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@aria-labelledby='verification-select' and @role='button']")
        ));
        verificationDropdown.click();
        logger.info("Clicked on the Select User Verification dropdown.");
        Thread.sleep(3000); // Pause for 3 seconds

        // Select the "Verified" option
        WebElement verifiedOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@role='option' and @data-value='verified']")
        ));
        verifiedOption.click();
        logger.info("Selected 'Verified' from the dropdown.");
        Thread.sleep(3000); // Pause for 3 seconds

        // Verify that the email is displayed
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'css-11rehn2') and contains(., 'kerolosmaged4@gmail.com')]")
        ));
        Assert.assertTrue(emailElement.isDisplayed(), "Email not displayed after filtering by verification.");
        logger.info("Email 'kerolosmaged4@gmail.com' is displayed.");
        Thread.sleep(3000); // Pause for 3 seconds
    }

    @Test(priority = 4)
    public void testSelectUserAccessFilter() throws InterruptedException {
        logger.info("Starting testSelectUserAccessFilter...");

        // Click on the Select User Access dropdown
        WebElement accessDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@aria-labelledby='access-select' and @role='button']")
        ));
        accessDropdown.click();
        logger.info("Clicked on the Select User Access dropdown.");
        Thread.sleep(3000); // Pause for 3 seconds

        // Select the "Active" option
        WebElement activeOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@role='option' and @data-value='active']")
        ));
        activeOption.click();
        logger.info("Selected 'Active' from the dropdown.");
        Thread.sleep(3000); // Pause for 3 seconds

        // Verify that the email is displayed
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'css-11rehn2') and contains(., 'kerolosmaged4@gmail.com')]")
        ));
        Assert.assertTrue(emailElement.isDisplayed(), "Email not displayed after filtering by access.");
        logger.info("Email 'kerolosmaged4@gmail.com' is displayed.");
        Thread.sleep(3000); // Pause for 3 seconds
    }

    @Test(priority = 5)
    public void testSelectRoleFilter() throws InterruptedException {
        logger.info("Starting testSelectRoleFilter...");

        // Click on the Select Role dropdown
        WebElement roleDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@aria-labelledby='role-select' and @role='button']")
        ));
        roleDropdown.click();
        logger.info("Clicked on the Select Role dropdown.");
        Thread.sleep(3000); // Pause for 3 seconds

        // Select the "AVC" option
        WebElement avcOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@role='option' and @data-value='670b73bde7636d86d195998c']")
        ));
        avcOption.click();
        logger.info("Selected 'AVC' from the dropdown.");
        Thread.sleep(3000); // Pause for 3 seconds

        // Verify that the email is displayed
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'css-11rehn2') and contains(., 'kerolosmaged4@gmail.com')]")
        ));
        Assert.assertTrue(emailElement.isDisplayed(), "Email not displayed after filtering by role.");
        logger.info("Email 'kerolosmaged4@gmail.com' is displayed.");
        Thread.sleep(3000); // Pause for 3 seconds
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }
}