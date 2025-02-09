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

        // Click on the "Roles" button
        WebElement rolesButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, '/roles/') and contains(@class, 'MuiListItemButton-root')]")
        ));
        rolesButton.click();
        logger.info("Clicked on the 'Roles' button.");

        try {
            Thread.sleep(3000); // Pause for 3 seconds
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
    public void testSelectOrganization() throws InterruptedException {
        logger.info("Starting testSelectOrganization...");

        // Click on the Select Organization dropdown
        WebElement organizationDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@role='button' and @aria-labelledby='select-organization']")
        ));
        organizationDropdown.click();
        logger.info("Clicked on the Select Organization dropdown.");
        Thread.sleep(3000); // Pause for 3 seconds

        // Select the "Genesis" option
        WebElement genesisOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@role='option' and @data-value='670f9ee8345d3802c96ce855']")
        ));
        genesisOption.click();
        logger.info("Selected 'Genesis' from the dropdown.");
        Thread.sleep(3000); // Pause for 3 seconds

        // Verify that the name "test test" is displayed
        WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@class, 'css-12ni5s9') and contains(., 'test test')]")
        ));
        Assert.assertTrue(nameElement.isDisplayed(), "Name 'test test' not displayed after selecting organization.");
        logger.info("Name 'test test' is displayed.");
        Thread.sleep(3000); // Pause for 3 seconds
    }

    @Test(priority = 4)
    public void testSearchUser() throws InterruptedException {
        logger.info("Starting testSearchUser...");

        // Enter "kerolos" in the search bar
        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Search User']")
        ));
        searchBar.sendKeys("kerolos");
        logger.info("Entered 'kerolos' in the search bar.");
        Thread.sleep(3000); // Pause for 3 seconds

        // Verify that the name "Kerolos Maged" is displayed
        WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@class, 'css-12ni5s9') and contains(., 'Kerolos Maged')]")
        ));
        Assert.assertTrue(nameElement.isDisplayed(), "Name 'Kerolos Maged' not displayed after search.");
        logger.info("Name 'Kerolos Maged' is displayed.");
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