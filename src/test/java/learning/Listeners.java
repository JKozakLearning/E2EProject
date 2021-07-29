package learning;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listeners extends Base implements ITestListener {
    ExtentTest extentTest;
    ExtentReports extentReports = ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> threadLocalTests = new ThreadLocal<ExtentTest>();


    @Override
    public void onTestStart(ITestResult result) {
        extentTest = extentReports.createTest(result.getMethod().getMethodName());
        threadLocalTests.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        threadLocalTests.get().log(Status.PASS, "Test Passed");

    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = null;
        String testMethodName = result.getMethod().getMethodName();
        Logger logs = LogManager.getLogger(result.getTestClass().getRealClass().getName());


        try {
            driver = (WebDriver)result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
        } catch (Exception e) {

        }
        try {
           threadLocalTests.get().addScreenCaptureFromPath("." + getScreenshotPath(testMethodName, driver)[0], getScreenshotPath(testMethodName, driver)[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
        threadLocalTests.get().fail(result.getThrowable());

        logs.error("Fails in Method:   " + testMethodName +
                "\nThrowable: " + result.getThrowable());

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        threadLocalTests.get().skip(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}
