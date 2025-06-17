package TestComponent;
import net.datafaker.Faker;
import org.example.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.time.Duration;

public class BaseTest {
    public WebDriver driver;
    public Login login ;
    public Home home;
    public ManageOrganizations manageOrganizations;
    public ManageUsers manageUsers;
    public Roles roles;
    public ManageLicenses manageLicenses;

    // Locators for login page
    public static final String LOGIN_URL = "https://ark-dev.genesiscreations.co/login/?returnUrl=%2F";
    public static final String VALID_EMAIL = "kerolosmaged4@gmail.com";
    public static final String VALID_PASSWORD = "Test123456";

    // Locators for home page
    public final By profileButton = By.xpath("//a[@href='/users/670bb16a83dd62271f2128ad/' and contains(text(), 'View Profile')]");
    public final By profileHeader = By.cssSelector(".MuiTypography-h5");
    public final By editButton = By.xpath("//a[@href='/users/670bb16a83dd62271f2128ad/edit/' and text()='Edit']");
    public final By changePassButton = By.xpath("//a[text()='Change Password']");
    public final By positionInput = By.xpath("//input[@placeholder='Job Title']");
    public final By emailinput = By.xpath("//input[@placeholder='E-mail']");
    public final By usernameinput = By.xpath("//input[@placeholder='Username']");
    public final By updateButton = By.xpath("//button[@type='button' and text()='Update']");
    public final By popUpMessage = By.cssSelector("div[role='status']");
    public final By errorNameMessage = By.xpath("//p[text()='Username must be at least 2 characters.']");
    public final By errorMailMessage = By.xpath("//p[text()='Invalid email!']");
    public final By positionHeader = By.xpath("(//div[@class='MuiBox-root css-16bhin1']//p)[1]");
    public final By currentPass = By.xpath("//input[@id='currentPassword']");
    public final By newPass = By.xpath("//input[@id='newPassword']");
    public final By confirmPass = By.xpath("//input[@id='confirmPassword']");
    public final By confirmButton = By.xpath("//button[text()='Confirm']");
    public final By RedMissingErrorMessage = By.xpath("//p[contains(@class, 'MuiFormHelperText-root') and text()='Password must be at least 8 characters.']");
    public static final String USER_NAME = "Kerolos Maged";
    public static final String UPDATE_TEXT = "QA EXPERT";
    public static final String CurrentPass = "Test123456";
    public static final String NewPass = " Test12345";

    //locators for manage organizations page
    public final By ExportButtonBy = By.xpath("//button[normalize-space()='Export']");
    public final By SearchOrganizationsBy = By.xpath("//input[@placeholder='Search Organization']");
    public final By DateRangeButtonBy = By.xpath("//button[contains(., 'Date Range')]");
    public final By SetDateButtonBy = By.xpath("//button[contains(., 'Set')]");
    public final By SelectIndustryBy = By.xpath("//div[@role='button' and @aria-labelledby='industry-select' and text()='All']");
    public final By SelectCountryBy = By.xpath("//div[@role='button' and @aria-labelledby='country-select' and contains(@class, 'MuiSelect-select')]");
    public final By MessageForDateBy = By.xpath("//div[text()='No organizations found for the selected date range']");
    public final By NameColumnBy = By.xpath("//div[@role='grid']//div[@role='row'][position() > 0]//div[@role='gridcell' and @data-field='name']");
    public final By SuspendedColumnBy = By.xpath("//div[@role='grid']//div[@role='row'][position() > 0]//div[@role='gridcell' and @data-field='suspended']");
    public final By CreatedAtColumnBy = By.xpath("//div[@role='grid']//div[@role='row'][position() > 0]//div[@role='gridcell' and @data-field='createdAt']");
    public final By ActionButtonBy = By.xpath("//*[@data-testid=\"DotsVerticalIcon\"]");
    public final By SusbendedButtonBy = By.xpath("//div[normalize-space(.)='Suspend']");
    public final By ActivateButtonBy = By.xpath("//div[normalize-space(.)='Activate']");
    public static final String Industry = "Arts & Crafts";
    public final By IndustryOption = By.xpath("//li[@role='option' and @data-value='" + Industry + "']");
    public final By IndustryColumnBy = By.xpath("//div[@role='grid']//div[@role='row'][position() > 0]//div[@role='gridcell' and @data-field='industry']");
    public static final String Country = "Egypt";
    public final By CountryOption = By.xpath("//li[@role='option' and normalize-space(text())='" + Country + "']");
    public final By CountryColumnBy = By.xpath("//div[@role='grid']//div[@role='row'][position() > 0]//div[@role='gridcell' and @data-field='country']");
    public static final String Search_Item = "Genesis";
    public final By AddOrganizationButtonBy = By.xpath("//button[normalize-space()='Add Organization']");
    public final By OrganizationNameInputBy = By.xpath("//input[@placeholder='Organization Name']");
    public static final String OrganizationIndustryInput = "Arts & Crafts";
    public final By OrganizationIndustryDropdownBy = By.xpath("//div[@id='industry-select' and @role='button']");
    public final By OrganizationIndustryInputOption = By.xpath("//li[@role='option' and @data-value='" + OrganizationIndustryInput + "']");
    public static final String OrganizationCountryOption = "EG";
    public final By OrganizationCountryDropdownBy = By.xpath("//div[@id='country-select' and @role='button']");
    public final By OrganizationCountryOptionBy = By.xpath("//li[@role='option' and @data-value='" + OrganizationCountryOption + "']");
    public final By ConfirmLogoButtonBy = By.xpath("//button[contains(@class,'MuiButton-contained') and contains(@class,'MuiButton-sizeSmall') and text()='Confirm']");
    public final By ConfirmButtonBy = By.xpath("//button[contains(@class,'MuiButton-containedPrimary') and normalize-space()='Confirm']");
    public final By EditButtonBy = By.xpath("//a[contains(@href, '/edit/') and contains(., 'Edit')]");
    public final By UpdateButtonBy = By.xpath("//button[text()='Update' and contains(@class, 'MuiButton-containedPrimary')]");

