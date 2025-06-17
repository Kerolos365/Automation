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

public class ManageUsersPage extends BaseTest {
    private WebDriverWait wait;

    @BeforeMethod
    public void loginFirst() throws IOException {
        // Initialize WebDriverWait first
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Perform login
        driver.get(LOGIN_URL);
        login.loginNow(VALID_EMAIL, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlContains("/home"));
        manageUsers.NavigateToManageUsersPage();
    }

    @Test(priority = 2, description = "Check export button/ Special export button")
    public void CheckExport() throws IOException {
        // Check if the Export button is displayed
        boolean isExportButtonDisplayed = driver.findElement(ExportButtonBy).isDisplayed();
        boolean isSpecialExportButtonDisplayed = driver.findElement(SpecialExportButtonBy).isDisplayed();
        System.out.println("Is Export button displayed: " + isExportButtonDisplayed);
        System.out.println("Is Special Export button displayed: " + isSpecialExportButtonDisplayed);
        // Assert that the Export button is displayed
        Assert.assertTrue(isExportButtonDisplayed, "Export button is not displayed");
        Assert.assertTrue(isSpecialExportButtonDisplayed, "Special Export button is not displayed");
        // Click the Export button
        WebElement exportButton = driver.findElement(ExportButtonBy);
        WebElement specialExportButton = driver.findElement(SpecialExportButtonBy);
        // Wait for the Export button to be clickable and then click it
        wait.until(ExpectedConditions.elementToBeClickable(exportButton)).click();
        // Wait for the Special Export button to be clickable and then click it
        wait.until(ExpectedConditions.elementToBeClickable(specialExportButton)).click();
    }

