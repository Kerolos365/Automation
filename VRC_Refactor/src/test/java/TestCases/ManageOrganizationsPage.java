package TestCases;

import TestComponent.BaseTest;
import net.datafaker.Faker;
import org.openqa.selenium.*;
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

public class ManageOrganizationsPage extends BaseTest {
    public WebDriverWait wait;


    @BeforeMethod
    public void loginFirst() throws IOException {
        // Initialize WebDriverWait first
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Perform login
        driver.get(LOGIN_URL);
        login.loginNow(VALID_EMAIL, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlContains("/home"));
        manageOrganizations.NavigateToManageorganizationsPage();
    }

    @Test(priority = 1, description = "Check export button")
    public void CheckExport() throws IOException {
        // Check if the Export button is displayed
        boolean isExportButtonDisplayed = driver.findElement(ExportButtonBy).isDisplayed();
        System.out.println("Is Export button displayed: " + isExportButtonDisplayed);
        Assert.assertTrue(isExportButtonDisplayed, "Export button is not displayed");
        // Click the Export button
        WebElement exportButton = driver.findElement(ExportButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(exportButton)).click();
    }

    @Test(priority = 2, description = "Search Organizations button")
    public void SearchOrganizations() throws IOException, InterruptedException {
        // 1. Check if the Search button is displayed
        boolean isSearchButtonDisplayed = driver.findElement(SearchOrganizationsBy).isDisplayed();
        System.out.println("Is Search button displayed: " + isSearchButtonDisplayed);
        Assert.assertTrue(isSearchButtonDisplayed, "Search button is not displayed");

        // 2. Click the Search button
        WebElement searchButton = driver.findElement(SearchOrganizationsBy);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        // 3. Enter search text
        searchButton.sendKeys(Search_Item);

        // 4. Wait for results and verify
        List<WebElement> organizations = driver.findElements(NameColumnBy); // Using your predefined locator

        boolean isFound = false;
        for (WebElement org : organizations) {
            if (org.getText().equalsIgnoreCase(Search_Item)) {
                isFound = true;
                break;
            }
        }
        Assert.assertTrue(isFound, "Search item '" + Search_Item + "' was not found in the organizations list");
    }

