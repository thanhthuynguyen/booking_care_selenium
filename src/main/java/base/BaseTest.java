package base;

import drivers.DriverFactory;
import drivers.DriverManager;
import drivers.DriverManagerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import report.ExtentReportManager;

import java.lang.reflect.Method;
import java.time.Duration;

public class BaseTest {

    protected final Logger LOG = LogManager.getLogger(getClass());
    protected final String BASE_URL = "https://demo2.cybersoft.edu.vn/";

    @BeforeSuite
    public void beforeSuite() {
        ExtentReportManager.initializeExtentReports();
    }
    protected String buildUrl(String path) {
        return BASE_URL + path;
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

        // Timeout configuration
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        LOG.info("Opening URL: " + BASE_URL);

        driver.get(BASE_URL);

        // Explicit wait to ensure the DOM has loaded.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        LOG.info("Page loaded successfully");

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