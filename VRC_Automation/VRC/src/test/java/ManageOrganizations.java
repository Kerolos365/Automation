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

        // Navigate to "Manage Organizations" after login
        WebElement manageOrganizationsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//p[contains(@class, 'MuiTypography-body1') and contains(text(), 'Manage Organizations')]")
        ));
        manageOrganizationsLink.click();
        logger.info("Navigated to 'Manage Organizations' section.");
    }

    @Test(priority = 1)
    public void testSearchOrganization() {
        logger.info("Starting testSearchOrganization...");

        // Click on the search textbox
        WebElement searchTextBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Search Organization' and @type='text']")
        ));
        searchTextBox.click();
        logger.info("Clicked on the search textbox.");

        // Clear any pre-filled text and enter "NW"
        searchTextBox.clear();
        searchTextBox.sendKeys("NW");
        logger.info("Entered 'NW' in the search textbox.");

        // Wait for the search results to load
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(@class, 'css-732qq2') and contains(text(), 'NW Telepharmacy')]")
        ));
        logger.info("Search results loaded.");

        // Verify that "NW Telepharmacy" is displayed in the search results
        WebElement nwTelepharmacyLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(@class, 'css-732qq2') and contains(text(), 'NW Telepharmacy')]")
        ));
        Assert.assertTrue(nwTelepharmacyLink.isDisplayed(), "'NW Telepharmacy' not found in search results!");
        logger.info("'NW Telepharmacy' is displayed in the search results.");
    }

    @Test(priority = 2)
    public void testExportButton() {
        logger.info("Starting testExportButton...");

        // Click on Export button
        WebElement exportButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Export')]")
        ));
        exportButton.click();
        logger.info("Clicked on the 'Export' button.");
    }

    @Test(priority = 3)
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

        // Verify the message "No organizations found for the selected date range" is displayed
        WebElement noOrganizationsMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='MuiBox-root css-3wt5m6' and contains(text(), 'No organizations found for the selected date range')]")
        ));
        Assert.assertTrue(noOrganizationsMessage.isDisplayed(), "Expected message not displayed.");
        logger.info("Message 'No organizations found for the selected date range' is displayed.");
    }

    @Test(priority = 4)
    public void testAddOrganization() {
        logger.info("Starting testAddOrganization...");

        // Click on "Add Organization" button
        WebElement addOrganizationButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Add Organization')]")
        ));
        addOrganizationButton.click();
        logger.info("Clicked on 'Add Organization' button.");

        // Verify redirection to the "Add Organization" page
        WebElement addOrganizationTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(@class, 'MuiTypography-h5') and contains(text(), 'Add Organziation')]")
        ));
        Assert.assertTrue(addOrganizationTitle.isDisplayed(), "Redirect to 'Add Organization' page failed.");
        logger.info("Redirected to the 'Add Organization' page successfully.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }
}