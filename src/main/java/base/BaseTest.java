package base;

import drivers.DriverFactory;
import drivers.DriverManager;
import drivers.DriverManagerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import report.ExtentReportManager;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

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
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        LOG.info("Opening URL: " + BASE_URL);
        driver.get(BASE_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        LOG.info("Page loaded successfully");
        ExtentReportManager.createTest(method.getName());
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        LOG.info("Test completed: " + result.getName());
        WebDriver driver = DriverFactory.getDriver();

        if (driver != null) {
            // Chỉ thực hiện Cleanup nếu test case thuộc group "course"
            List<String> groups = Arrays.asList(result.getMethod().getGroups());
            if (groups.contains("course")) {
                cleanAllEnrolledCourses(driver);
            }

            // Chụp ảnh màn hình nếu test Fail
            if (result.getStatus() == ITestResult.FAILURE) {
                ExtentReportManager.captureScreenshot(driver, result.getMethod().getMethodName());
                ExtentReportManager.fail(result.getThrowable().toString());
            }

            LOG.info("Quitting driver...");
            driver.quit();
        }
        DriverFactory.removeDriver();
    }

    protected void cleanAllEnrolledCourses(WebDriver driver) {
        LOG.info("--- [CLEANUP] Đang dọn dẹp TOÀN BỘ khóa học trong My Account ---");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // 1. Đi đến trang cá nhân
            driver.get(BASE_URL + "thongtincanhan");

            // 2. Click Tab Khóa học (Đảm bảo tab này được active để hiện element infoCourse)
            WebElement tabKhoaHoc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Khóa học']")));
            js.executeScript("arguments[0].click();", tabKhoaHoc);

            // 3. Vòng lặp xóa sạch
            while (true) {
                // Đợi danh sách item xuất hiện (hoặc đợi trang ổn định)
                // XPath này tìm tất cả các nút "Hủy khóa học" nằm trong cấu trúc bạn cung cấp
                String xpathCancelBtn = "//div[@id='infoCourse']//div[contains(@class,'myCourseItem')]//button[contains(text(),'Hủy khóa học')]";

                // Lấy danh sách các nút hiện đang có trên màn hình
                List<WebElement> buttons = driver.findElements(By.xpath(xpathCancelBtn));

                if (buttons.isEmpty()) {
                    LOG.info("✅ Cleanup hoàn tất: My Account đã sạch sẽ.");
                    break;
                }

                LOG.info("Tìm thấy " + buttons.size() + " khóa học cần xóa.");

                // Luôn thao tác với nút đầu tiên (Index 0)
                WebElement firstBtn = buttons.get(0);

                try {
                    // Cuộn tới nút để đảm bảo nó hiển thị
                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", firstBtn);
                    Thread.sleep(500); // Nghỉ cực ngắn để hiệu ứng cuộn dừng lại

                    // Click bằng JS để tránh bị che bởi header hoặc các element nổi khác
                    js.executeScript("arguments[0].click();", firstBtn);
                    LOG.info("Đã gửi lệnh Hủy 1 khóa học.");

                    // Đợi cho element đó biến mất khỏi DOM hoàn toàn mới quét tiếp
                    // Điều này cực kỳ quan trọng để tránh lỗi StaleElementReferenceException
                    wait.until(ExpectedConditions.stalenessOf(firstBtn));

                } catch (Exception e) {
                    LOG.warn("Có lỗi khi click nút Hủy, thử lại vòng lặp... " + e.getMessage());
                    driver.navigate().refresh(); // F5 nếu bị kẹt để quét lại từ đầu
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Khóa học']"))).click();
                }
            }
        } catch (Exception e) {
            LOG.error("❌ Cleanup thất bại nghiêm trọng: " + e.getMessage());
        }
    }

    @AfterSuite
    public void afterSuite() {
        ExtentReportManager.flushReports();
    }
}