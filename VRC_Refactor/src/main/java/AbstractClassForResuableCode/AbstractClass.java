package AbstractClassForResuableCode;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AbstractClass  {
    protected WebDriver driver;
    public AbstractClass(WebDriver driver) {
        this.driver = driver;
    }
    public void waitingForElementToBeVisible(By findby) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findby));
    }
    public void waitingForElementToBeInvisible(By findby) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(findby));
    }
    public void waitingWebElement(WebElement findby) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(findby));
    }
    public void waitingForElementToBeClickable(By findby) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(findby));
    }

    public void waitingForElementToBeSelected(By findby) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeSelected(findby));
    }

    public void WaitingForHomePage() {
        By cartBy = By.cssSelector("a[class='MuiButtonBase-root MuiListItemButton-root MuiListItemButton-gutters MuiListItemButton-root MuiListItemButton-gutters active css-1lkpnu'] div[class='MuiBox-root css-164kvcj']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //  wait.until(ExpectedConditions.elementToBeClickable(HomeBy)).click();
    }

    public void WaitingForMangeOrganizationsPage() {
        By  OrderPage = By.cssSelector("a[class='MuiButtonBase-root MuiListItemButton-root MuiListItemButton-gutters MuiListItemButton-root MuiListItemButton-gutters active css-1lkpnu'] p[class='MuiTypography-root MuiTypography-body1 MuiTypography-noWrap css-jjguvf']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait.until(ExpectedConditions.elementToBeClickable(MangeOrgBy)).click();
    }
    public void WaitingForMangeUsersPage() {
        By  OrderPage = By.cssSelector("body > div:nth-child(27) > div:nth-child(3) > div:nth-child(3) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(3) > a:nth-child(1) > div:nth-child(2) > p:nth-child(1)");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait.until(ExpectedConditions.elementToBeClickable(MangeUsersBy)).click();
    }
    public void WaitingForRolesPage() {
        By cartBy = By.cssSelector("body > div:nth-child(27) > div:nth-child(3) > div:nth-child(3) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(4) > a:nth-child(1) > div:nth-child(2)");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait.until(ExpectedConditions.elementToBeClickable(RolesBy)).click();
    }
    public void WaitingForCoursesPage() {
        By cartBy = By.cssSelector("body > div:nth-child(27) > div:nth-child(3) > div:nth-child(3) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(5) > a:nth-child(1) > div:nth-child(2)");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait.until(ExpectedConditions.elementToBeClickable(CoursesBy)).click();
    }
    public void WaitingForVersionConfigurationsPage() {
        By cartBy = By.cssSelector("body > div:nth-child(27) > div:nth-child(3) > div:nth-child(3) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(6) > a:nth-child(1) > div:nth-child(2) > p:nth-child(1)");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait.until(ExpectedConditions.elementToBeClickable(VesionConfigBy)).click();
    }
    public void WaitingForSessionsHistoryPage() {
        By cartBy = By.cssSelector("body > div:nth-child(27) > div:nth-child(3) > div:nth-child(3) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(7) > a:nth-child(1) > div:nth-child(2) > p:nth-child(1)");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait.until(ExpectedConditions.elementToBeClickable(SessionsBy)).click();
    }
    public void WaitingForManageLicensesPage() {
        By cartBy = By.cssSelector("body > div:nth-child(27) > div:nth-child(3) > div:nth-child(3) > div:nth-child(1) > ul:nth-child(1) > li:nth-child(8) > a:nth-child(1) > div:nth-child(2) > p:nth-child(1)");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait.until(ExpectedConditions.elementToBeClickable(ManageLiscBy)).click();
    }
}
