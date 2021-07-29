package learning;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
    static ExtentReports extentReports;

    public static ExtentReports getReportObject() {
        String path = "reports/index.html";

        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(path);
        extentSparkReporter.config().setReportName("First Project Results");
        extentSparkReporter.config().setDocumentTitle("Test details");


        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        extentReports.setSystemInfo("Tester", "Jakub Kozak");

        return extentReports;

    }
}
