package TestCases;

import TestComponent.BaseTest;
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


    @Test(priority = 2, description = "Select License Status Dropdown")
    public void SelectLicenseStatusDropdown() throws IOException, InterruptedException {
        // 1. Open and apply Status filter
        Thread.sleep(2000); // Wait for the dropdown to be clickable
        WebElement SelectLicenseStatusDropdown = wait.until(ExpectedConditions.elementToBeClickable(SelectLicenseStatusDropdownyBy));
        SelectLicenseStatusDropdown.click();
        wait.until(ExpectedConditions.elementToBeClickable(LicenseStatusOption)).click();
        // 2. Verify selected Status
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

    @Test(priority = 3, description = "Add License button")
    public void AddLicense() throws IOException, InterruptedException {
        // Click the Add License button
        WebElement addLicenseButton = wait.until(ExpectedConditions.elementToBeClickable(AddLicenseButtonBy));
        addLicenseButton.click();

        // Fill in the form
        driver.findElement(LicenseNamefieldBy).sendKeys(Licenses_NewName);

        // Wait for the dropdown to be clickable and open it
        Thread.sleep(5000);
        WebElement organizationDropdown = driver.findElement(OrganizationDrobdownBy);
        organizationDropdown.click();
        Thread.sleep(2000);  // Optional: Wait for the dropdown options to load

        // Select the first option from the dropdown
        WebElement firstOrganizationOption = driver.findElement(FirstOrganizationOption);
        firstOrganizationOption.click();

        // Wait for the Expire Date field to be clickable
        Thread.sleep(4000);  // Optional: Wait for the Expire Date field to be clickable
        driver.findElement(ExpireDateBy).sendKeys("31-12-2027");

        // Select Efficient Operations option
        driver.findElement(EfficientOperationsRadioButton).click();

        // Select Digi checkbox
        driver.findElement(DigiCheckbox).click();

        // Submit the form
        WebElement continueButton = driver.findElement(ContinueButtonBy);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
        Thread.sleep(2000);  // Optional: Wait for the button to be in view
        continueButton.click();

        // Wait for the final confirmation button to be clickable
        WebElement finalConfirmButton = wait.until(ExpectedConditions.elementToBeClickable(FinalConfirmButtonBy));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", finalConfirmButton);
        Thread.sleep(2000);  // Optional: Wait for the button to be in view
        finalConfirmButton.click();

        // Wait for success message
        String successMessage = driver.findElement(SubmitMessageBy).getText();
        Assert.assertTrue(successMessage.contains("License created successfully"),
                "License was not added successfully. Message: " + successMessage);
    }


    @Test(priority = 4, description = "Date Range button")
    public void DateRange() throws IOException, InterruptedException {

        // Open Date Range filter
        wait.until(ExpectedConditions.elementToBeClickable(DateRangeButtonBy)).click();

        // Apply today's date
        wait.until(ExpectedConditions.elementToBeClickable(SetDateButtonBy)).click();

        // Wait until the first element appears (table is not empty)
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(CreatedAtColumnBy));

        // Short delay to let the page stabilize
        Thread.sleep(3000);

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
    @Test(priority = 5, description = "Invalid data license")
    public void InvalidData() throws IOException, InterruptedException {
        // Click the Add License button
        WebElement addLicenseButton = wait.until(ExpectedConditions.elementToBeClickable(AddLicenseButtonBy));
        addLicenseButton.click();

        // Leave the dropdown empty (intentionally not selecting an organization)
        Thread.sleep(3000);  // Optional: Wait for the dropdown to be clickable

        // Enter an old expiration date (this should trigger the expiration error)
        driver.findElement(ExpireDateBy).sendKeys("31-12-2000");

        // Submit the form
        WebElement continueButton = driver.findElement(ContinueButtonBy);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueButton);
        Thread.sleep(2000);  // Optional: Wait for the button to be in view
        continueButton.click();

        // Wait for the error messages to appear after form submission
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











