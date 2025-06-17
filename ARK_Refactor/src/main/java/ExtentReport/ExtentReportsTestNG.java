package ExtentReport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.WebDriver;

public class ExtentReportsTestNG {
    WebDriver driver;
    static ExtentReports extent = new ExtentReports();

    public static ExtentReports GenerateExtentReport() {
        String Path = System.getProperty("user.dir") + "\\reports\\index.html";
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        // Extentreports , Extentsparkreporter
        ExtentSparkReporter reporter = new ExtentSparkReporter(Path);
        //change any property in the report
        reporter.config().setReportName("Demo ExtentReport");
        reporter.config().setDocumentTitle("Demo ExtentReport Result");
        //extent reports
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "el king Kero");
        extent.setSystemInfo("Date", todayDate);
        extent.setSystemInfo("User", "kings only");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("OS", "Windows");
        return extent;
    }
}
