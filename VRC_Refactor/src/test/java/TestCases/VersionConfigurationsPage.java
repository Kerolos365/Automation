package TestCases;

import TestComponent.BaseTest;
import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class VersionConfigurationsPage extends BaseTest {
    public WebDriverWait wait;
    public String versionName = "Test" + new Faker().number().digits(5);

    @BeforeMethod
    public void loginFirst() throws IOException {
        // Initialize WebDriverWait first
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Perform login
        driver.get(LOGIN_URL);
        login.loginNow(VALID_EMAIL, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlContains("/home"));
        versionConfigurations.NavigateToVersionConfigurationsPage();
    }

    @Test(priority = 1, description = "Check export button")
    public void CheckExport() throws IOException {
        // Check if the Export button is displayed
        boolean isExportButtonDisplayed = driver.findElement(ExportButtonBy).isDisplayed();
        System.out.println("Is Export button displayed: " + isExportButtonDisplayed);
        // Assert that the Export button is displayed
        Assert.assertTrue(isExportButtonDisplayed, "Export button is not displayed");
        // Click the Export button
        WebElement exportButton = driver.findElement(ExportButtonBy);
        // Wait for the Export button to be clickable and then click it
        wait.until(ExpectedConditions.elementToBeClickable(exportButton)).click();
    }

    @Test(priority = 5, description = "Search Versions ")
    public void SearchVersions() throws IOException, InterruptedException {
        // 1. Check if the Search button is displayed
        boolean isSearchButtonDisplayed = driver.findElement(SearchVersionsBy).isDisplayed();
        System.out.println("Is Search button displayed: " + isSearchButtonDisplayed);
        Assert.assertTrue(isSearchButtonDisplayed, "Search button is not displayed");

        // 2. Click the Search button
        WebElement searchButton = driver.findElement(SearchVersionsBy);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        // 3. Enter search text
        Thread.sleep(2000); // Wait for the search button to be clickable
        searchButton.sendKeys(versionName);
        Thread.sleep(2000); // Wait for the search results to load

        // 4. Wait for results and verify
        List<WebElement> Users = driver.findElements(Version_Name_ColumnBy);
        boolean isFound = false;
        for (WebElement org : Users) {
            if (org.getText().trim().contains(versionName)) {
                isFound = true;
                break;
            }
        }
        Assert.assertTrue(isFound, "Search user '" + versionName + "' was not found in the users list");
    }

    @Test(priority = 6, description = "Date Range button")
    public void DateRange() throws IOException, InterruptedException {

        // Open Date Range filter
        wait.until(ExpectedConditions.elementToBeClickable(VersionDateRangeButtonBy)).click();

        // Apply today's date
        wait.until(ExpectedConditions.elementToBeClickable(SetDateButtonBy)).click();

        // Wait until the first element appears (table is not empty)
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(CreatedAtColumnBy));

        // Short delay to let the page stabilize
        Thread.sleep(1000);  // Add this to prevent background refresh glitches

        // Get fresh list after page settles
        List<WebElement> organizations = driver.findElements(CreatedAtColumnBy);

        Assert.assertFalse(organizations.isEmpty(), "❌ No organizations created for today's date.");

        String todayPrefix = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        for (WebElement element : organizations) {
            String dateText = element.getText();
            Assert.assertFalse(
                    dateText.contains(todayPrefix),
                    "❌ Organization date doesn't match today's date! Found: " + dateText
            );
        }
    }

    @Test(priority = 3, description = "Select Version Type Dropdown")
    public void SelectVersionTypeDropdown() throws IOException, InterruptedException {
        // 1. Open and apply industry filter
        Thread.sleep(2000); // Wait for the dropdown to be clickable
        WebElement SelectVersionTypeDropdown = wait.until(ExpectedConditions.elementToBeClickable(Versions_Type_DropdownBy));
        SelectVersionTypeDropdown.click();
        wait.until(ExpectedConditions.elementToBeClickable(VersionTypeOption)).click();
        // 2. Verify selected Type
        List<WebElement> VersionType = driver.findElements(Versions_Type_columnBy);
        boolean isFound = false;
        for (int i = 0; i < VersionType.size(); i++) {
            // Re-fetch the element each loop to avoid stale reference
            WebElement org = driver.findElements(Versions_Type_columnBy).get(i);
            if (org.getText().equalsIgnoreCase(Version_Type)) {
                isFound = true;
                break;
            }
        }
        // Assert that the selected status is found
        Assert.assertTrue(isFound, "Selected Version Type '" + Version_Type + "' was not found in the version list");
    }

    @Test(priority = 4, description = "Select Version Checkbox")
    public void SelectVersionCheckbox() throws IOException, InterruptedException {
        // 1. Check if the Select Version Checkbox is displayed
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".MuiDataGrid-row")));
        WebElement CheckboxButton = wait.until(ExpectedConditions.presenceOfElementLocated(SelectRowCheckbox));
        Thread.sleep(500);
        Assert.assertTrue(CheckboxButton.isDisplayed(), "First row checkbox is not displayed.");

        // 2. Click the Select Version Checkbox
        WebElement selectVersionCheckbox = driver.findElement(SelectRowCheckbox);
        wait.until(ExpectedConditions.elementToBeClickable(selectVersionCheckbox)).click();
        Thread.sleep(2000); // Optional: Wait for the checkbox to be selected

        // 3. Get Name for this Version
        WebElement versionName = driver.findElement(Version_Name_ColumnBy);
        String versionNameText = versionName.getText();
        System.out.println("Selected Version Name: " + versionNameText);

         // 4. Click on Archive button
        Thread.sleep(2000); //  Wait for the action button to be clickable
        WebElement archiveButton = driver.findElement(ArchiveButton);
        archiveButton.click();
        Thread.sleep(2000);

        // 5. Confirm the action By Check Archived versions
        WebElement archivedVersions = wait.until(ExpectedConditions.elementToBeClickable(ArchivedVersionsButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", archivedVersions);

        // 6. search on un archived versions to make sure it exists
        WebElement searchButton = driver.findElement(SearchVersionsBy);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        Thread.sleep(2000); // Wait for the search button to be clickable
        searchButton.sendKeys(versionNameText);

        // 7. Verify that the version is archived
        Thread.sleep(3000); // Wait for the archived versions to load
        List<WebElement> archivedVersionName = driver.findElements(Version_Name_ColumnBy);
        boolean isArchived = false;
        for (WebElement org : archivedVersionName) {
            if (org.getText().trim().equalsIgnoreCase(versionNameText)) {
                isArchived = true;
                break;
            }
        }
        Assert.assertTrue(isArchived, "Version '" + versionNameText + "' was not found in the archived versions list");
    }

    @Test(priority = 2, description = "Add Version Configuration")
    public void AddVersionConfiguration() throws IOException, InterruptedException {
        // 1. Click on Add Version Configuration button
        WebElement addVersionButton = driver.findElement(AddVersionButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(addVersionButton)).click();

        // 2. Enter Version Name
        WebElement versionNameInput = driver.findElement(VesrionNameInputBy);
        versionNameInput.sendKeys(versionName);
        Thread.sleep(2000); // Wait for the input to be filled

        // 3. Click Next button
        WebElement nextButton = driver.findElement(VesrionNextButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();

        // 4. Select Course
        WebElement courseCheckbox = driver.findElement(VRC_CourseBy);
        courseCheckbox.click();
        Thread.sleep(2000); //  Wait for the scroll to complete
        // Scroll to the course checkbox
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", courseCheckbox);
        Thread.sleep(2000); //  Wait for the scroll to complete

        // 5. Select Module
        WebElement moduleCheckbox = driver.findElement(VRC_FirstModuleBy);
        moduleCheckbox.click();
        Thread.sleep(2000); // Wait for the scroll to complete

        // 6. Click Create Configuration button
        WebElement createConfigButton = driver.findElement(CreateConfigButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(createConfigButton)).click();

        // 7. Verify success message
        WebElement successMessage = driver.findElement(confMessagenBy);
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        String messageText = successMessage.getText();
        Assert.assertTrue(messageText.contains("Version configuration created successfully"),
                "Success message not displayed or incorrect: " + messageText);

        // 8.Assign Users to the Version
        // 8.1 Choose anu user which its status is activated and verified
        List<WebElement> activatedUsers = driver.findElements(ActivatedUsersAccessColumn);
        List<WebElement> verifiedUsers = driver.findElements(VerifiedUsersStatusColumn);
        boolean isFound = false;
        for (int i = 0; i < activatedUsers.size(); i++) {
            // Re-fetch the element each loop to avoid stale reference
            WebElement org = driver.findElements(ActivatedUsersAccessColumn).get(i);
            WebElement org1 = driver.findElements(VerifiedUsersStatusColumn).get(i);
            if (org.getText().equalsIgnoreCase("Activated") && org1.getText().equalsIgnoreCase("Verified")) {
                isFound = true;
                break;
            }
        }
        // 8.2 Click on the selected users checkbox Action button
        // Scroll to the action button
        Thread.sleep(2000);
        WebElement actionButton = driver.findElement(SelectRowCheckbox);
        wait.until(ExpectedConditions.elementToBeClickable(actionButton)).click();
        Thread.sleep(2000); //  Wait for the action button to be clickable
        WebElement assignUsersButton = driver.findElement(AssignUsersButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(assignUsersButton)).click();
        Thread.sleep(2000); // Wait for the Assign Users button to be clickable
        // 8.3 Verify that the Assign Users button is displayed
        WebElement SuccessMessage = driver.findElement(confMessagenBy);
        wait.until(ExpectedConditions.visibilityOf(SuccessMessage));
        String successMessageText = SuccessMessage.getText();
        Assert.assertTrue(successMessageText.contains("Assigned users to session successfully"),
                "Success message not displayed or incorrect: " + successMessageText);
        // 9. Verify that the new version is displayed in the list
        List<WebElement> versionNameList = driver.findElements(NewSessionNameColumnBy);
        boolean isVersionFound = false;
        for (WebElement org : versionNameList) {
            if (org.getText().trim().equalsIgnoreCase(versionName)) {
                isVersionFound = true;
                break;
            }
        }
        // Assert the system redirects to session history page by url
        Thread.sleep(2000);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/sessions"),
                "Expected to contain '/sessions' but got: " + currentUrl);
        // Assert that the new version is found
        Assert.assertTrue(isVersionFound, "Version '" + versionName + "' was not found in the version list");
       // Deactivate user Again
        //  Click the action button
        WebElement scrollDiv = driver.findElement(By.xpath("//div[contains(@class, 'MuiDataGrid-scrollbar--horizontal')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth;", scrollDiv);
        WebElement actionButton2 = driver.findElement(By.xpath("//div[@data-field='actions']//button[@type='button']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", actionButton2);
        Thread.sleep(1000); // You can replace with WebDriverWait for better practice
        // Click the "Deactivate" button from the dropdown
        WebElement deactivateButton = driver.findElement(By.xpath("//button[normalize-space()='Deactivate']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deactivateButton);
        Thread.sleep(2000); // Wait for the action to complete
        // Verify success message
        WebElement successMessage2 = driver.findElement(confMessagenBy);
        wait.until(ExpectedConditions.visibilityOf(successMessage2));
        String messageText2 = successMessage2.getText();
        Assert.assertTrue(messageText2.contains("Deactivated user session successfully"),
                "Success message not displayed or incorrect: " + messageText2);
    }

    @Test(priority = 7, description = "Add Version Configuration without selecting any course or module")
    public void AddVersionConfigurationWithMissingStaff() throws IOException, InterruptedException {
        // 1. Click on Add Version Configuration button
        WebElement addVersionButton = driver.findElement(AddVersionButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(addVersionButton)).click();

        // 2. Enter Version Name
        WebElement versionNameInput = driver.findElement(VesrionNameInputBy);
        String versionName = "Test" + new Faker().number().digits(5);
        versionNameInput.sendKeys(versionName);
        Thread.sleep(2000); // Wait for the input to be filled

        // 3. Click Next button
        WebElement nextButton = driver.findElement(VesrionNextButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();

        // 4. Select Course
        WebElement courseCheckbox = driver.findElement(VRC_CourseBy);
        Thread.sleep(2000); //  Wait for the scroll to complete
        // Scroll to the course checkbox
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", courseCheckbox);
        Thread.sleep(2000); //  Wait for the scroll to complete

        // 6. Click Create Configuration button
        WebElement createConfigButton = driver.findElement(CreateConfigButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(createConfigButton)).click();

        // 7. Verify fail message
        WebElement failMessage = driver.findElement(confMessagenBy);
        wait.until(ExpectedConditions.visibilityOf(failMessage));
        String messageText = failMessage.getText();
        Assert.assertTrue(messageText.contains("please select course"),
                "Success message not displayed or incorrect: " + messageText);

        // 8 . click on course only
        Thread.sleep(5000);
        courseCheckbox.click();
        Thread.sleep(2000); //  Wait for the scroll to complete
        // 9. Click Create Configuration button
        wait.until(ExpectedConditions.elementToBeClickable(createConfigButton)).click();

        // 10. Verify fail message
        WebElement failMessage2 = driver.findElement(confMessagenBy);
        wait.until(ExpectedConditions.visibilityOf(failMessage2));
        String messageText2 = failMessage2.getText();
        Assert.assertTrue(messageText2.contains("please select module"),
                "Success message not displayed or incorrect: " + messageText2);
    }
    @Test(priority = 8, description = "Add Version Configuration without entering version name")
    public void AddVersionConfigurationWithMissingVersionName() throws IOException, InterruptedException {
        // 1. Click on Add Version Configuration button
        WebElement addVersionButton = driver.findElement(AddVersionButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(addVersionButton)).click();

        // 2. Enter Version Name
        WebElement versionNameInput = driver.findElement(VesrionNameInputBy);
        versionNameInput.sendKeys("");
        Thread.sleep(2000); // Wait for the input to be filled

        // 3. Click Next button
        WebElement nextButton = driver.findElement(VesrionNextButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();

        // 4. Verify fail message
        WebElement failMessage = driver.findElement(errorNameVesrionMessBy);
        wait.until(ExpectedConditions.visibilityOf(failMessage));
        String messageText = failMessage.getText();
        Assert.assertTrue(messageText.contains("Use letters and numbers only!"),
                "fail message not displayed or incorrect: " + messageText);
    }
}