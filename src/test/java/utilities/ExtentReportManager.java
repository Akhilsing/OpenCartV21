package utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {

    // 🔥 Report generator (HTML file builder)
    public ExtentSparkReporter sparkReporter;

    // 🔥 Main report engine
    public ExtentReports extent;

    // 🔥 Each test entry in report
    public ExtentTest test;

    // ================== BEFORE SUITE START ==================
    @Override
    public void onStart(ITestContext context) {

        // 📌 Create unique report name using timestamp
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        // 📌 Get test suite name from TestNG XML
        String testName = context.getName();

        // 📌 Report file path
        String reportPath = "./reports/" + testName + "_" + timeStamp + ".html";

        // 📌 Initialize HTML reporter
        sparkReporter = new ExtentSparkReporter(reportPath);

        // 📌 Initialize Extent report engine
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // 📌 Report UI settings
        sparkReporter.config().setReportName("Automation Report");
        sparkReporter.config().setDocumentTitle("Test Results");
    }

    // ================== TEST PASSED ==================
    @Override
    public void onTestSuccess(ITestResult result) {

        // 📌 Create test entry in report
        test = extent.createTest(getTestName(result));

        // 📌 Log Excel/DataProvider row data
        logDataProviderData(result);

        // 📌 Mark test as PASS
        test.log(Status.PASS, "Test Passed Successfully");
    }

    // ================== TEST FAILED ==================
    @Override
    public void onTestFailure(ITestResult result) {

        // 📌 Create test entry
        test = extent.createTest(getTestName(result));

        // 📌 Log Excel row data (very important for debugging)
        logDataProviderData(result);

        // 📌 Basic failure message
        test.log(Status.FAIL, "Test Method Failed");
        test.log(Status.FAIL, "Test Failed");

        // 📌 Log actual exception (why test failed)
        if (result.getThrowable() != null) {
            test.log(Status.FAIL, result.getThrowable());
        }

        // 📌 Screenshot capture logic
        try {
            BaseClass base = (BaseClass) result.getInstance();

            if (base.driver != null) {
                String img = base.captureScreenshot(result.getName(), base.driver);

                // 📌 Attach screenshot in report
                test.addScreenCaptureFromPath(img);
            } else {
                test.log(Status.WARNING, "Driver was null, screenshot not captured");
            }

        } catch (Exception e) {
            test.log(Status.WARNING, "Screenshot error: " + e.getMessage());
        }
    }

    // ================== TEST SKIPPED ==================
    @Override
    public void onTestSkipped(ITestResult result) {

        test = extent.createTest(getTestName(result));

        logDataProviderData(result);

        test.log(Status.SKIP, "Test Skipped");

        if (result.getThrowable() != null) {
            test.log(Status.INFO, result.getThrowable().getMessage());
        }
    }

    // ================== EXCEL DATA LOGGER ==================
    private void logDataProviderData(ITestResult result) {

        // 📌 Get parameters from DataProvider (Excel row values)
        Object[] params = result.getParameters();

        if (params != null && params.length > 0) {

            StringBuilder data = new StringBuilder("Excel Row Data => ");

            for (Object param : params) {
                data.append(param).append(" | ");
            }

            // 📌 Log full row data in report
            test.log(Status.INFO, data.toString());

        } else {
            test.log(Status.INFO, "No DataProvider data found");
        }
    }

    // ================== TEST NAME FORMAT ==================
    private String getTestName(ITestResult result) {

        // 📌 Format: ClassName :: MethodName
        return result.getTestClass().getName()
                + " :: "
                + result.getMethod().getMethodName();
    }

    // ================== AFTER SUITE END ==================
    @Override
    public void onFinish(ITestContext context) {

        // 📌 Flush all logs into HTML report
        extent.flush();
    }
}