   @Test(priority = 7, description = "Date Range button")
    public void DateRange() throws IOException, InterruptedException {

        // Open Date Range filter
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
        }
    }


    @Test(priority = 4, description = "Industry Dropdown")
    public void IndustryDropdown() throws IOException {
        // 1. Open and apply industry filter
        WebElement industryDropdown = wait.until(ExpectedConditions.elementToBeClickable(SelectIndustryBy));
        industryDropdown.click();
        wait.until(ExpectedConditions.elementToBeClickable(IndustryOption)).click();
        // 2. Verify selected industry
        List<WebElement> organizationsIndustry = driver.findElements(IndustryColumnBy);
        boolean isFound = false;
        for (int i = 0; i < organizationsIndustry.size(); i++) {
            // Re-fetch the element each loop to avoid stale reference
            WebElement org = driver.findElements(IndustryColumnBy).get(i);
            if (org.getText().equalsIgnoreCase(Industry)) {
                isFound = true;
                break;
            }
        }
        // Assert that the selected industry is found in the organizations list
        Assert.assertTrue(isFound, "Selected industry '" + Industry + "' was not found in the organizations list");
    }

    // this test case is good but the issue that there is a bug here that whatever country you choose it will always get you nothing
    @Test(priority = 5, description = "Country Dropdown")
    public void CountryDropdown() throws IOException, InterruptedException {
        // 1. Open and apply country filter
        wait.until(ExpectedConditions.visibilityOfElementLocated(SelectCountryBy));
        WebElement countryDropdown = wait.until(ExpectedConditions.elementToBeClickable(SelectCountryBy));
        countryDropdown.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(CountryOption));
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(CountryOption)).click();

        // 2. Verify selected country
        List<WebElement> organizationsCountry = driver.findElements(CountryColumnBy);
        boolean isFound = false;

        for (int i = 0; i < organizationsCountry.size(); i++) {
            WebElement org = driver.findElements(CountryColumnBy).get(i);
            if (org.getText().equalsIgnoreCase("CA")) {
                isFound = true;
                break;
            }
        }

        Assert.assertTrue(isFound, "Selected Country '" + "CA" + "' was not found in the organizations list");
    }

    @Test(priority = 6, description = "Suspended Column")
    public void SuspendedColumn() throws IOException, InterruptedException {
        // 1. Check if the Suspended column is displayed
        boolean isSuspendedColumnDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(SuspendedColumnBy)).isDisplayed();
        System.out.println("Is Suspended column displayed: " + isSuspendedColumnDisplayed);
        Assert.assertTrue(isSuspendedColumnDisplayed, "Suspended column is not displayed");

        // 2. Click the Action button
        WebElement actionButton = wait.until(ExpectedConditions.elementToBeClickable(ActionButtonBy));
        actionButton.click();

        // 3. Wait for the Suspend button to be clickable and then click it
        WebElement suspendButton = wait.until(ExpectedConditions.elementToBeClickable(SusbendedButtonBy));
        suspendButton.click();

        // 4. Wait for the status to update to 'Suspended' (organization is suspended)
        Thread.sleep(2000); // You can replace this with a better wait, but for now, we use sleep for UI to update.
        List<WebElement> statusElements = driver.findElements(SuspendedColumnBy);
        boolean isSuspended = false;

        for (WebElement element : statusElements) {
            if (element.getText().equalsIgnoreCase("Suspended")) {
                isSuspended = true;
                break;
            }
        }

        // Assert that the status has changed to Suspended
        Assert.assertTrue(isSuspended, "Organization was not suspended");

        // 5. Activate the organization
        actionButton.click();

        // 6. Wait for the Activate button to be clickable and then click it
        WebElement activateButton = wait.until(ExpectedConditions.elementToBeClickable(ActivateButtonBy));
        activateButton.click();

        // 7. Wait for the status to update to 'Active' (organization is active)
        Thread.sleep(2000); // Again, wait for UI to update
        List<WebElement> updatedStatusElements = driver.findElements(SuspendedColumnBy);
        boolean isActivated = false;

        for (WebElement element : updatedStatusElements) {
            if (element.getText().equalsIgnoreCase("Active")) {
                isActivated = true;
                break;
            }
        }

        // Assert that the status has changed to Active
        Assert.assertTrue(isActivated, "Organization was not activated after clicking Activate.");
    }

    @Test(priority = 3, description = "Add Organization")
    public void AddOrganization() throws IOException, InterruptedException {
        // Click on Add Organization Button
        WebElement AddOrganization = wait.until(ExpectedConditions.elementToBeClickable(AddOrganizationButtonBy));
        AddOrganization.click();

        // Verify Directing to add Organization Page
        Thread.sleep(2000);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/create-organization"),
                "Expected to contain '/create-organization' but got: " + currentUrl);

        // Enter Name for the organization
        Faker faker = new Faker();
        WebElement NameTextBox = driver.findElement(OrganizationNameInputBy);
        String fakeUsername = faker.name().firstName();
        NameTextBox.clear();
        NameTextBox.sendKeys(fakeUsername);
        System.out.println("Fake username entered: " + fakeUsername);
        Thread.sleep(2000); // Wait for the username field to be filled


        // Choose Industry
        wait.until(ExpectedConditions.visibilityOfElementLocated(OrganizationIndustryDropdownBy));
        WebElement IndustryDropdown = wait.until(ExpectedConditions.elementToBeClickable(OrganizationIndustryDropdownBy));
        IndustryDropdown.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(OrganizationIndustryInputOption));
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(OrganizationIndustryInputOption)).click();

        // Choose Country
        wait.until(ExpectedConditions.visibilityOfElementLocated(OrganizationCountryDropdownBy));
        WebElement countryDropdown = wait.until(ExpectedConditions.elementToBeClickable(OrganizationCountryDropdownBy));
        countryDropdown.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(OrganizationCountryOptionBy));
        Thread.sleep(2000);
        wait.until(ExpectedConditions.elementToBeClickable(OrganizationCountryOptionBy)).click();

        // 1. Upload the file directly without clicking the drop zone
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("input[type='file']")));

        // 2. Upload the file directly to the input element
        String filePath = "C:\\Users\\Genesis QA\\Documents\\logo\\genesis_egypt_logo.jfif";
        fileInput.sendKeys(filePath);

        // 3. Wait for upload confirmation
        Thread.sleep(3000); // 3 seconds delay


        // 4. Force remove focus from the file input to prevent reopening
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector('input[type=\"file\"]').blur();");
        Thread.sleep(2000);

        // 5. Click confirm button
        WebElement confirmlogoButton = driver.findElement(ConfirmLogoButtonBy);
        confirmlogoButton.click();
        Thread.sleep(3000);

        // 6. Click confirm button
        Thread.sleep(2000);
        WebElement confirmButton = driver.findElement(ConfirmButtonBy);
        confirmButton.click();
        Thread.sleep(3000); // Extra wait for data refresh

        // 7. Verify if organization is created
        WebElement searchButton = driver.findElement(SearchOrganizationsBy);
        searchButton.click();
        Thread.sleep(2000);
        searchButton.sendKeys(fakeUsername);

        // Wait specifically for search results
        Thread.sleep(3000); // Allow time for search to complete
        // More robust search verification
        List<WebElement> organizations = driver.findElements(NameColumnBy);
        boolean isFound = false;

        for (WebElement org : organizations) {
            if (org.getText().trim().equalsIgnoreCase(fakeUsername)) {
                isFound = true;
                System.out.println("Found organization: " + org.getText());
                break;
            }
        }
        Thread.sleep(2000);
        Assert.assertTrue(isFound, "Search item '" + fakeUsername + "' was not found in the organizations list");


    }

    @Test(priority = 8, description = "Invalid Data while Creating New Organization Organization")
    public void AddInvalidDataForOrganization() throws IOException, InterruptedException {
        // Click on Add Organization Button
        WebElement AddOrganization = wait.until(ExpectedConditions.elementToBeClickable(AddOrganizationButtonBy));
        AddOrganization.click();

        // Verify Directing to add Organization Page
        Thread.sleep(2000);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/create-organization"),
                "Expected to contain '/create-organization' but got: " + currentUrl);

        // Enter NO Name for the organization
        WebElement NameTextBox = driver.findElement(OrganizationNameInputBy);
        wait.until(ExpectedConditions.elementToBeClickable(NameTextBox)).click();
        NameTextBox.clear();
        NameTextBox.sendKeys("");
        // Click confirm button
        Thread.sleep(2000);
        WebElement confirmButton = driver.findElement(ConfirmButtonBy);
        confirmButton.click();
        //  Verify error message
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[text()='Name must be at least 2 characters.']")));
        String errorText = errorMessage.getText();
        Assert.assertEquals(errorText, "Name must be at least 2 characters.",
                "Error message does not match expected");
    }


}







