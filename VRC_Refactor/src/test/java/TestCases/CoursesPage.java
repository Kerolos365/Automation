package TestCases;
import TestComponent.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.time.Duration;

public class CoursesPage extends BaseTest {
    public WebDriverWait wait;

    @BeforeMethod
    public void loginFirst() throws IOException {
        // Initialize WebDriverWait first
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Perform login
        driver.get(LOGIN_URL);
        login.loginNow(VALID_EMAIL, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlContains("/home"));
        courses.NavigateToCoursesPage();
    }
    @Test(priority = 1, description = "Search Courses button")
    public void SearchCourses() throws IOException, InterruptedException {
        // 1. Check if the Search button is displayed
        boolean isSearchButtonDisplayed = driver.findElement(CoursesSearchBy).isDisplayed();
        System.out.println("Is Search button displayed: " + isSearchButtonDisplayed);
        Assert.assertTrue(isSearchButtonDisplayed, "Search button is not displayed");

        // 2. Click the Search button
        WebElement searchButton = driver.findElement(CoursesSearchBy);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        // 3. Enter search text
        Thread.sleep(2000);
        searchButton.sendKeys(Course_Name);
        Thread.sleep(2000); // Wait for the search results to load
        // 4. Scroll to the search result
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 500);");
        // 5. Check if the course name is displayed
        boolean isCourseNameDisplayed = driver.findElement(Courses_Name).isDisplayed();
        System.out.println("Is Course name displayed: " + isCourseNameDisplayed);
        // 6. Assert that the course name is displayed
        Assert.assertTrue(isCourseNameDisplayed, "Course name is not displayed");
        // 7. Click the course name
        WebElement courseName = driver.findElement(Courses_Name);
        wait.until(ExpectedConditions.elementToBeClickable(courseName)).click();
        // 8. Wait for the course details to load
        Thread.sleep(2000);
        // 9. Scroll to the course details
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 500);");
        // 10. Check if the click redirects to the right page
        boolean isCourseHeaderDisplayed = driver.findElement(Course_Header).isDisplayed();
        System.out.println("Is Course header displayed: " + isCourseHeaderDisplayed);
        String courseHeader = driver.findElement(Course_Header).getText();
        // 11. Assert that the course header is displayed
        Assert.assertTrue(isCourseHeaderDisplayed, "Course header is not displayed");
        Assert.assertTrue(courseHeader.contains(Course_Name), "Expected course name to be part of the header, but got: " + Course_Header);
        // 12. Check if the course details are displayed
        boolean isCourseDetailsDisplayed = driver.findElement(Course_Details).isDisplayed();
        System.out.println("Is Course details displayed: " + isCourseDetailsDisplayed);
        // 13. Assert that the course details are displayed
        Assert.assertTrue(isCourseDetailsDisplayed, "Course details are not displayed");
    }
    @Test(priority = 2, description = "Check First Module")
    public void CheckFirstModule() throws IOException, InterruptedException {
        // 1.Click on Courses button
        WebElement courseName = driver.findElement(Courses_Name);
        wait.until(ExpectedConditions.elementToBeClickable(courseName)).click();
        Thread.sleep(2000);
        // 1. Check if the First Module is displayed
        boolean isFirstModuleDisplayed = driver.findElement(First_Module).isDisplayed();
        System.out.println("Is First Module displayed: " + isFirstModuleDisplayed);
        // 2. Assert that the First Module is displayed
        Assert.assertTrue(isFirstModuleDisplayed, "First Module is not displayed");
        // 3. Click the First Module
        WebElement firstModule = driver.findElement(First_Module);
        wait.until(ExpectedConditions.elementToBeClickable(firstModule)).click();
        // 4. Wait for the First Module to load
        Thread.sleep(2000);
        // 5. Scroll to the First Module
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 500);");
        // 6. Check if the First Module Count is displayed
        boolean isFirstModuleCountDisplayed = driver.findElement(First_Module_Count).isDisplayed();
        System.out.println("Is First Module Count displayed: " + isFirstModuleCountDisplayed);
        // 7. Assert that the First Module Count is displayed
        Assert.assertTrue(isFirstModuleCountDisplayed, "First Module Count is not displayed");
        // 8. Check if the First Module Count is correct
        String firstModuleCount = driver.findElement(First_Module_Count).getText();
        Assert.assertTrue(firstModuleCount.contains("6"), "Expected 6 interactions, but got: " + firstModuleCount);
        // 9. Check if the First Module interactions are displayed
        boolean isWashingHandsInteractionDisplayed = driver.findElement(WashingHands_interaction_First_Module).isDisplayed();
        System.out.println("Is Washing Hands interaction displayed: " + isWashingHandsInteractionDisplayed);
        boolean isDonningGlovesInteractionDisplayed = driver.findElement(DonningGloves_interaction_First_Module).isDisplayed();
        System.out.println("Is Donning Gloves interaction displayed: " + isDonningGlovesInteractionDisplayed);
        boolean isCleaningPECInteractionDisplayed = driver.findElement(CleaningPEC_interaction_First_Module).isDisplayed();
        System.out.println("Is Cleaning PEC interaction displayed: " + isCleaningPECInteractionDisplayed);
        boolean isDonningPPEInteractionDisplayed = driver.findElement(DonningPPE_interaction_First_Module).isDisplayed();
        System.out.println("Is Donning PPE interaction displayed: " + isDonningPPEInteractionDisplayed);
        boolean isDonningGownInteractionDisplayed = driver.findElement(DonningGown_interaction_First_Module).isDisplayed();
        System.out.println("Is Donning Gown interaction displayed: " + isDonningGownInteractionDisplayed);
        boolean isCleaningSinkInteractionDisplayed = driver.findElement(CleaningSink_First_Module).isDisplayed();
        System.out.println("Is Cleaning Sink interaction displayed: " + isCleaningSinkInteractionDisplayed);
        // 10. Assert that the First Module interactions are displayed
        Assert.assertTrue(isWashingHandsInteractionDisplayed, "Washing Hands interaction is not displayed");
        Assert.assertTrue(isDonningGlovesInteractionDisplayed, "Donning Gloves interaction is not displayed");
        Assert.assertTrue(isCleaningPECInteractionDisplayed, "Cleaning PEC interaction is not displayed");
        Assert.assertTrue(isDonningPPEInteractionDisplayed, "Donning PPE interaction is not displayed");
        Assert.assertTrue(isDonningGownInteractionDisplayed, "Donning Gown interaction is not displayed");
        Assert.assertTrue(isCleaningSinkInteractionDisplayed, "Cleaning Sink interaction is not displayed");
    }
    @Test(priority = 3, description = "Check Second Module")
    public void CheckSecondModule() throws IOException, InterruptedException {
        // 1. Click on Courses button
        WebElement courseName = driver.findElement(Courses_Name);
        wait.until(ExpectedConditions.elementToBeClickable(courseName)).click();
        Thread.sleep(2000);
        // 2. Check if the Second Module is displayed
        boolean isSecondModuleDisplayed = driver.findElement(Second_Module).isDisplayed();
        System.out.println("Is Second Module displayed: " + isSecondModuleDisplayed);
        // 3. Assert that the Second Module is displayed
        Assert.assertTrue(isSecondModuleDisplayed, "Second Module is not displayed");
        // 4. Click the Second Module
        WebElement secondModule = driver.findElement(Second_Module);
        wait.until(ExpectedConditions.elementToBeClickable(secondModule)).click();
        // 5. Wait for the Second Module to load
        Thread.sleep(2000);
        // 6. Scroll to the Second Module
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 500);");
        // 7. Check if the Second Module Count is displayed
        boolean isSecondModuleCountDisplayed = driver.findElement(Second_Module_Count).isDisplayed();
        System.out.println("Is Second Module Count displayed: " + isSecondModuleCountDisplayed);
        // 8. Assert that the Second Module Count is displayed
        Assert.assertTrue(isSecondModuleCountDisplayed, "Second Module Count is not displayed");
        // 9. Check if the Second Module Count is correct
        String secondModuleCount = driver.findElement(Second_Module_Count).getText();
        Assert.assertTrue(secondModuleCount.contains("2"), "Expected 2 interactions, but got: " + secondModuleCount);
        // 10. Check if the Second Module interactions are displayed
        boolean isCleaningWasteContainerInteractionDisplayed = driver.findElement(CleaningWasteContainer_interaction_Second_Module).isDisplayed();
        System.out.println("Is Cleaning Waste Container interaction displayed: " + isCleaningWasteContainerInteractionDisplayed);
        boolean isTableCleaningInteractionDisplayed = driver.findElement(TableCleaning_interaction_Second_Module).isDisplayed();
        System.out.println("Is Table Cleaning interaction displayed: " + isTableCleaningInteractionDisplayed);
        // 11. Assert that the Second Module interactions are displayed
        Assert.assertTrue(isCleaningWasteContainerInteractionDisplayed, "Cleaning Waste Container interaction is not displayed");
        Assert.assertTrue(isTableCleaningInteractionDisplayed, "Table Cleaning interaction is not displayed");
    }

    @Test(priority = 4, description = "Check New Session button")
    public void CheckNewSession() throws IOException, InterruptedException {
        // 1. Click on Courses button
        WebElement courseName = driver.findElement(Courses_Name);
        wait.until(ExpectedConditions.elementToBeClickable(courseName)).click();
        Thread.sleep(2000);
        // 2. Check if the New Session button is displayed
        boolean isNewSessionButtonDisplayed = driver.findElement(NewSession_Button).isDisplayed();
        System.out.println("Is New Session button displayed: " + isNewSessionButtonDisplayed);
        // 3. Assert that the New Session button is displayed
        Assert.assertTrue(isNewSessionButtonDisplayed, "New Session button is not displayed");
        // 4. Click the New Session button
        WebElement newSessionButton = driver.findElement(NewSession_Button);
        wait.until(ExpectedConditions.elementToBeClickable(newSessionButton)).click();
        // 5. Wait for the New Session page to load
        Thread.sleep(2000);
         // 7. Assert that redirects to the New Session page
        Assert.assertTrue(driver.getCurrentUrl().contains("/create-configuration"), "Expected to contain '/create-configuration' but got: " + driver.getCurrentUrl());
    }

    @Test(priority = 5, description = "Check New License button")
    public void CheckNewLicense() throws IOException, InterruptedException {
        // 1. Click on Courses button
        WebElement courseName = driver.findElement(Courses_Name);
        wait.until(ExpectedConditions.elementToBeClickable(courseName)).click();
        Thread.sleep(2000);
        // 2. Check if the New License button is displayed
        boolean isNewLicenseButtonDisplayed = driver.findElement(NewLicense_Button).isDisplayed();
        System.out.println("Is New License button displayed: " + isNewLicenseButtonDisplayed);
        // 3. Assert that the New License button is displayed
        Assert.assertTrue(isNewLicenseButtonDisplayed, "New License button is not displayed");
        // 4. Click the New License button
        WebElement newLicenseButton = driver.findElement(NewLicense_Button);
        wait.until(ExpectedConditions.elementToBeClickable(newLicenseButton)).click();
        // 5. Wait for the New License page to load
        Thread.sleep(2000);
         // 7. Assert that redirects to the New License page
        Assert.assertTrue(driver.getCurrentUrl().contains("/licenses"), "Expected to contain '/licenses' but got: " + driver.getCurrentUrl());
    }
}