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

public class Courses {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(Courses.class);

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

        // Navigate to "Courses" after login
        WebElement coursesLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='MuiBox-root css-164kvcj']/p[text()='Courses']")
        ));
        coursesLink.click();
        logger.info("Navigated to 'Courses' section.");
    }

    @Test(priority = 1)
    public void testSearchCourse() {
        logger.info("Starting testSearchCourse...");

        // Click on the search textbox
        WebElement searchTextBox = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='Find your course' and @type='search']")
        ));
        searchTextBox.click();
        logger.info("Clicked on the search textbox.");

        // Clear any pre-filled text and enter "Medical"
        searchTextBox.clear();
        searchTextBox.sendKeys("Medical");
        logger.info("Entered 'Medical' in the search textbox.");

        // Verify that "Medical Hygiene Training in VR" is displayed in the search results
        WebElement medicalCourseResult = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h6[contains(@class, 'MuiTypography-h6') and contains(text(), 'Medical Hygiene Training in VR')]")
        ));
        Assert.assertTrue(medicalCourseResult.isDisplayed(), "'Medical Hygiene Training in VR' not found in search results!");
        logger.info("'Medical Hygiene Training in VR' result is displayed in the search results.");
    }

    @Test(priority = 2)
    public void testNavigateToCourseDetails() {
        logger.info("Starting testNavigateToCourseDetails...");

        // Click on the "Medical Hygiene Training in VR" course title
        WebElement medicalCourseTitle = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//h6[contains(@class, 'MuiTypography-h6') and contains(text(), 'Medical Hygiene Training in VR')]")
        ));
        medicalCourseTitle.click();
        logger.info("Clicked on the 'Medical Hygiene Training in VR' course title.");

        // Verify redirection to the course details page and check if the title matches
        WebElement courseTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(@class, 'MuiTypography-h5') and contains(text(), 'Medical Hygiene Training in VR')]")
        ));
        Assert.assertTrue(courseTitle.isDisplayed(), "'Medical Hygiene Training in VR' title not found on the course details page!");
        logger.info("Redirected to the course details page and 'Medical Hygiene Training in VR' title is displayed.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed.");
        }
    }
}