    //Locators for manage users
    public final By SpecialExportButtonBy = By.xpath("//button[normalize-space()='special export']");
    public final By SearchUsersBy = By.xpath("//input[@placeholder='Search User']");
    public static final String Search_User = "Kerolos Maged";
    public final By UsersColumnBy = By.xpath("//div[@role='row']//div[@role='gridcell' and @data-field='username']");
    public final By SelectUserVerificationDropdownyBy = By.xpath("//div[@class='MuiGrid-root MuiGrid-container css-hcse15']//div[@role='button'][normalize-space()='All']");
    public static final String status = "verified";
    public final By StatusOption = By.xpath("//li[@role='option' and @data-value='" + status + "']");
    public final By StatusColumnBy = By.xpath("//div[@role='row']//div[@role='gridcell' and @data-field='isEmailVerified']");
    public final By SelectUserAccessDropdownyBy = By.xpath("//div[@role='button' and @aria-labelledby='access-select' and normalize-space()='All']");
    public static final String Access = "active";
    public static final String ExpectedAccessLabel = "Activated"; // used in column check
    public final By AccessOption = By.xpath("//li[@role='option' and @data-value='" + Access + "']");
    public final By AccessColumnBy = By.xpath("//div[@role='row']//div[@role='gridcell' and @data-field='active']");
    public final By RoleDropdownyBy = By.xpath("//div[@role='button' and @aria-labelledby='role-select' and normalize-space()='All']");
    public static final String RoleName = "AVC";
    public final By RoleOption = By.xpath("//li[@role='option' and normalize-space(text())='" + RoleName + "']");
    public final By RoleColumnBy = By.xpath("//div[@role='row']//div[@role='gridcell' and @data-field='role']");
    public final By addUserButtonBy = By.xpath("//button[contains(text(), 'Add User')]");
    public final By RegisterEmailBoxBy = By.xpath("//input[@placeholder='Email' and @type='email']");
    public final By RegisterNameBoxBy =By.xpath("//input[@placeholder='Username' and @type='text']");
    public static final String Pass = "Test12345";
    public final By RegisterPassBoxBy =By.xpath("//input[@placeholder='Password' and @type='password']");
    public final By RegisterConfirmPassBoxBy =By.xpath("//input[@placeholder='Confirm Password' and @type='password']");
    public static final String Title = "QA IS THE BEST";
    public final By RegisterTitleBoxBy =By.xpath("//input[@placeholder='Job Title' and @type='text']");
    public final By RegisterNextButtonBy = By.xpath("//button[contains(text(), 'Next') and @type='button']");
    public final By RegisterfirstNameInputBy = By.xpath("//input[@placeholder='First Name']");
    public final By RegisterlastNameInputBy = By.xpath("//input[@placeholder='Last Name']");
    public static final String Phone = "12345648984";
    public final By RegisterPhoneInputBy = By.xpath("//input[@placeholder='Phone number']");
    public static final String Address = "Florida";
    public final By RegisterAddressInputBy = By.xpath("//input[@placeholder='7777, Mendez Plains, Florida']");
    public final By NextButton2By = By.xpath("//button[text()='Next']");
    public final By submitButtonBy = By.xpath("//button[contains(@class, 'MuiButton-root') and text()='Submit']");
    public final By usernameValidationMsg = By.xpath("//p[text()='Username must be at least 2 characters.']");
    public final By emailValidationMsg = By.xpath("//p[text()='Invalid email!']");
    public final By passwordValidationMsg = By.xpath("//p[text()='Password must be at least 8 characters.']");
    public final By confirmPasswordMatchError = By.xpath("//p[text()='Passwords must match.']");
    public final By UserActionButtonBy = By.xpath("//div[@class='MuiDataGrid-row MuiDataGrid-row--firstVisible']//button[@type='button']//*[name()='svg']");
    public final By UserEditButtonBy = By.xpath("//a[@role='menuitem' and contains(., 'Edit')]");
    public final By UserViewButtonBy = By.xpath("//a[@role='menuitem' and contains(., 'View')]");
    public final By UserUpdateButtonBy = By.xpath("//button[@type='button' and contains(., 'Update')]");
    public final By UserAddressFieldBy = By.xpath("//input[@placeholder='7777, Mendez Plains, Florida']");
    public final By UserUpdateMessageBy = By.xpath("//div[@role='status']");

