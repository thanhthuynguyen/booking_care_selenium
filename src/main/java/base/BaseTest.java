package base;

import drivers.DriverFactory;
import drivers.DriverManager;
import drivers.DriverManagerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import report.ExtentReportManager;

import java.lang.reflect.Method;

public class BaseTest {

    protected final Logger LOG = LogManager.getLogger(getClass());
    protected final String BASE_URL = "https://demo2.cybersoft.edu.vn/";

    @BeforeSuite
    public void beforeSuite() {
        ExtentReportManager.initializeExtentReports();
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void setup(@Optional("chrome") String browser, Method method) throws Exception {

        LOG.info("[Thread-" + Thread.currentThread().threadId() + "] Setup executed...");

        DriverManager driverManager = DriverManagerFactory.getDriverManager(browser);
        driverManager.createDriver();
        WebDriver driver = driverManager.getDriver();
        DriverFactory.setDriver(driver);

        driver.manage().window().maximize();
        driver.get(BASE_URL);

        ExtentReportManager.createTest(method.getName());
    }

    @AfterMethod
    public void teardown(ITestResult result) {

        LOG.info("Test completed");

        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentReportManager.captureScreenshot(
                    DriverFactory.getDriver(),
                    result.getMethod().getMethodName()
            );
            ExtentReportManager.fail(result.getThrowable().toString());
        }

        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            driver.quit();
        }

        DriverFactory.removeDriver();
    }

    @AfterSuite
    public void afterSuite() {
        ExtentReportManager.flushReports();
    }
}