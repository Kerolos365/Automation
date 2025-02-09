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
    }

    @Test(priority = 1)
    public void testViewProfileRedirection() {
        logger.info("Starting testViewProfileRedirection...");

        // Wait for the "View Profile" button to be clickable and click it
        WebElement viewProfileButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, '/users/6698da304f522a3825b10b99/') and contains(text(), 'View Profile')]")
        ));
        viewProfileButton.click();
        logger.info("Clicked on 'View Profile' button.");

        // Wait for the Profile Page to load and verify the "Administrator" heading
        WebElement administratorHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h5[contains(@class, 'MuiTypography-h5') and contains(text(), 'Adminstrator')]")
        ));
        Assert.assertTrue(administratorHeading.isDisplayed(), "Redirection to Profile Page failed. 'Administrator' heading not found.");
        logger.info("Redirection to Profile Page successful. 'Administrator' heading is displayed.");
    }

    @Test(priority = 2)
    public void testNWTelepharmacyRedirection() {
        logger.info("Starting testNWTelepharmacyRedirection...");

        // Perform login again (as per the requirement)
        driver.get("http://vrc.genesiscreations.co:3000/login/?returnUrl=%2Forganizations%2F663cbce3660b94e9321dca97%2F");
        logger.info("Navigated back to the login page for re-login.");

        // Wait for the page to load completely
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        logger.info("Page loaded completely.");

        // Perform login by clicking the "Log In" button
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Log In')]")
        ));
        loginButton.click();
        logger.info("Clicked the 'Log In' button for re-login.");

        // Wait for the "NW Telepharmacy" link to be clickable and click it
        WebElement nwTelepharmacyLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, '/organizations/663cbce3660b94e9321dca97/') and contains(text(), 'NW Telepharmacy')]")
        ));
        nwTelepharmacyLink.click();
        logger.info("Clicked on 'NW Telepharmacy' link.");

        // Wait for the Organization Page to load and verify the "NW Telepharmacy" heading
        WebElement nwTelepharmacyHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[contains(@class, 'MuiTypography-h4') and contains(text(), 'NW Telepharmacy')]")
        ));
        Assert.assertTrue(nwTelepharmacyHeading.isDisplayed(), "Redirection to Organization Page failed. 'NW Telepharmacy' heading not found.");
        logger.info("Redirection to Organization Page successful. 'NW Telepharmacy' heading is displayed.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }
}