    // Locators for Roles page
    public final By RolesExportButtonBy = By.xpath("//button[contains(@class, 'MuiButton-outlined') and contains(text(), 'Export')]");
    public final By RolesSearchBy = By.xpath("//input[@placeholder='Search User']");
    public final By RolesUsersColumnBy = By.xpath("//div[@role='gridcell' and @data-colindex='1']");
    public final By privilegesBy = By.xpath("//div[@data-field='actions' and @role='gridcell']//button[@type='button']");
    public final By privilegesSubmitBy = By.xpath("//button[@type='submit' and text()='Submit']");
    public final By SubmitMessageBy = By.xpath("//div[@role='status']");
    public final By privilegesChangeBy = By.xpath("//input[@class='PrivateSwitchBase-input MuiSwitch-input css-1m9pwf3']");

    // Locators for manage licenses
    public final By LicensesExportButtonBy = By.xpath("//button[contains(text(), 'Export')]");
    public final By SearchLicensessBy = By.xpath("//input[@placeholder='Search License']");
    public static final String Licenses_Name = "Genesis SAE";
    public final By LicenseNameColumnBy = By.xpath("//div[@role='row']//div[@role='gridcell' and @data-field='name']");
    public final By SelectLicenseStatusDropdownyBy = By.xpath("//div[@role='button'and@aria-haspopup='listbox'and@aria-labelledby='status-select']");
    public static final String Licensestatus = "active";
    public final By LicenseStatusOption = By.xpath("//li[@role='option' and @data-value='" + Licensestatus + "']");
    public final By LicenseStatusColumnBy = By.xpath("//div[@role='gridcell' and @data-colindex='7']");
    public final By AddLicenseButtonBy = By.xpath("//button[contains(@class, 'MuiButton-root') and contains(text(), 'Add License')]");
    public static final String Licenses_NewName = "GenesisSAE" + new Faker().letterify("?????");
    public final By LicenseNamefieldBy = By.xpath("//input[@placeholder='License Name']");
    public final By OrganizationDrobdownBy = By.xpath("//div[@id='organization-select' and @role='button']");
    public final By FirstOrganizationOption = By.xpath("//ul[@role='listbox']//li[1]");
    public final By ExpireDateBy = By.xpath("//input[@type='date']");
    public final By EfficientOperationsRadioButton = By.xpath("//input[@type='radio' and @value='efficient-operations']");
    public final By DigiCheckbox = By.xpath("//input[@type='checkbox' and contains(@name, 'custom-checkbox-icons')]");
    public final By ContinueButtonBy = By.xpath("//button[text()='Continue']");
    public final By FinalConfirmButtonBy = By.xpath("//button[text()='Confirm']");
    public final By licenseOrganizationErrorMessage = By.xpath("//p[contains(text(), 'Organization is required')]");
    public final By licenseDateErrorMessage = By.xpath("//p[contains(text(), 'You need to select a future date for expiration')]");

    public WebDriver initializeDriver()  throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("C:\\Users\\Genesis QA\\IdeaProjects\\TestNG\\src\\main\\java\\Resources\\globalData.properties");
        prop.load(fis);
        String browser = prop.getProperty("browser");

        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else {
            throw new RuntimeException("Unsupported browser: " + browser);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }

    public String TakesScreenShot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File source = screenshot.getScreenshotAs(OutputType.FILE);

        // Ensure the screenshots folder exists
        String folderPath = System.getProperty("user.dir") + "/reports/screenshots/";
        File screenshotsDir = new File(folderPath);
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }

        // Save screenshot with timestamp
        String fileName = testCaseName + "_" + System.currentTimeMillis() + ".png";
        String absolutePath = folderPath + fileName;
        File finalDestination = new File(absolutePath);
        FileUtils.copyFile(source, finalDestination);

        // Return relative path for Extent Report
        return "screenshots/" + fileName;
    }

    @BeforeMethod
    public Login openBrowser() throws IOException {
        driver = initializeDriver();
        login = new Login(driver);
        home = new Home(driver);
        manageOrganizations = new ManageOrganizations(driver);
        manageUsers = new ManageUsers(driver);
        roles = new Roles(driver);
        manageLicenses = new ManageLicenses(driver);
        login.goTo("https://ark-dev.genesiscreations.co/login/?returnUrl=%2F");
        return login; // Returning the Login instance
    }

    @AfterMethod
    public void quitBrowser() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }
}
