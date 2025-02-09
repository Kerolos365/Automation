import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public class LoginTest {
    private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeClass
    public void setUp() {
        logger.info("Setting up WebDriver...");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }

    @Test(priority = 1)
    public void testCase1_EmptyEmailAndPassword() {
        logger.info("Running Test Case 1: Empty Email and Password...");
        driver.get("https://ark.genesiscreations.co/login/?returnUrl=%2Fhome%2F");

        // Click login button without entering email or password
        loginPage.clickLoginButton();

        // Verify no popup message is displayed (issue)
        boolean isPopupDisplayed = loginPage.isPopupMessageDisplayed();
        Assert.assertFalse(isPopupDisplayed, "Popup message is displayed, but it should not be.");
    }

    @Test(priority = 2)
    public void testCase2_ShortPassword() {
        logger.info("Running Test Case 2: Short Password...");
        driver.get("https://ark.genesiscreations.co/login/?returnUrl=%2Fhome%2F");

        // Enter valid email and password less than 8 characters
        loginPage.login("valid-email@example.com", "short");

        // Verify no popup message is displayed (issue)
        boolean isPopupDisplayed = loginPage.isPopupMessageDisplayed();
        Assert.assertFalse(isPopupDisplayed, "Popup message is displayed, but it should not be.");
    }

    @Test(priority = 3)
    public void testCase3_InvalidEmailAndPassword() {
        logger.info("Running Test Case 3: Invalid Email and Password...");
        driver.get("https://ark.genesiscreations.co/login/?returnUrl=%2Fhome%2F");

        // Enter invalid email and password (more than 8 characters)
        loginPage.login("invalid-email@example.com", "InvalidPassword123");

        // Verify popup message is displayed (expected behavior)
        boolean isPopupDisplayed = loginPage.isPopupMessageDisplayed();
        Assert.assertTrue(isPopupDisplayed, "Popup message is not displayed, but it should be.");
        logger.info("Popup message is displayed as expected. Test case passed.");
    }

    @Test(priority = 4)
    public void testCase4_ValidLogin() {
        logger.info("Running Test Case 4: Valid Login...");
        driver.get("https://ark.genesiscreations.co/login/?returnUrl=%2Fhome%2F");

        // Enter valid email and password
        loginPage.login("kerolosmaged4@gmail.com", "Test123456");

        // Wait for the Welcome message on the home page
        WebElement welcomeMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h5[contains(@class, 'MuiTypography-h5') and contains(text(), 'Welcome')]")
                ));

        // Verify the Welcome message is displayed
        Assert.assertTrue(welcomeMessage.isDisplayed(), "Welcome message is not displayed. Login might have failed.");
        logger.info("Welcome message is displayed. Login successful!");
    }

    @AfterClass
    public void tearDown() {
        logger.info("Tearing down WebDriver...");
        if (driver != null) {
            driver.quit();
        }
    }
}

class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Updated Locators
    private By emailField = By.xpath("//input[@placeholder='user@genesiscreations.co']");
    private By passwordField = By.id("auth-login-v2-password");
    private By loginButton = By.xpath("//button[text()='Log In']");
    private By popupMessage = By.xpath("//div[@style='position:fixed;z-index:9999;top:16px;left:16px;right:16px;bottom:16px;pointer-events:none']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Custom method to wait for a specified time (in milliseconds)
    private void waitFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enterEmail(String email) {
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        emailElement.clear();
        emailElement.sendKeys(email);
        waitFor(3000); // 3-second delay
    }

    public void enterPassword(String password) {
        WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passwordElement.clear();
        passwordElement.sendKeys(password);
        waitFor(3000); // 3-second delay
    }

    public void clickLoginButton() {
        WebElement loginButtonElement = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButtonElement.click();
        waitFor(3000); // 3-second delay
    }

    public boolean isPopupMessageDisplayed() {
        try {
            WebElement popupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(popupMessage));
            return popupElement.isDisplayed();
        } catch (Exception e) {
            return false; // Popup message is not displayed
        }
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }
}