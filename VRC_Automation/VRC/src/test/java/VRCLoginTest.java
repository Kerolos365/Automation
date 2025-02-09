import org.openqa.selenium.*;
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

public class VRCLoginTest {
    private static final Logger logger = LoggerFactory.getLogger(VRCLoginTest.class);
    private WebDriver driver;
    private VRCLoginPage vrcLoginPage;

    @BeforeClass
    public void setUp() {
        logger.info("Setting up WebDriver for VRC...");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        vrcLoginPage = new VRCLoginPage(driver);
        driver.get("http://vrc.genesiscreations.co:3000/login/?returnUrl=%2Forganizations%2F");
    }

    @Test(priority = 1)
    public void testCase1_ShortPassword() throws InterruptedException {
        logger.info("Running Test Case 1: Invalid Password...");
        vrcLoginPage.clearFields();
        Thread.sleep(2000);
        vrcLoginPage.enterEmail("kerolos.maged@genesiscreations.co");
        Thread.sleep(2000);
        vrcLoginPage.enterPassword("invalid");
        Thread.sleep(2000);
        vrcLoginPage.clickLoginButton();
        Thread.sleep(2000);

        String expectedMessage = "Username/Password are invalid.";
        String actualMessage = vrcLoginPage.getPopupText();
        Assert.assertEquals(actualMessage, expectedMessage, "Error message mismatch!");
        logger.info("Popup message displayed correctly: " + actualMessage);
    }

    // Test Case 2: Valid login and redirection to home page
    @Test(priority = 2)
    public void testCase2_ValidLoginAndWelcomeMessage() throws InterruptedException {
        logger.info("Running Test Case 2: Valid Login and Welcome Message...");

        // Navigate to the login page again to ensure a clean state
        driver.get("http://vrc.genesiscreations.co:3000/login/?returnUrl=%2Forganizations%2F");

        // Clear fields and perform valid login
        vrcLoginPage.clearFields();
        vrcLoginPage.enterEmail(""); // it is a shortcut as system write admin mail autofill and i tried to solve it but the issue has to be solved from webteam
        vrcLoginPage.enterPassword(""); // Valid password
        vrcLoginPage.clickLoginButton();

        // Wait for 2 seconds after login to ensure the home page loads
        Thread.sleep(2000); // 2-second delay

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

    // Helper method for delays
    private void waitFor(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class VRCLoginPage {
    private static final Logger logger = LoggerFactory.getLogger(VRCLoginPage.class);
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By emailField = By.id(":Rhtadaf6:");
    private By passwordField = By.id("auth-login-v2-password");
    private By loginButton = By.cssSelector("button[type='submit']");
    private By popupMessage = By.xpath("//div[contains(text(), 'Username/Password are invalid.')]");

    public VRCLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Clear fields aggressively
    public void clearFields() {
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));

        // JavaScript to force-clear fields
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", emailElement);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", passwordElement);
        emailElement.clear();
        passwordElement.clear();

        logger.info("Fields cleared. Email: '{}', Password: '{}'",
                emailElement.getAttribute("value"),
                passwordElement.getAttribute("value")
        );
    }

    public void enterEmail(String email) {
        WebElement emailElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        emailElement.clear();
        emailElement.sendKeys(email);
        logger.info("Entered email: {}", email);
    }

    public void enterPassword(String password) {
        WebElement passwordElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passwordElement.clear();
        passwordElement.sendKeys(password);
        logger.info("Entered password: {}", password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        logger.info("Clicked login button");
    }

    public boolean isPopupMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(popupMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPopupText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(popupMessage)).getText().trim();
        } catch (Exception e) {
            return "Popup not found";
        }
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public String getEmailFieldValue() {
        return driver.findElement(emailField).getAttribute("value").trim();
    }

    public String getPasswordFieldValue() {
        return driver.findElement(passwordField).getAttribute("value").trim();
    }
}