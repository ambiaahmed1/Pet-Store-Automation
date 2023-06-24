package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);

        sparkReporter.config().setDocumentTitle("RestAssuredAutomation");
        sparkReporter.config().setReportName("Pet Store Users API");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "Pet Store Users API");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("user", "ambia");
    }

    public void onTestSuccess(ITestResult results) {
        test = extent.createTest(results.getName());
        test.assignCategory(results.getMethod().getGroups());
        test.createNode(results.getName());
        test.log(Status.PASS, "Test Passed");
    }

    public void onTestFailure(ITestResult results) {
        test = extent.createTest(results.getName());
        test.assignCategory(results.getMethod().getGroups());
        test.createNode(results.getName());
        test.log(Status.FAIL, "Test Failed");
        test.log(Status.FAIL, results.getThrowable().getMessage());
    }

    public void onTestSkipped(ITestResult results) {
        test = extent.createTest(results.getName());
        test.assignCategory(results.getMethod().getGroups());
        test.createNode(results.getName());
        test.log(Status.SKIP, "Test Skipped");
        test.log(Status.SKIP, results.getThrowable().getMessage());
    }

    public void onFinish(ITestContext testContext) {
        extent.flush();
    }
}