    @Test(priority = 3, description = "Search Users button")
    public void SearchUsers() throws IOException, InterruptedException {
        // 1. Check if the Search button is displayed
        boolean isSearchButtonDisplayed = driver.findElement(SearchUsersBy).isDisplayed();
        System.out.println("Is Search button displayed: " + isSearchButtonDisplayed);
        Assert.assertTrue(isSearchButtonDisplayed, "Search button is not displayed");

        // 2. Click the Search button
        WebElement searchButton = driver.findElement(SearchUsersBy);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        Thread.sleep(2000); // Wait for the search input to be clickable

        // 3. Enter search text
        searchButton.sendKeys(Search_User);
        Thread.sleep(2000); // Wait for the search results to load

        // 4. Wait for results and verify
        List<WebElement> Users = driver.findElements(UsersColumnBy);

        boolean isFound = false;
        for (WebElement org : Users) {
            if (org.getText().trim().contains(Search_User)) {
                isFound = true;
                break;
            }
        }
        Assert.assertTrue(isFound, "Search user '" + Search_User + "' was not found in the users list");
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

    @Test(priority = 4, description = "Select User Verification Dropdown")
    public void SelectUserVerificationDropdown() throws IOException, InterruptedException {
        // 1. Open and apply industry filter
        Thread.sleep(2000); // Wait for the dropdown to be clickable
        WebElement SelectUserVerificationDropdown = wait.until(ExpectedConditions.elementToBeClickable(SelectUserVerificationDropdownyBy));
        SelectUserVerificationDropdown.click();
        wait.until(ExpectedConditions.elementToBeClickable(StatusOption)).click();
        // 2. Verify selected Status
        List<WebElement> UsersStatus = driver.findElements(StatusColumnBy);
        boolean isFound = false;
        for (int i = 0; i < UsersStatus.size(); i++) {
            // Re-fetch the element each loop to avoid stale reference
            WebElement org = driver.findElements(StatusColumnBy).get(i);
            if (org.getText().equalsIgnoreCase(status)) {
                isFound = true;
                break;
            }
        }
        // Assert that the selected status is found
        Assert.assertTrue(isFound, "Selected verification '" + status + "' was not found in the Users list");
    }


    @Test(priority = 5, description = "Select User Access Dropdown")
    public void SelectUserAccessDropdown() throws IOException, InterruptedException {
        Thread.sleep(2000);
        WebElement SelectUserAccessDropdown = wait.until(ExpectedConditions.elementToBeClickable(SelectUserAccessDropdownyBy));
        SelectUserAccessDropdown.click();

        wait.until(ExpectedConditions.elementToBeClickable(AccessOption)).click();
        Thread.sleep(1000); // Wait for changes

        List<WebElement> UsersAccess = driver.findElements(AccessColumnBy);
        boolean isFound = false;

        for (WebElement org : UsersAccess) {
            String accessText = org.getText().trim();
            System.out.println("Access column shows: " + accessText);
            if (accessText.equalsIgnoreCase(ExpectedAccessLabel)) {
                isFound = true;
                break;
            }
        }

        Assert.assertTrue(isFound, "Selected Access '" + ExpectedAccessLabel + "' was not found in the Access list");
    }


    @Test(priority = 6, description = "Select Role Dropdown")
    public void SelectRoleDropdown() throws IOException, InterruptedException {
        Thread.sleep(2000);
        WebElement SelectRoleDropdown = wait.until(ExpectedConditions.elementToBeClickable(RoleDropdownyBy));
        SelectRoleDropdown.click();

        wait.until(ExpectedConditions.elementToBeClickable(RoleOption)).click();
        Thread.sleep(1000); // Wait for changes

        List<WebElement> UsersRole = driver.findElements(RoleColumnBy);
        boolean isFound = false;

        for (WebElement org : UsersRole) {
            String roleText = org.getText().trim();
            System.out.println("Role column shows: " + roleText);
            if (roleText.equalsIgnoreCase(RoleName)) {
                isFound = true;
                break;
            }
        }

        Assert.assertTrue(isFound, "Selected Role '" + RoleName + "' was not found in the Role list");
    }
    @Test(priority = 1, description = "Add User")
    public void ClickAddUserButton() throws IOException, InterruptedException {
        // 1. Check if the Add User button is displayed
        boolean isAddUserButtonDisplayed = driver.findElement(addUserButtonBy).isDisplayed();
        System.out.println("Is Add User button displayed: " + isAddUserButtonDisplayed);
        Assert.assertTrue(isAddUserButtonDisplayed, "Add User button is not displayed");

        // 2. Click the Add User button
        WebElement addUserButton = driver.findElement(addUserButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(addUserButton)).click();
        Thread.sleep(2000);
        // 3. Verify that the system redirects to the Add User page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/register"),
                "Expected to contain '/register' but got: " + currentUrl);
        // 4. Wait for the page to load
        Thread.sleep(2000);
          // 5. Fill in the Add User form
        // 5.1 Fill in the email field
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(RegisterEmailBoxBy));
        emailField.click();
        // Initialize Faker
        Faker faker = new Faker();
        // Generate a realistic fake email
        String fakeEmail = faker.internet().emailAddress();
        emailField.sendKeys(fakeEmail);
        System.out.println("Fake email entered: " + fakeEmail);
        Thread.sleep(2000); // Wait for the email field to be filled
        // 5.2 Fill in the username field
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(RegisterNameBoxBy));
        usernameField.click();
        // Generate a realistic fake username
        String fakeUsername = faker.name().firstName();
        usernameField.sendKeys(fakeUsername);
        System.out.println("Fake username entered: " + fakeUsername);
        Thread.sleep(2000); // Wait for the username field to be filled
        // 5.3 Fill in the password field
        WebElement PassField = wait.until(ExpectedConditions.elementToBeClickable(RegisterPassBoxBy));
        PassField.click();
        PassField.sendKeys(Pass);
        Thread.sleep(2000); // Wait for the password field to be filled
        // 5.4 Fill in the Confirm password field
        WebElement ConfirmPassField = wait.until(ExpectedConditions.elementToBeClickable(RegisterConfirmPassBoxBy));
        ConfirmPassField.click();
        ConfirmPassField.sendKeys(Pass);
        Thread.sleep(2000); // Wait for the Confirm password field to be filled
        // 5.5 Fill in the Job title field
        WebElement JobTitleField = wait.until(ExpectedConditions.elementToBeClickable(RegisterTitleBoxBy));
        JobTitleField.click();
        JobTitleField.sendKeys(Title);
        Thread.sleep(2000); // Wait for the Job title field to be filled
        // 5.6 Click the Next button
        WebElement NextButton = wait.until(ExpectedConditions.elementToBeClickable(RegisterNextButtonBy));
        NextButton.click();
        Thread.sleep(2000); // Wait for the Next button to be clicked
        // 6. Verify that the system redirects to the next step
        String PageHeader = driver.findElement(By.xpath("//h5[text()='Personal Information']")).getText();
        Assert.assertTrue(PageHeader.contains("Personal Information"),
                "Expected to contain 'Personal Information' but got: " + PageHeader);
        Thread.sleep(2000); // Wait for the page to load
        // 7. Fill in the Personal Information form
        // 7.1 Fill in the first name field
        WebElement firstNameField = wait.until(ExpectedConditions.elementToBeClickable(RegisterfirstNameInputBy));
        firstNameField.click();
        // Generate a realistic fake first name
        String fakeFirstname = faker.name().firstName();
        firstNameField.sendKeys(fakeFirstname);
        System.out.println("Fake Firstname entered: " + fakeFirstname);
        Thread.sleep(2000); // Wait for the Firstname field to be filled
        // 7.2 Fill in the last name field
        WebElement lastNameField = wait.until(ExpectedConditions.elementToBeClickable(RegisterlastNameInputBy));
        lastNameField.click();
        // Generate a realistic fake last name
        String fakeLastname = faker.name().lastName();
        lastNameField.sendKeys(fakeLastname);
        System.out.println("Fake Lastname entered: " + fakeLastname);
        Thread.sleep(2000); // Wait for the Lastname field to be filled
        // 7.3 Fill in the phone number field
        WebElement phoneField = wait.until(ExpectedConditions.elementToBeClickable(RegisterPhoneInputBy));
        phoneField.click();
        phoneField.sendKeys(Phone);
        Thread.sleep(2000); // Wait for the Phone field to be filled
        // 7.4 Fill in the address field
        WebElement addressField = wait.until(ExpectedConditions.elementToBeClickable(RegisterAddressInputBy));
        addressField.click();
        addressField.sendKeys(Address);
        Thread.sleep(2000); // Wait for the Address field to be filled
        // 7.5 Click the Next button
        WebElement NextButton2 = wait.until(ExpectedConditions.elementToBeClickable(NextButton2By));
        NextButton2.click();
        // 7.6 Verify that the system redirects to the next step
        String PageHeader2 = driver.findElement(By.xpath("//h5[text()='Select Role']")).getText();
        Assert.assertTrue(PageHeader2.contains("Select Role"),
                "Expected to contain 'Select Role' but got: " + PageHeader2);
        Thread.sleep(2000); // Wait for the page to load
        // 8. Click the Submit button
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonBy));
        submitButton.click();
        Thread.sleep(2000); // Wait for the Submit button to be clicked
        // 9. Verify that the system redirects to the Users page
        String PageHeader3 = driver.findElement(By.xpath("//p[text()='Users list']")).getText();
        Assert.assertTrue(PageHeader3.contains("Users list"),
                "Expected to contain 'Users list' but got: " + PageHeader3);
        Thread.sleep(2000); // Wait for the page to load
        boolean isFound = false;
        while (true) {
            List<WebElement> users = driver.findElements(UsersColumnBy);

            for (WebElement user : users) {
                if (user.getText().trim().contains(fakeUsername)) {
                    isFound = true;
                    break;
                }
            }
            if (isFound) {
                break;
            }
            // Check if the next button is present and clickable
            List<WebElement> nextButtons = driver.findElements(By.xpath("//button[@title='Go to next page']//*[name()='svg']"));

            if (nextButtons.isEmpty() || !nextButtons.get(0).isDisplayed() || !nextButtons.get(0).isEnabled()) {
                break; // No more pages to check
            }
            // Click next and wait for page to load
            Thread.sleep(2000);
            nextButtons.get(0).click();
            Thread.sleep(2000); // adjust wait as needed
        }
        // Final assertion
        Assert.assertTrue(isFound, "New user '" + fakeUsername + "' was not found in the users list across all pages");
        System.out.println("New user '" + fakeUsername + "' was found in the users list");
    }
    @Test(priority = 8, description = "invalid data while adding a user ")
    public void ClickAddUserButtonInvalidData() throws IOException, InterruptedException {
        // 1. Check if the Add User button is displayed
        boolean isAddUserButtonDisplayed = driver.findElement(addUserButtonBy).isDisplayed();
        Assert.assertTrue(isAddUserButtonDisplayed, "Add User button is not displayed");

        // 2. Click the Add User button
        WebElement addUserButton = driver.findElement(addUserButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(addUserButton)).click();
        Thread.sleep(2000);
        // 3. Verify that the system redirects to the Add User page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/register"),
                "Expected to contain '/register' but got: " + currentUrl);
        // 4. Wait for the page to load
        Thread.sleep(2000);
        // 5. Fill in the Add User form
        // 5.1 Fill in the email field
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(RegisterEmailBoxBy));
        emailField.click();
        emailField.sendKeys("kero");
        Thread.sleep(2000); // Wait for the email field to be filled

        // 5.2 Fill in the username field
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(RegisterNameBoxBy));
        usernameField.click();
        usernameField.sendKeys("");
        Thread.sleep(2000); // Wait for the username field to be filled
        // 5.3 Fill in the password field
        WebElement PassField = wait.until(ExpectedConditions.elementToBeClickable(RegisterPassBoxBy));
        PassField.click();
        PassField.sendKeys("Test");
        Thread.sleep(2000); // Wait for the password field to be filled
        // 5.4 Fill in the Confirm password field
        WebElement ConfirmPassField = wait.until(ExpectedConditions.elementToBeClickable(RegisterConfirmPassBoxBy));
        ConfirmPassField.click();
        ConfirmPassField.sendKeys(Pass);
        Thread.sleep(2000); // Wait for the Confirm password field to be filled
          // 5.5 Click the Next button
        WebElement NextButton = wait.until(ExpectedConditions.elementToBeClickable(RegisterNextButtonBy));
        NextButton.click();
        Thread.sleep(2000); // Wait for the Next button to be clicked
        // 6. Verify that the system does not redirect to the next step
        String PageHeader = driver.findElement(By.xpath("//h5[text()='Account Information']")).getText();
        Assert.assertTrue(PageHeader.contains("Account Information"),
                "Expected to contain 'Account Information' but got: " + PageHeader);
        // 7.Error messages
        // 7.1 Verify that the email validation message is displayed
        WebElement emailValidationMsgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailValidationMsg));
        Assert.assertTrue(emailValidationMsgElement.isDisplayed(), "Email validation message is not displayed");
        // 7.2 Verify that the username validation message is displayed
        WebElement usernameValidationMsgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameValidationMsg));
        Assert.assertTrue(usernameValidationMsgElement.isDisplayed(), "Username validation message is not displayed");
        // 7.3 Verify that the password validation message is displayed
        WebElement passwordValidationMsgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordValidationMsg));
        Assert.assertTrue(passwordValidationMsgElement.isDisplayed(), "Password validation message is not displayed");
        // 7.4 Verify that the Confirm password match error message is displayed
        WebElement confirmPasswordMatchErrorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordMatchError));
        Assert.assertTrue(confirmPasswordMatchErrorElement.isDisplayed(), "Confirm password match error message is not displayed");
    }

    @Test(priority = 9, description = "Edit Avc Users")
    public void EditAVCUsers() throws IOException, InterruptedException {
        // 1. Check if the Action button is displayed
        boolean isUserActionButtonDisplayed = driver.findElement(UserActionButtonBy).isDisplayed();
        Assert.assertTrue(isUserActionButtonDisplayed, "Action button is not displayed");

        // 2. Click the User Action button
        WebElement UserActionButton = driver.findElement(UserActionButtonBy);
        Thread.sleep(2000); // Wait for the Action button to be clicked
        wait.until(ExpectedConditions.elementToBeClickable(UserActionButton)).click();
        UserActionButton.click();
        Thread.sleep(2000); // Wait for the Action button to be clicked

        // 3. Click the Edit button
        WebElement UserEditButton = driver.findElement(UserEditButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(UserEditButton)).click();
        Thread.sleep(2000); // Wait for the Edit button to be clicked

        // 4. Verify that the system redirects to the Edit User page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/edit"),
                "Expected to contain '/edit' but got: " + currentUrl);

        // 5. Wait for the page to load
        Thread.sleep(2000);

        // 6. Change the Address for the user
        WebElement UserAddressField = driver.findElement(UserAddressFieldBy);
        Faker faker = new Faker();
        String fakeAddress = faker.address().fullAddress();
        UserAddressField.sendKeys(fakeAddress);
        System.out.println("Fake Address entered: " + fakeAddress);
        Thread.sleep(7000); // Wait for the Address field to be filled

        // Scroll the Update button into view to ensure it's clickable
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");

        // 7. Click the Update button
        Thread.sleep(3000); // Wait for the page to load
        WebElement UserUpdateButton = driver.findElement(UserUpdateButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(UserUpdateButton)).click();
        Thread.sleep(2000); // Wait for the Update button to be clicked

        // 8. Verify that the system not redirects to the manage User page and show  Not Authorized
       WebElement UserUpdateMessage = driver.findElement(UserUpdateMessageBy);
        String messageText = UserUpdateMessage.getText();
        Assert.assertTrue(messageText.contains("Not Authorized"),
                "Expected to contain 'Not Authorized' but got: " + messageText);

        Thread.sleep(2000); // Wait for the page to load
    }
    @Test(priority = 10, description = "edit nothing Users")
    public void EditNothingCUsers() throws IOException, InterruptedException {
        // 1. Check if the Action button is displayed
        boolean isUserActionButtonDisplayed = driver.findElement(UserActionButtonBy).isDisplayed();
        Assert.assertTrue(isUserActionButtonDisplayed, "Action button is not displayed");

        // 2. Click the User Action button
        WebElement UserActionButton = driver.findElement(UserActionButtonBy);
        Thread.sleep(2000); // Wait for the Action button to be clicked
        wait.until(ExpectedConditions.elementToBeClickable(UserActionButton)).click();
        UserActionButton.click();
        Thread.sleep(2000); // Wait for the Action button to be clicked

        // 3. Click the Edit button
        WebElement UserEditButton = driver.findElement(UserEditButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(UserEditButton)).click();
        Thread.sleep(2000); // Wait for the Edit button to be clicked

        // 4. Verify that the system redirects to the Edit User page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/edit"),
                "Expected to contain '/edit' but got: " + currentUrl);

        // 5. Wait for the page to load
        Thread.sleep(2000);

        // 6. Click on update button without changing anything
        WebElement UserUpdateButton = driver.findElement(UserUpdateButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(UserUpdateButton)).click();
        String currentUrl2 = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl2.contains("/edit"),
                "Expected to contain '/edit' but got: " + currentUrl2);
    }
    @Test(priority = 11, description = "ViewU Users")
    public void ViewUsers() throws IOException, InterruptedException {
        // 1. Check if the Action button is displayed
        boolean isUserActionButtonDisplayed = driver.findElement(UserActionButtonBy).isDisplayed();
        Assert.assertTrue(isUserActionButtonDisplayed, "Action button is not displayed");

        // 2. Click the User Action button
        WebElement UserActionButton = driver.findElement(UserActionButtonBy);
        Thread.sleep(2000); // Wait for the Action button to be clicked
        wait.until(ExpectedConditions.elementToBeClickable(UserActionButton)).click();
        UserActionButton.click();
        Thread.sleep(2000); // Wait for the Action button to be clicked
        // 3. Click the View button
        WebElement UserViewButton = driver.findElement(UserViewButtonBy);
        wait.until(ExpectedConditions.elementToBeClickable(UserViewButton)).click();
        Thread.sleep(2000); // Wait for the View button to be clicked
        // 4. Verify that the system redirects to the View User page
        WebElement UserViewButtonText = driver.findElement(By.xpath("//p[text()='Session History']"));
        String messageText = UserViewButtonText.getText();
        Assert.assertTrue(messageText.contains("Session History"),
                "Expected to contain 'Session History' but got: " + messageText);


    }
}




