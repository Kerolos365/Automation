package TestCases;

import TestComponent.BaseTest;
import net.datafaker.Faker;
import org.apache.poi.ss.formula.functions.T;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SessionHistoryPage extends BaseTest {
    public WebDriverWait wait;
    private final By StatusColumnBy = By.xpath("//div[@role='rowgroup']/div[@role='row']/div[@data-field='session status']");

    @BeforeMethod
    public void loginFirst() throws IOException {
        // Initialize WebDriverWait first
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Perform login
        driver.get(LOGIN_URL);
        login.loginNow(VALID_EMAIL, VALID_PASSWORD);
        wait.until(ExpectedConditions.urlContains("/home"));
        sessionHistory.NavigateToSessionHistoryPage();
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

    @Test(priority = 2, description = "Search Session button")
    public void SearchSessions() throws IOException, InterruptedException {
        // 1. Check if the Search button is displayed
        Thread.sleep(4000); // Wait for the search button to be clickable
        boolean isSearchButtonDisplayed = driver.findElement(SearchSessionBy).isDisplayed();
        System.out.println("Is Search button displayed: " + isSearchButtonDisplayed);
        Assert.assertTrue(isSearchButtonDisplayed, "Search button is not displayed");

        // 2. Click the Search button
        WebElement searchButton = driver.findElement(SearchSessionBy);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        // 3. Enter search text
        searchButton.sendKeys("table");

        // 4. Wait for results and verify
        List<WebElement> Session = driver.findElements(SessionNameColumnBy); // Using your predefined locator

        boolean isFound = false;
        for (WebElement org : Session) {
            if (org.getText().equalsIgnoreCase("table")) {
                isFound = true;
                break;
            }
        }
        Assert.assertTrue(isFound, "Search session '" + "table" + "' was not found in the session list");
    }

    @Test(priority = 3, description = "sessionType Dropdown")
    public void SelectSessionTypeDropdown() throws IOException, InterruptedException {
        // 1. Open and apply industry filter
        Thread.sleep(2000); // Wait for the dropdown to be clickable
        WebElement SelectVersionTypeDropdown = wait.until(ExpectedConditions.elementToBeClickable(Versions_Type_DropdownBy));
        SelectVersionTypeDropdown.click();
        wait.until(ExpectedConditions.elementToBeClickable(VersionTypeOption)).click();

        // 2. Verify selected Type
        WebElement horizontalScroll = driver.findElement(By.className("MuiDataGrid-scrollbar--horizontal"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth;", horizontalScroll);
        Thread.sleep(3000); //  Wait for the scroll to complete

        List<WebElement> SessionType = driver.findElements(SessionTypeColumnBy);
        boolean isFound = false;

        for (WebElement org : SessionType) {
            if (org.getText().trim().equalsIgnoreCase(Version_Type.trim())) {
                isFound = true;
                break;
            }
        }

        // assertion
        Assert.assertTrue(isFound, "Selected Session Type '" + Version_Type + "' was not found in the version list");
    }
    @Test(priority = 4, description = "Check Resend PIN Code button")
    public void CheckResendPINCode() throws IOException, InterruptedException {
        //  Click the action button
        WebElement scrollDiv = driver.findElement(By.xpath("//div[contains(@class, 'MuiDataGrid-scrollbar--horizontal')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth;", scrollDiv);
        WebElement actionButton2 = driver.findElement(SessionActionButtonBy);
        actionButton2.click();
        Thread.sleep(1000);
        // Click the resend PINCode button
        WebElement ResendPinCodeButton = driver.findElement(ResendPINCodeBy);
        wait.until(ExpectedConditions.elementToBeClickable(ResendPinCodeButton)).click();
        Thread.sleep(2000); // Wait for the Resend PinCode button to be clickable
        // Verify success message
        WebElement successMessage = driver.findElement(confMessagenBy);
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        String messageText = successMessage.getText();
        Assert.assertTrue(messageText.contains("Sent PIN Code successfully"),
                "Success message not displayed or incorrect: " + messageText);
    }

    @Test(priority = 5, description = "Reassign User and Deactivate it")
    public void ReassignUser() throws IOException, InterruptedException {
        // Scroll right to make the Status and Action columns visible
        WebElement hScroll = driver.findElement(By.className("MuiDataGrid-scrollbar--horizontal"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth;", hScroll);
        Thread.sleep(3000);

        // Find the first row with any status
        List<WebElement> SessionStatus = driver.findElements(StatusColumnBy);
        int targetRow = -1;
        String statusText = "";
        for (int i = 0; i < SessionStatus.size(); i++) {
            statusText = SessionStatus.get(i).getText().trim();
            if (!statusText.isEmpty()) {
                targetRow = i;
                break;
            }
        }

        // Fail test if no valid status found
        Assert.assertTrue(targetRow != -1, "No session found with a status");

        // Scroll again just to ensure action button is visible
        WebElement scrollDiv = driver.findElement(By.className("MuiDataGrid-scrollbar--horizontal"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth;", scrollDiv);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click the corresponding Action button for the target row
        List<WebElement> actionButtons = driver.findElements(SessionActionButtonBy);
        WebElement actionButton = actionButtons.get(targetRow);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".MuiBackdrop-root")));
        wait.until(ExpectedConditions.elementToBeClickable(actionButton)).click();
        Thread.sleep(1000);

        if (statusText.equalsIgnoreCase("Not started")) {
            // Deactivate first
            WebElement DeactivateButton = driver.findElement(DeactivateButtonBy);
            wait.until(ExpectedConditions.elementToBeClickable(DeactivateButton)).click();
            Thread.sleep(2000);
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(confMessagenBy));
            String messageText = successMessage.getText().trim();
            Assert.assertTrue(messageText.contains("Deactivated user session successfully"),
                    "Success message not displayed or incorrect: " + messageText);

            // Refresh and Reassign
            driver.navigate().refresh();
            Thread.sleep(2000);

            // Scroll and click action again
            WebElement scrollDiv2 = driver.findElement(By.className("MuiDataGrid-scrollbar--horizontal"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth;", scrollDiv2);
            List<WebElement> actionButtonsAfterRefresh = driver.findElements(SessionActionButtonBy);
            WebElement actionButton2 = actionButtonsAfterRefresh.get(targetRow);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".MuiBackdrop-root")));
            wait.until(ExpectedConditions.elementToBeClickable(actionButton2)).click();
            Thread.sleep(1000);

            WebElement ReassignButton = driver.findElement(ReassignButtonBy);
            wait.until(ExpectedConditions.elementToBeClickable(ReassignButton)).click();
            Thread.sleep(2000);

            WebElement successMessage2 = wait.until(ExpectedConditions.visibilityOfElementLocated(confMessagenBy));
            String messageText2 = successMessage2.getText().trim();

            if (messageText2.contains("Re-assigned user to session successfully")) {
                System.out.println("Reassign succeeded.");
            } else if (messageText2.contains("The user is already in an active session.")) {
                System.out.println("User already active; skipping deactivate.");
                return;
            } else {
                Assert.fail("Unexpected reassign message: " + messageText2);
            }

            // Refresh and deactivate again
            driver.navigate().refresh();
            Thread.sleep(2000);

            WebElement scrollDiv3 = driver.findElement(By.className("MuiDataGrid-scrollbar--horizontal"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth;", scrollDiv3);
            List<WebElement> actionButtonsAfterReassign = driver.findElements(SessionActionButtonBy);
            WebElement actionButton3 = actionButtonsAfterReassign.get(targetRow);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".MuiBackdrop-root")));
            wait.until(ExpectedConditions.elementToBeClickable(actionButton3)).click();
            Thread.sleep(1000);

            WebElement DeactivateButton2 = driver.findElement(DeactivateButtonBy);
            wait.until(ExpectedConditions.elementToBeClickable(DeactivateButton2)).click();
            Thread.sleep(2000);

            WebElement successMessage3 = driver.findElement(confMessagenBy);
            wait.until(ExpectedConditions.visibilityOf(successMessage3));
            String messageText3 = successMessage3.getText();
            Assert.assertTrue(messageText3.contains("Deactivated user session successfully"),
                    "Success message not displayed or incorrect: " + messageText3);
        } else {
            // Reassign then deactivate
            WebElement ReassignButton = driver.findElement(ReassignButtonBy);
            wait.until(ExpectedConditions.elementToBeClickable(ReassignButton)).click();
            Thread.sleep(2000);

            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(confMessagenBy));
            String messageText = successMessage.getText().trim();

            if (messageText.contains("Re-assigned user to session successfully")) {
                System.out.println("Reassign succeeded.");
            } else if (messageText.contains("The user is already in an active session.")) {
                System.out.println("User already active; skipping deactivate.");
                return;
            } else {
                Assert.fail("Unexpected reassign message: " + messageText);
            }

            // Refresh and deactivate
            driver.navigate().refresh();
            Thread.sleep(2000);

            WebElement scrollDiv2 = driver.findElement(By.className("MuiDataGrid-scrollbar--horizontal"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth;", scrollDiv2);
            List<WebElement> actionButtonsAfterReassign = driver.findElements(SessionActionButtonBy);
            WebElement actionButton2 = actionButtonsAfterReassign.get(targetRow);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".MuiBackdrop-root")));
            wait.until(ExpectedConditions.elementToBeClickable(actionButton2)).click();
            Thread.sleep(1000);

            WebElement DeactivateButton = driver.findElement(DeactivateButtonBy);
            wait.until(ExpectedConditions.elementToBeClickable(DeactivateButton)).click();
            Thread.sleep(2000);

            WebElement successMessage2 = driver.findElement(confMessagenBy);
            wait.until(ExpectedConditions.visibilityOf(successMessage2));
            String messageText2 = successMessage2.getText();
            Assert.assertTrue(messageText2.contains("Deactivated user session successfully"),
                    "Success message not displayed or incorrect: " + messageText2);
        }
    }





}