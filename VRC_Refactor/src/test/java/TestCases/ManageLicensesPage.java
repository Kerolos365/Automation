package TestCases;

import TestComponent.BaseTest;
import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class ManageLicensesPage extends BaseTest {
    private WebDriverWait wait;


    @BeforeMethod
    public void loginFirst() throws IOException {
        // Initialize WebDriverWait first
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Perform login
        driver.get(LOGIN_URL);
        login.loginNow(VALID_EMAIL, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlContains("/home"));
        manageLicenses.NavigateToManageLicensesPage();
    }

    @Test(priority = 1, description = "Check export button")
    public void CheckExport() throws IOException {
        // Check if the Export button is displayed
        boolean isExportButtonDisplayed = driver.findElement(LicensesExportButtonBy).isDisplayed();
        System.out.println("Is Export button displayed: " + isExportButtonDisplayed);
        // Assert that the Export button is displayed
        Assert.assertTrue(isExportButtonDisplayed, "Export button is not displayed");
        // Click the Export button
        WebElement exportButton = driver.findElement(LicensesExportButtonBy);
        // Wait for the Export button to be clickable and then click it
        wait.until(ExpectedConditions.elementToBeClickable(exportButton)).click();
    }

    @Test(priority = 2, description = "Search Users button")
    public void SearchUsers() throws IOException, InterruptedException {
        // 1. Check if the Search input is displayed
        Thread.sleep(3000);
        WebElement searchInput = driver.findElement(SearchLicensessBy);
        Assert.assertTrue(searchInput.isDisplayed(), "Search input is not displayed");
        wait.until(ExpectedConditions.elementToBeClickable(searchInput)).click();

        // 2. Enter search text
        Thread.sleep(2000); // Wait for the search input to be clickable
        searchInput.sendKeys("Genesis");

        // 3. Wait for at least one result to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(LicenseNameColumnBy));

        // 4. Get all licenses
        List<WebElement> licenses = driver.findElements(LicenseNameColumnBy);

        boolean isFound = false;
        for (WebElement license : licenses) {
            String licenseText = license.getText().trim();
            System.out.println("License found: " + licenseText); // Debugging
            if (licenseText.contains("Genesis")) {
                isFound = true;
                break;
            }
        }
        Assert.assertTrue(isFound, "Search Licenses '" + "Genesis" + "' was not found in the Licenses list");
    }

    @Test(priority = 3, description = "Select License Status Dropdown")
    public void SelectLicenseStatusDropdown() throws IOException, InterruptedException {
        // 1. Open and apply STATUS filter
        Thread.sleep(5000); // Wait for the dropdown to be clickable
        WebElement SelectLicenseStatusDropdown = wait.until(ExpectedConditions.elementToBeClickable(SelectLicenseStatusDropdownyBy));
        SelectLicenseStatusDropdown.click();
        wait.until(ExpectedConditions.elementToBeClickable(LicenseStatusOption)).click();
        Thread.sleep(2000); // Optional: Wait for the dropdown to be in view
        // 2. check the desired status
        WebElement gridScroller = driver.findElement(By.className("MuiDataGrid-virtualScroller"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = 1000", gridScroller);
        Thread.sleep(2000); // Wait for the scroll to take effect

        // 3. Verify selected Status
        List<WebElement> LicenseStatus = driver.findElements(LicenseStatusColumnBy);
        boolean isFound = false;
        for (int i = 0; i < LicenseStatus.size(); i++) {
            // Re-fetch the element each loop to avoid stale reference
            WebElement org = driver.findElements(LicenseStatusColumnBy).get(i);
            if (org.getText().equalsIgnoreCase(Licensestatus)) {
                isFound = true;
                break;
            }
        }
        // Assert that the selected status is found
        Assert.assertTrue(isFound, "Selected Status '" + Licensestatus + "' was not found in the Licenses list");
    }

    @Test(priority = 4, description = "Add License button")
    public void AddLicense() throws IOException, InterruptedException {
        // Click the Add License button
        Thread.sleep(3000);
        WebElement addLicenseButton = wait.until(ExpectedConditions.elementToBeClickable(AddLicenseButtonBy));
        addLicenseButton.click();

        // Fill in the form
        driver.findElement(LicenseNamefieldBy).sendKeys(Licenses_NewName);

        // Wait for the dropdown to be clickable and open it
        Thread.sleep(3000); // Optional: Wait for the dropdown to be clickable
        WebElement organizationDropdown = driver.findElement(OrganizationDrobdownBy);
        organizationDropdown.click();

        // Select the first option from the dropdown
        WebElement firstOrganizationOption = driver.findElement(FirstOrganizationOption);
        firstOrganizationOption.click();

        // Wait for the Expire Date field to be clickable
        Thread.sleep(3000);  // Optional: Wait for the Expire Date field to be clickable
        driver.findElement(ExpireDateBy).sendKeys("31-12-2027");

        // Submit the form
        WebElement CreateLicenseButton = driver.findElement(CreateLicenseButtonBy);
        Thread.sleep(2000);  // Optional: Wait for the button to be in view
        CreateLicenseButton.click();

        // Wait for success message
        String successMessage = driver.findElement(SubmitMessageBy).getText();
        Assert.assertTrue(successMessage.contains("License created successfully"),
                "License was not added successfully. Message: " + successMessage);

        // Wait for New Page Header
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(LicenseHeaderBy));
        Assert.assertEquals(header.getText(), "License Contents");

        // Choose VrcCourse
        WebElement VRCCourse = driver.findElement(VrcCourseBy);
        VRCCourse.click();
        Thread.sleep(2000);  //  Wait for the scroll to take effect

        // Choose module
        WebElement Module = driver.findElement(VrcFirstModuleBy);
        Module.click();
        Thread.sleep(2000);  //  Wait for the scroll to take effect

        // Choose interaction
        WebElement Interaction = driver.findElement(VrcFirstInteractionBy);
        Interaction.click();
        Thread.sleep(2000);  //  Wait for the scroll to take effect

        // Click on add Content
        WebElement AddContentButton = driver.findElement(AddContentButtonBy);
        AddContentButton.click();
        Thread.sleep(2000);  //  Wait for the scroll to take effect

        // Wait for success message
        String successMessage2 = driver.findElement(SubmitMessageBy).getText();
        Assert.assertTrue(successMessage2.contains("Added license details successfully"),
                "License was not added successfully. Message: " + successMessage2);
    }
    @Test(priority = 5, description = "Add no content License ")
    public void AddNoContentLicense() throws IOException, InterruptedException {
        // Click the Add License button
        Thread.sleep(3000);
        WebElement addLicenseButton = wait.until(ExpectedConditions.elementToBeClickable(AddLicenseButtonBy));
        addLicenseButton.click();

        // Fill in the form
        Faker faker = new Faker();
        String Licenses_NewName2 = faker.name().firstName();
        driver.findElement(LicenseNamefieldBy).sendKeys(Licenses_NewName2);

        // Wait for the dropdown to be clickable and open it
        Thread.sleep(3000); // Optional: Wait for the dropdown to be clickable
        WebElement organizationDropdown = driver.findElement(OrganizationDrobdownBy);
        organizationDropdown.click();

        // Select the first option from the dropdown
        WebElement firstOrganizationOption = driver.findElement(FirstOrganizationOption);
        firstOrganizationOption.click();

        // Wait for the Expire Date field to be clickable
        Thread.sleep(3000);  // Optional: Wait for the Expire Date field to be clickable
        driver.findElement(ExpireDateBy).sendKeys("31-12-2027");

        // Submit the form
        WebElement CreateLicenseButton = driver.findElement(CreateLicenseButtonBy);
        Thread.sleep(2000);  // Optional: Wait for the button to be in view
        CreateLicenseButton.click();

        // Wait for success message
        String successMessage = driver.findElement(SubmitMessageBy).getText();
        Assert.assertTrue(successMessage.contains("License created successfully"),
                "License was not added successfully. Message: " + successMessage);

        // Wait for New Page Header
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(LicenseHeaderBy));
        Assert.assertEquals(header.getText(), "License Contents");

        // Click on add Content
        WebElement AddContentButton = driver.findElement(AddContentButtonBy);
        AddContentButton.click();
        Thread.sleep(2000);  //  Wait for the scroll to take effect

        // Wait for failure message
        String failureMessage = driver.findElement(SubmitMessageBy).getText();
        Assert.assertTrue(failureMessage.contains("please select course"),
                "License was added successfully. Message: " + failureMessage);
    }
    @Test(priority = 6, description = "Add missing Content to License")
    public void AddNoMissingContentLicense() throws IOException, InterruptedException {
        // Click the Add License button
        Thread.sleep(3000);
        WebElement addLicenseButton = wait.until(ExpectedConditions.elementToBeClickable(AddLicenseButtonBy));
        addLicenseButton.click();

        // Fill in the form
        Faker faker = new Faker();
        String Licenses_NewName3 = faker.name().firstName();
        driver.findElement(LicenseNamefieldBy).sendKeys(Licenses_NewName3);

        // Wait for the dropdown to be clickable and open it
        Thread.sleep(3000); // Optional: Wait for the dropdown to be clickable
        WebElement organizationDropdown = driver.findElement(OrganizationDrobdownBy);
        organizationDropdown.click();

        // Select the first option from the dropdown
        WebElement firstOrganizationOption = driver.findElement(FirstOrganizationOption);
        firstOrganizationOption.click();

        // Wait for the Expire Date field to be clickable
        Thread.sleep(3000);  // Optional: Wait for the Expire Date field to be clickable
        driver.findElement(ExpireDateBy).sendKeys("31-12-2027");

        // Submit the form
        WebElement CreateLicenseButton = driver.findElement(CreateLicenseButtonBy);
        Thread.sleep(2000);  // Optional: Wait for the button to be in view
        CreateLicenseButton.click();

        // Wait for success message
        String successMessage = driver.findElement(SubmitMessageBy).getText();
        Assert.assertTrue(successMessage.contains("License created successfully"),
                "License was not added successfully. Message: " + successMessage);

        // Wait for New Page Header
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(LicenseHeaderBy));
        Assert.assertEquals(header.getText(), "License Contents");

        // Choose VrcCourse
        WebElement VRCCourse = driver.findElement(VrcCourseBy);
        VRCCourse.click();
        Thread.sleep(2000);  //  Wait for the scroll to take effect

        // Choose module
        WebElement Module = driver.findElement(VrcFirstModuleBy);
        Module.click();
        Thread.sleep(2000);  //  Wait for the scroll to take effect

        // Click on add Content
        WebElement AddContentButton = driver.findElement(AddContentButtonBy);
        AddContentButton.click();
        Thread.sleep(2000);  //  Wait for the scroll to take effect

        // Wait for failure message
        String failureMessage = driver.findElement(SubmitMessageBy).getText();
        Assert.assertTrue(failureMessage.contains("please select at least one interaction"),
                "License was added successfully. Message: " + failureMessage);
    }


    @Test(priority = 7, description = "Date Range button")
    public void DateRange() throws IOException, InterruptedException {

        // Open Date Range filter
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(DateRangeButtonBy)).click();

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
        }}
    @Test(priority = 8, description = "Invalid data license")
    public void InvalidData() throws IOException, InterruptedException {
        // Click the Add License button
        Thread.sleep(3000);
        WebElement addLicenseButton = wait.until(ExpectedConditions.elementToBeClickable(AddLicenseButtonBy));
        addLicenseButton.click();

        // Leave the dropdown empty (intentionally not selecting an organization)
        Thread.sleep(3000);  // Optional: Wait for the dropdown to be clickable

        // Enter an old expiration date (this should trigger the expiration error)
        driver.findElement(ExpireDateBy).sendKeys("31-12-2000");

        // Submit the form
        WebElement CreateLicenseButton = driver.findElement(CreateLicenseButtonBy);
        Thread.sleep(2000);  // Optional: Wait for the button to be in view
        CreateLicenseButton.click();

        // Verify that the error for the missing organization appears
        WebElement organizationErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(licenseOrganizationErrorMessage));
        String organizationErrorText = organizationErrorMessage.getText();
        Assert.assertTrue(organizationErrorText.contains("Organization is required"),
                "License was added successfully without an organization. Message: " + organizationErrorText);

        // Verify that the error for the expired date appears
        WebElement dateErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(licenseDateErrorMessage));
        String dateErrorText = dateErrorMessage.getText();
        Assert.assertTrue(dateErrorText.contains("You need to select a future date for expiration! atleast 2 days from now"),
                "License was added successfully with an old date. Message: " + dateErrorText);

    }
}











