package testcases.course;

import base.BaseTest;
import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CourseDetailPage;
import pages.LoginPage;
import pages.RegisterPage;

import java.time.Duration;
import java.util.*;

public class Course_06_Register_New_User_And_Enroll_Course_From_Detail_Page extends BaseTest {

    private static final List<String> COURSE_POOL = new ArrayList<>(Arrays.asList(
            "000123456", "01230123", "09876788", "100999", "11205"
    ));

    private static final Random RAND = new Random();

    public static synchronized String getRandomUniqueCourseId() {
        if (COURSE_POOL.isEmpty()) throw new RuntimeException("LỖI: Hết ID!");
        return COURSE_POOL.remove(RAND.nextInt(COURSE_POOL.size()));
    }

    @Test(description = "DKKH_036 - Register New User And Enroll Course From Detail Page", groups = {"smoke","course"})
    public void registerNewUserAndEnrollCourseFromDetailPageSuccessfully() throws InterruptedException {
        WebDriver driver = DriverFactory.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String courseId = getRandomUniqueCourseId();

        // Step 1: Mở chi tiết khóa học
        CourseDetailPage page = new CourseDetailPage(driver);
        page.openCourseDetail(courseId);

        // Step 2: Lấy tên khóa học
        By byDetailTitleElem = By.xpath("//h4[@class='titleDetailCourse']");
        String expectedCourseName = wait.until(ExpectedConditions.visibilityOfElementLocated(byDetailTitleElem)).getText().trim();
        LOG.info("Khóa học mục tiêu: " + expectedCourseName);

        // Step 3: Click Đăng ký (Lần 1 - Chưa login)
        By bybtnRegisterCource = By.xpath("//button[text()='Đăng ký']");
        wait.until(ExpectedConditions.elementToBeClickable(bybtnRegisterCource)).click();

        // STEP 4: Chuyển sang Form Đăng ký
        wait.until(ExpectedConditions.urlContains("login"));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("signUp"))).click();

        // Step 5: Điền thông tin đăng ký
        RegisterPage registerPage = new RegisterPage(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("taiKhoan")));

        String randomID = String.valueOf(System.currentTimeMillis()).substring(7);
        String account = "user" + randomID;
        String password = "Admin@123456";
        String email = "test" + randomID + "@gmail.com";

        LOG.info("--- THỰC HIỆN ĐĂNG KÝ: " + account + " ---");
        // Lưu ý: Hàm registerNewAccount nên bao gồm luôn việc nhấn nút Submit ở cuối hàm
        registerPage.registerNewAccount(account, "Hoc Vien", password, email, "0901234567", "GP01");

        // --- GIẢI PHÁP THAY THẾ THREAD.SLEEP ---
        // 1. Chờ Popup thông báo đăng ký thành công xuất hiện
        By byRegSuccessPopup = By.xpath("//div[contains(@class,'swal-title') and contains(text(),'thành công')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(byRegSuccessPopup));
        LOG.info("Đăng ký tài khoản thành công trên hệ thống.");

        // 2. Chờ Popup biến mất hoàn toàn trước khi click chuyển màn hình (Để tránh ElementClickInterceptedException)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(byRegSuccessPopup));

        // 3. Click nút Đăng nhập trên Overlay
        WebElement btnSignInOverlay = wait.until(ExpectedConditions.elementToBeClickable(By.id("signIn")));
        js.executeScript("arguments[0].click();", btnSignInOverlay);
        LOG.info("Đã chuyển sang form Đăng nhập.");
        Thread.sleep(3000);

        // Step 6: Thực hiện Đăng nhập
        By bySignInAccount = By.xpath("//div[contains(@class,'sign-in-container')]//input[@name='taiKhoan']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(bySignInAccount));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(account, password);
        LOG.info("Đã gửi lệnh Đăng nhập.");

        // Step 7: Chờ quay lại trang chi tiết và Đăng ký khóa học (Lần 2)
        // Đợi nút Đăng ký hiển thị lại (Chứng tỏ đã login xong và trang đã load)
        wait.until(ExpectedConditions.elementToBeClickable(bybtnRegisterCource));

        js.executeScript("arguments[0].click();", driver.findElement(bybtnRegisterCource));
        LOG.info("Đã click Đăng ký khóa học lần cuối.");

        // Step 8: Verify Popup đăng ký khóa học thành công
        By bySuccesMsg = By.xpath("//div[@class='swal-title' and text()='Đăng kí thành công']");
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(bySuccesMsg));
            LOG.info("🔥 CHÚC MỪNG: Đăng ký khóa học thành công!");
        } catch (Exception e) {
            LOG.error("Lỗi: Không thấy thông báo đăng ký khóa học thành công.");
            throw e;
        }
        // Đợi popup đóng để kết thúc sạch sẽ
        wait.until(ExpectedConditions.invisibilityOfElementLocated(bySuccesMsg));

        // Step 7: Wait for the success message to disappear before proceeding and click to myaccount page to check if the course is listed in the enrolled courses
        boolean isMsgHidden = wait.until(ExpectedConditions.invisibilityOfElementLocated(bySuccesMsg));
        if (isMsgHidden) {
            // Navigate to the user's profile page
            By byMyAccountLink = By.xpath("//a[@href='/thongtincanhan']");
            WebElement myAccountLink = wait.until(ExpectedConditions.visibilityOfElementLocated(byMyAccountLink));
            myAccountLink.click();
            wait.until(ExpectedConditions.urlContains("/thongtincanhan"));

            // Click on the "Khóa học" button to display the courses
            By byCourseTab = By.xpath("//button[text()='Khóa học']");
            WebElement courseTab = wait.until(ExpectedConditions.elementToBeClickable(byCourseTab));
            courseTab.click();

            // Wait for the list of courses to appear.
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("myCourseItem")));

            // Get a list of all course names currently in My Account.
            List<WebElement> enrolledCourseNames = driver.findElements(By.xpath("//div[@class='myCourseItem']//h6"));
            System.out.println("--- Danh sách tên khóa học trong My Account ---");
            LOG.info("--- Danh sách tên khóa học trong My Account ---");
            for (int i = 0; i < enrolledCourseNames.size(); i++) {
                String courseName = enrolledCourseNames.get(i).getText().trim();
                LOG.info("Khóa học thứ " + (i + 1) + ": " + courseName);
                System.out.println("Khóa học thứ " + (i + 1) + ": " + courseName);
            }

            // Check the comparison logic.
            boolean isMatch = enrolledCourseNames.stream().anyMatch(elementName -> elementName.getText().trim().equalsIgnoreCase(expectedCourseName));

            // Process notifications based on results.
            if (isMatch) {
                LOG.info("Đã tìm thấy khóa học '" + expectedCourseName + "' ở trang My Account");
                System.out.println("Đã tìm thấy khóa học '" + expectedCourseName + "' ở trang My Account");
            } else {
                // Print the actual list for easier debugging when it fails (Optional)
                LOG.info("KHÔNG tìm thấy khóa học mong đợi: '" + expectedCourseName + "'.");
                System.out.println("KHÔNG tìm thấy khóa học mong đợi: '" + expectedCourseName + "'.");
                System.out.println("Danh sách thực tế đang có: ");
                enrolledCourseNames.forEach(e -> System.out.println("- " + e.getText().trim()));
            }

            // Use Assert to mark the Test Case status in the Report (TestNG/JUnit)
            Assert.assertTrue(isMatch, "FAIL: Khóa học '" + expectedCourseName + "' không tìm thấy hoặc hiển thị sai tên tại trang My Account!");
        }
    }
}
