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

public class Home {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(Home.class);

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
    }

    @Test(priority = 1)
    public void testViewProfileRedirection() throws InterruptedException {
        logger.info("Starting testViewProfileRedirection...");

        // Wait for home page to load completely
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='MuiBox-root css-164kvcj']")));  // Home page element
        logger.info("Home page loaded completely.");
        Thread.sleep(3000); // Pause for recording

        // Step 2: Click on the "View Profile" button
        try {
            // Wait for the "View Profile" button to be visible and clickable
            WebElement viewProfileButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(@href, '/UsersAssetStore/670bb16a83dd62271f2128ad/') and contains(text(), 'View Profile')]")
            ));
            logger.info("'View Profile' button is visible and clickable.");
            viewProfileButton.click();
            logger.info("Clicked on 'View Profile' button.");
        } catch (TimeoutException e) {
            // Log the error and take a screenshot for debugging
            logger.error("'View Profile' button not found or not clickable: " + e.getMessage());
            throw new RuntimeException("'View Profile' button not found or not clickable.", e);
        }
        Thread.sleep(3000); // Pause for recording

        // Step 3: Verify redirection to the profile page and check for "First Name: Kerolos"
        try {
            // Wait for the profile page to load and verify the presence of "First Name: Kerolos"
            WebElement firstNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='MuiBox-root css-j6b6o5']//p[@class='MuiTypography-root MuiTypography-body1 css-1k32hsy' and contains(text(), 'First Name:')]/following-sibling::p[@class='MuiTypography-root MuiTypography-body1 css-1qazv96' and contains(text(), 'Kerolos')]")
            ));
            Assert.assertTrue(firstNameElement.isDisplayed(), "Redirection to profile page failed. 'First Name: Kerolos' not found.");
            logger.info("Redirection to profile page successful. 'First Name: Kerolos' is displayed.");
        } catch (TimeoutException e) {
            // Log the error and take a screenshot for debugging
            logger.error("Profile page not loaded or 'First Name: Kerolos' not found: " + e.getMessage());
            throw new RuntimeException("Profile page not loaded or 'First Name: Kerolos' not found.", e);
        }
        Thread.sleep(3000); // Pause for recording
    }

    @Test(priority = 2)
    public void topOrganizationRedirectionTest() throws InterruptedException {
        logger.info("Starting topOrganizationRedirectionTest...");

        // Wait for home page to load completely
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='MuiBox-root css-164kvcj']")));  // Home page element
        logger.info("Home page loaded completely.");
        Thread.sleep(3000); // Pause for recording

        // Click on "Test organization" link from Top Organizations section
        WebElement organizationLink = driver.findElement(By.xpath("//a[contains(@href, '/organizations/673f44110e1a58d68302dba2/')]"));
        organizationLink.click();
        logger.info("Clicked on 'Test organization' link.");
        Thread.sleep(3000); // Pause for recording

        // Wait for redirection to Manage Organizations page
        WebElement manageOrganizationsHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Test organization')]")));
        Assert.assertTrue(manageOrganizationsHeader.isDisplayed(), "Redirection to Manage Organizations page failed.");
        logger.info("Redirection to Manage Organizations page successful.");
        Thread.sleep(3000); // Pause for recording
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }
}