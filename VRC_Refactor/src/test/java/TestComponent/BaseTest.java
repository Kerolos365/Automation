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
    public Courses courses;
    public VersionConfigurations versionConfigurations;
    public SessionHistory sessionHistory;

    // Locators for login page
    public static final String LOGIN_URL = "http://vrc.genesiscreations.co:3000/login/";
    public static final String VALID_EMAIL = "kerolos.maged@genesiscreations.co";
    public static final String VALID_PASSWORD = "Test123456";

    // Locators for home page
    public final By profileButton = By.cssSelector(".MuiButtonBase-root.MuiButton-root.MuiButton-contained.MuiButton-containedPrimary.MuiButton-sizeMedium.MuiButton-containedSizeMedium.MuiButton-root.MuiButton-contained.MuiButton-containedPrimary.MuiButton-sizeMedium.MuiButton-containedSizeMedium.css-1xl4ka3");
    public final By profileHeader = By.cssSelector("h5.MuiTypography-h5");
    public final By editButton = By.cssSelector(".MuiButton-outlinedPrimary.css-cvhhm1");
    public final By changePassButton = By.xpath("//a[text()='Change Password']");
    public final By positionInput = By.xpath("//input[@placeholder='Job Title']");
    public final By emailinput = By.xpath("//input[@placeholder='E-mail']");
    public final By usernameinput = By.xpath("//input[@placeholder='Username']");
    public final By updateButton = By.cssSelector(".MuiButtonBase-root.MuiButton-root.MuiButton-contained.MuiButton-containedPrimary.MuiButton-sizeMedium.MuiButton-containedSizeMedium.MuiButton-root.MuiButton-contained.MuiButton-containedPrimary.MuiButton-sizeMedium.MuiButton-containedSizeMedium.css-1xl4ka3");
    public final By popUpMessage = By.cssSelector("div[role='status']");
    public final By errorNameMessage = By.xpath("//p[text()='Username must be at least 2 characters.']");
    public final By errorMailMessage = By.xpath("//p[text()='Invalid email!']");
    public final By positionHeader = By.cssSelector(".MuiBox-root.css-16bhin1 .MuiBox-root.css-104ckfi:nth-of-type(1) p");
    public final By currentPass = By.xpath("//input[@id='currentPassword']");
    public final By newPass = By.xpath("//input[@id='newPassword']");
    public final By confirmPass = By.xpath("//input[@id='confirmPassword']");
    public final By confirmButton = By.xpath("//button[text()='Confirm']");
    public final By RedMissingErrorMessage = By.xpath("//p[contains(@class, 'MuiFormHelperText-root') and text()='Password must be at least 8 characters.']");
    public static final String USER_NAME = "Kerolos";
    public static final String CurrentPass = "Test123456";
    public static final String NewPass = " Test12345";
    public static final String UPDATE_TEXT = "QA Engineer";

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
    public static final String Industry = "Hospital & Health Care";
    public final By IndustryOption = By.xpath("//li[@role='option' and @data-value='" + Industry + "']");
    public final By IndustryColumnBy = By.xpath("//div[@role='grid']//div[@role='row'][position() > 0]//div[@role='gridcell' and @data-field='industry']");
    public static final String Country = "Canada";
    public final By CountryOption = By.xpath("//li[@role='option' and normalize-space(text())='" + Country + "']");
    public final By CountryColumnBy = By.xpath("//div[@role='grid']//div[@role='row'][position() > 0]//div[@role='gridcell' and @data-field='country']");
    public static final String Search_Item = "Genesis";
    public final By AddOrganizationButtonBy = By.xpath("//button[normalize-space()='Add Organization']");
    public final By OrganizationNameInputBy = By.xpath("//input[@placeholder='Organization Name']");
    public static final String OrganizationIndustryInput = "Hospital & Health Care";
    public final By OrganizationIndustryDropdownBy = By.xpath("//div[@id='industry-select' and @role='button']");
    public final By OrganizationIndustryInputOption = By.xpath("//li[@role='option' and @data-value='" + OrganizationIndustryInput + "']");
    public static final String OrganizationCountryOption = "CA";
    public final By OrganizationCountryDropdownBy = By.xpath("//div[@id='country-select' and @role='button']");
    public final By OrganizationCountryOptionBy = By.xpath("//li[@role='option' and @data-value='" + OrganizationCountryOption + "']");
    public final By ConfirmLogoButtonBy = By.xpath("//button[contains(@class,'MuiButton-contained') and contains(@class,'MuiButton-sizeSmall') and text()='Confirm']");
    public final By ConfirmButtonBy = By.xpath("//button[contains(@class,'MuiButton-containedPrimary') and normalize-space()='Confirm']");
    public final By EditButtonBy = By.xpath("//a[contains(@href, '/edit/') and contains(., 'Edit')]");
    public final By UpdateButtonBy = By.xpath("//button[text()='Update' and contains(@class, 'MuiButton-containedPrimary')]");

    //Locators for manage users
    public final By SearchUsersBy = By.xpath("//input[@placeholder='Search User']");
    public static final String Search_User = "Kerolos Maged";
    public final By UsersColumnBy = By.xpath("//div[@role='row']//div[@role='gridcell' and @data-field='username']");
    public final By SelectUserVerificationDropdownyBy = By.xpath("//div[@role='button' and @aria-labelledby='verification-select' and text()='All']");
    public static final String status = "verified";
    public final By StatusOption = By.xpath("//li[@role='option' and @data-value='" + status + "']");
    public final By StatusColumnBy = By.xpath("//div[@role='row']//div[@role='gridcell' and @data-field='isEmailVerified']");
    public final By SelectUserAccessDropdownyBy = By.xpath("//div[@role='button' and @aria-labelledby='access-select' and normalize-space()='All']");
    public static final String Access = "active";
    public static final String ExpectedAccessLabel = "Activated"; // used in column check
    public final By AccessOption = By.xpath("//li[@role='option' and @data-value='" + Access + "']");
    public final By AccessColumnBy = By.xpath("//div[@role='row']//div[@role='gridcell' and @data-field='active']");
    public final By RoleDropdownyBy = By.xpath("//div[@role='button' and @aria-labelledby='role-select' and normalize-space()='All']");
    public static final String RoleName = "Super User";
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
    public final By confirmPasswordMatchError = By.xpath("//p[text()='Passwords must match.']");
    public final By UserActionButtonBy = By.xpath("//div[@class='MuiDataGrid-row MuiDataGrid-row--firstVisible']//button[@type='button']//*[name()='svg']");
    public final By UserEditButtonBy = By.xpath("//a[@role='menuitem' and contains(@href, '/users/') and contains(text(), 'Edit')]");
    public final By UserViewButtonBy = By.xpath("//a[@role='menuitem' and contains(@href, '/users/') and contains(text(), 'View')]");
    public final By UserUpdateButtonBy = By.xpath("//button[@type='button' and contains(., 'Update')]");
    public final By UserAddressFieldBy = By.xpath("//input[@placeholder='7777, Mendez Plains, Florida']");
    public final By UserUpdateMessageBy = By.xpath("//div[@role='status']");

    // Locators for Roles page
    public final By RolesExportButtonBy = By.xpath("//button[contains(@class, 'MuiButton-outlined') and contains(text(), 'Export')]");
    public final By RolesSearchBy = By.xpath("//input[@placeholder='Search User']");
    public final By RolesUsersColumnBy = By.xpath("//div[@role='gridcell' and @data-colindex='1']");
    public final By privilegesBy = By.xpath("//div[@data-field='actions' and @role='gridcell']//button[@type='button']");
    public final By privilegesSubmitBy = By.xpath("//button[@class='MuiButtonBase-root MuiButton-root MuiButton-contained MuiButton-containedPrimary MuiButton-sizeLarge MuiButton-containedSizeLarge MuiButton-root MuiButton-contained MuiButton-containedPrimary MuiButton-sizeLarge MuiButton-containedSizeLarge css-8b9g06' and text()='Submit']");
    public final By SubmitMessageBy = By.xpath("//div[@role='status']");
    public final By privilegesChangeBy = By.xpath("//input[@class='PrivateSwitchBase-input MuiSwitch-input css-1m9pwf3']");

    // Locators for manage licenses
    public final By LicensesExportButtonBy = By.xpath("//button[contains(text(), 'Export')]");
    public final By SearchLicensessBy = By.xpath("//input[@placeholder='Search License']");
    public static final String Licenses_Name = "Genesis SAE";
    public final By LicenseNameColumnBy = By.xpath("//div[@role='gridcell' and @data-colindex='1']");
    public final By SelectLicenseStatusDropdownyBy = By.xpath("//div[@role='button'and@aria-haspopup='listbox'and@aria-labelledby='status-select']");
    public static final String Licensestatus = "active";
    public final By LicenseStatusOption = By.xpath("//li[@role='option' and @data-value='" + Licensestatus + "']");
    public final By LicenseStatusColumnBy = By.xpath("//div[@role='gridcell' and @data-field='active']");
    public final By AddLicenseButtonBy = By.xpath("//button[contains(@class, 'MuiButton-root') and contains(text(), 'Add License')]");
    public static final String Licenses_NewName = "GenesisSAE" + new Faker().letterify("?????");
    public final By LicenseNamefieldBy = By.xpath("//input[@placeholder='License Name']");
    public final By OrganizationDrobdownBy = By.xpath("//div[@id='organization-select' and @role='button']");
    public final By FirstOrganizationOption = By.xpath("//ul[@role='listbox']//li[1]");
    public final By ExpireDateBy = By.xpath("//input[@type='date']");
    public final By CreateLicenseButtonBy = By.xpath("//button[normalize-space()='Create License']");
    public final By VrcCourseBy = By.xpath("//div[contains(@class, 'MuiCardMedia-root') and contains(@style, 'medicalCourse.jpg')]");
    public final By VrcFirstModuleBy = By.xpath("//div[contains(@class, 'MuiCardContent-root')]//p[contains(text(), 'Garbing and Hand Hygiene')]");
    public final By VrcFirstInteractionBy = By.xpath("//div[contains(@class, 'MuiCardContent-root') and .//p[contains(text(), 'Washing Hands')]]");
    public final By AddContentButtonBy = By.xpath("//button[contains(text(), 'Add Contents')]");
    public final By licenseOrganizationErrorMessage = By.xpath("//p[contains(text(), 'Organization is required')]");
    public final By licenseDateErrorMessage = By.xpath("//p[contains(text(), 'You need to select a future date for expiration')]");
    public final By LicenseHeaderBy = By.xpath("//h5[normalize-space()='License Contents']");

    // Locators For Courses
    public final By CoursesSearchBy = By.xpath("//input[@placeholder='Find your course' and @type='search']");
    public static final String Course_Name = "Medical Hygiene Training in VR";
    public final By Courses_Name = By.xpath("//h6[contains(@class, 'MuiTypography-h6')][contains(.,'" + Course_Name + "')]");
    public final By Course_Details = By.xpath("//p[contains(text(), 'The medical course is designed to train individuals')]");
    public final By Course_Header = By.xpath("//span[text()='Medical Hygiene Training in VR']");
    public final By First_Module = By.xpath("//div[@class='MuiCardContent-root css-295pkn']/p[@class='MuiTypography-root MuiTypography-body1 css-g614xj']");
    public final By First_Module_Count = By.xpath("//div[@class='MuiBox-root css-q7b74t']//p[@class='MuiTypography-root MuiTypography-body1 css-13u6hmq']");
    public final By WashingHands_interaction_First_Module = By.xpath("//div[@class='MuiGrid-root MuiGrid-item MuiGrid-grid-xs-12 MuiGrid-grid-md-6 MuiGrid-grid-lg-4 css-170ukis'][1]//p[@class='MuiTypography-root MuiTypography-body1 css-g614xj']");
    public final By DonningGloves_interaction_First_Module = By.xpath("//div[@class='MuiGrid-root MuiGrid-item MuiGrid-grid-xs-12 MuiGrid-grid-md-6 MuiGrid-grid-lg-4 css-170ukis'][2]//p[@class='MuiTypography-root MuiTypography-body1 css-g614xj']");
    public final By CleaningPEC_interaction_First_Module = By.xpath("//div[@class='MuiGrid-root MuiGrid-item MuiGrid-grid-xs-12 MuiGrid-grid-md-6 MuiGrid-grid-lg-4 css-170ukis'][3]//p[@class='MuiTypography-root MuiTypography-body1 css-g614xj']");
    public final By DonningPPE_interaction_First_Module = By.xpath("//div[@class='MuiGrid-root MuiGrid-item MuiGrid-grid-xs-12 MuiGrid-grid-md-6 MuiGrid-grid-lg-4 css-170ukis'][4]//p[@class='MuiTypography-root MuiTypography-body1 css-g614xj']");
    public final By DonningGown_interaction_First_Module = By.xpath("//div[@class='MuiGrid-root MuiGrid-item MuiGrid-grid-xs-12 MuiGrid-grid-md-6 MuiGrid-grid-lg-4 css-170ukis'][5]//p[@class='MuiTypography-root MuiTypography-body1 css-g614xj']");
    public final By CleaningSink_First_Module = By.xpath("//div[@class='MuiGrid-root MuiGrid-item MuiGrid-grid-xs-12 MuiGrid-grid-md-6 MuiGrid-grid-lg-4 css-170ukis'][6]//p[@class='MuiTypography-root MuiTypography-body1 css-g614xj']");
    public final By Second_Module = By.xpath("//div[@class='MuiCardContent-root css-tcmlgr']/p[@class='MuiTypography-root MuiTypography-body1 css-g614xj']");
    public final By Second_Module_Count = By.xpath("//div[@class='MuiBox-root css-q7b74t']//p[@class='MuiTypography-root MuiTypography-body1 css-13u6hmq']");
    public final By CleaningWasteContainer_interaction_Second_Module = By.xpath("//div[@class='MuiGrid-root MuiGrid-item MuiGrid-grid-xs-12 MuiGrid-grid-md-6 MuiGrid-grid-lg-4 css-170ukis'][1]//p[@class='MuiTypography-root MuiTypography-body1 css-g614xj']");
    public final By TableCleaning_interaction_Second_Module = By.xpath("//div[@class='MuiGrid-root MuiGrid-item MuiGrid-grid-xs-12 MuiGrid-grid-md-6 MuiGrid-grid-lg-4 css-170ukis'][2]//p[@class='MuiTypography-root MuiTypography-body1 css-g614xj']");
    public final By NewSession_Button = By.xpath("//button[normalize-space()='New session']");
    public final By NewLicense_Button = By.xpath("//a[normalize-space()='New license']");

    // Locators for version configurations page
    public final By SearchVersionsBy = By.xpath("//input[@placeholder='Search Configuration']");
    public final By Version_Name_ColumnBy = By.xpath("//div[@role='row']//div[@role='gridcell' and @data-field='name']");
    public final By Versions_Type_DropdownBy = By.xpath("//div[@role='button' and @aria-labelledby='type-select']");
    public final By Versions_Type_columnBy = By.xpath("//div[@role='row']//div[@role='gridcell' and @data-field='type']");
    public static final String Version_Type = "training";
    public final By VersionTypeOption = By.xpath("//li[@role='option' and @data-value='" + Version_Type + "']");
    public final By SelectRowCheckbox =By.xpath("(//div[contains(@class,'MuiDataGrid-cellCheckbox')]//span[contains(@class,'MuiCheckbox-root')])[1]");
    public final By Versions_CheckBoxColumnBy = By.xpath("//div[@role='row']//div[@role='gridcell' and @data-field='__check__']");
    public final By Versions_ActionsColumnBy = By.xpath("//div[@role='row']//div[@role='gridcell' and @data-field='actions']");
    public final By ArchiveButton = By.xpath("//button[@aria-label='Archive']");
    public final By VersionActionButtonBy =   By.xpath("(//div[@role='gridcell' and @data-field='actions']//button)[1]");
    public final By ArchivedVersionsButton = By.xpath("//button[contains(@class, 'MuiButton-root') and contains(., 'Archived Versions')]");
    public final By confMessagenBy = By.xpath("//div[@role='status']");
    public final By AddVersionButtonBy = By.xpath("//button[normalize-space()='Add Version Configuration']");
    public final By VesrionNameInputBy = By.xpath("//input[@placeholder='Version configuration name']");
    public final By VesrionNextButtonBy = By.xpath("//button[normalize-space()='Next']");
    public final By VRC_CourseBy = By.xpath("//div[contains(@class, 'MuiCardMedia-root') and contains(@style, 'medicalCourse.jpg')]");
    public final By VRC_FirstModuleBy = By.xpath("//div[contains(@class, 'MuiCardContent-root')]//p[contains(text(), 'Garbing and Hand Hygiene')]");
    public final By CreateConfigButtonBy = By.xpath("//button[normalize-space()='Create Configuration']");
    public final By ActivatedUsersAccessColumn = By.xpath("//div[@data-field='active']//span[text()='Activated']");
    public final By VerifiedUsersStatusColumn = By.xpath("//div[@data-field='isEmailVerified']//span[text()='Verified']");
    public final By AssignUsersButtonBy = By.xpath("//button[normalize-space()='Assign users']");
    public final By VersionDateRangeButtonBy = By.xpath("//button[normalize-space(text())='Date Range']");
    public final By NewSessionNameColumnBy = By.xpath("//div[@role='row']//div[@role='gridcell' and @data-field='sessionName']");
    public final By errorNameVesrionMessBy = By.xpath("//p[normalize-space(text())='Use letters and numbers only!']");

    // Locators for session history
    public final By SessionTypeColumnBy = By.xpath("//div[@role='rowgroup']/div[@role='row']/div[@data-field='status']");
    public final By SessionNameColumnBy = By.xpath("//div[@role='rowgroup']/div[@role='row']/div[@data-field='sessionName']");
    public final By SearchSessionBy = By.xpath("//input[@placeholder='Search History']");
    public final By ResendPINCodeBy = By.xpath("//button[normalize-space(text())='Resend PIN Code']");
    public final By ReassignButtonBy = By.xpath("//button[normalize-space(text())='Reassign']");
    public final By SessionActionButtonBy = By.xpath("//div[@data-field='actions']//button[@type='button']");
    public final By DeactivateButtonBy = By.xpath("//button[normalize-space()='Deactivate']");
    public final By UserNameColumnBy = By.xpath("//div[@role='rowgroup']/div[@role='row']/div[@data-field='username']");

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
        courses = new Courses(driver);
        versionConfigurations = new VersionConfigurations(driver);
        sessionHistory = new SessionHistory(driver);
        login.goTo("http://vrc.genesiscreations.co:3000/login/");
        return login;
    }
    @AfterMethod
    public void quitBrowser() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }
}
