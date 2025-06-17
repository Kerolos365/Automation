package TestComponent;
import ExtentReport.ExtentReportsTestNG;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.Duration;

public class Listeners  extends BaseTest implements ITestListener {
    ExtentReports extent = ExtentReportsTestNG.GenerateExtentReport();
    ExtentTest Test;
    //thread local for parallel execution
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
    @Override
    public void onTestStart(ITestResult result) {
        Test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(Test);
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS , "Test is passed");
    }
    @SneakyThrows
    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());

        try {
            //  Ensure the WebDriver instance is correctly retrieved
            WebDriver driver = (WebDriver) result.getTestClass()
                    .getRealClass()
                    .getField("driver")
                    .get(result.getInstance());
            //  Thread sleep for 2 seconds before taking the screenshot
            Thread.sleep(3000); // Wait for the page to be ready for screenshot capture

            // Call the corrected screenshot method
            String screenshotPath = TakesScreenShot(result.getMethod().getMethodName(), driver);

            // Attach screenshot to Extent Report (using relative path)
            extentTest.get().addScreenCaptureFromPath(screenshotPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Override
    public void onTestSkipped(ITestResult result) {
    }
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {
    }
    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

}
