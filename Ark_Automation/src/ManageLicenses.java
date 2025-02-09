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

        // Click on the "Manage Licenses" button
        WebElement manageLicensesButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, '/licenses/') and contains(@class, 'MuiButtonBase-root')]")
        ));
        manageLicensesButton.click();
        logger.info("Clicked on the 'Manage Licenses' button.");
        sleep(3000); // Pause for 3 seconds
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
    public void testSelectStatusFilter() throws InterruptedException {
        logger.info("Starting testSelectStatusFilter...");

        // Click on the Select Status dropdown
        WebElement statusDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@role='button' and @aria-labelledby='status-select']")
        ));
        statusDropdown.click();
        logger.info("Clicked on the Select Status dropdown.");
        Thread.sleep(3000); // Pause for 3 seconds

        // Select the "Active" option
        WebElement activeOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@role='option' and @data-value='active']")
        ));
        activeOption.click();
        logger.info("Selected 'Active' from the dropdown.");
        Thread.sleep(3000); // Pause for 3 seconds

        // Verify that the name "Genesis SAE" is displayed
        WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@class, 'css-hu8vas') and contains(., 'Genesis SAE')]")
        ));
        Assert.assertTrue(nameElement.isDisplayed(), "Name 'Genesis SAE' not displayed after filtering by status.");
        logger.info("Name 'Genesis SAE' is displayed.");
        Thread.sleep(3000); // Pause for 3 seconds
    }

    @Test(priority = 4)
    public void testSearchLicense() throws InterruptedException {
        logger.info("Starting testSearchLicense...");

        // Enter "Genesis" in the search bar
        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Search License']")
        ));
        searchBar.sendKeys("Genesis");
        logger.info("Entered 'Genesis' in the search bar.");
        Thread.sleep(3000); // Pause for 3 seconds

        // Verify that the name "Genesis SAE" is displayed
        WebElement nameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@class, 'css-hu8vas') and contains(., 'Genesis SAE')]")
        ));
        Assert.assertTrue(nameElement.isDisplayed(), "Name 'Genesis SAE' not displayed after search.");
        logger.info("Name 'Genesis SAE' is displayed.");
        Thread.sleep(3000); // Pause for 3 seconds
    }

    @Test(priority = 5)
    public void testClickLicense() throws InterruptedException {
        logger.info("Starting testClickLicense...");

        // Click on the "Genesis SAE" license
        WebElement licenseLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@class, 'css-hu8vas') and contains(., 'Genesis SAE')]")
        ));
        licenseLink.click();
        logger.info("Clicked on the 'Genesis SAE' license.");
        Thread.sleep(3000); // Pause for 3 seconds

        // Verify that the system redirects to the correct page and the license name is displayed
        WebElement licenseName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'css-66azsl') and contains(., 'License Name: Genesis SAE')]")
        ));
        Assert.assertTrue(licenseName.isDisplayed(), "System did not redirect to the correct page or license name is not displayed.");
        logger.info("System redirected to the correct page. License Name: Genesis SAE is displayed.");
        Thread.sleep(3000); // Pause for 3 seconds
    }

    @Test(priority = 6)
    public void testAddLicense() throws InterruptedException {
        logger.info("Starting testAddLicense...");

        // Click on the Add License button
        WebElement addLicenseButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'css-7suplt') and contains(., 'Add License')]")
        ));
        addLicenseButton.click();
        logger.info("Clicked on the Add License button.");
        Thread.sleep(3000); // Pause for 3 seconds

        // Verify that the system redirects to the License Details page
        WebElement licenseDetailsHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h5[contains(@class, 'css-163374d') and contains(., 'License Details')]")
        ));
        Assert.assertTrue(licenseDetailsHeader.isDisplayed(), "System did not redirect to the License Details page.");
        logger.info("System redirected to the License Details page.");
        Thread.sleep(3000); // Pause for 3 seconds
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }

    // Helper method to handle Thread.sleep with InterruptedException
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted: " + e.getMessage());
            throw new RuntimeException("Thread sleep interrupted", e);
        }
    }
}