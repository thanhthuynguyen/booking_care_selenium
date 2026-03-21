package testcases.course;

import base.BaseTest;
import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CourseDetailPage;
import pages.LoginPage;

import java.time.Duration;
import java.util.*;

public class Course_01_RegisterCourse_With_Login_Redirect extends BaseTest {

    private static final String ACCOUNT = "thanhthuy01"; // account chưa đăng ký
    private static final String PASSWORD = "Admin@123456";

    private void login(WebDriver driver) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ACCOUNT, PASSWORD);
    }

    // 1. Create a mutable list (ArrayList) from the original array.
    // Use static data so that the data is retained across all test cases.
    private static final List<String> COURSE_POOL = new ArrayList<>(Arrays.asList(
            "000123456", "01230123", "09876788", "100999", "1009991", "10099922", "100999999", "1111111111", "111111111111", "11205"
    ));

    private static final Random RAND = new Random();

    /**
     * Get a random Course ID and DELETE it from the pool to ensure there are no duplicates.
     * Synchronization ensures safety when running tests in parallel.
     */
    public static synchronized String getRandomUniqueCourseId() {
        // Check if the "warehouse" has run out of IDs.
        if (COURSE_POOL.isEmpty()) {
            throw new RuntimeException("LỖI: Tất cả Course ID đã được sử dụng hết!");
        }

        // Randomly select any position from the remaining IDs.
        int randomIndex = RAND.nextInt(COURSE_POOL.size());

        // Extract and DELETE the element at that position.
        return COURSE_POOL.remove(randomIndex);
    }

    private static final String COURSE_ID = getRandomUniqueCourseId();

    @Test(description = "DKKH_01 - Register course with login redirect", groups = {"smoke","course"})
    public void registerCourseSuccessfully() {

        WebDriver driver = DriverFactory.getDriver();

        // Step 1: Open course detail
        CourseDetailPage page = new CourseDetailPage(driver);
        page.openCourseDetail(COURSE_ID);
        System.out.println("course ID:" + COURSE_ID);

        // step 2 : Get course name from course detail page before registration
        By byDetailTitleElem = By.xpath("//h4[@class='titleDetailCourse']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement elementDetailTitleElem = wait.until(ExpectedConditions.visibilityOfElementLocated(byDetailTitleElem));
        String expectedCourseName = elementDetailTitleElem.getText().trim();
        System.out.println("Tên khóa học tại trang Chi tiết: " + expectedCourseName);

        // Step 3: Click "Đăng ký" button
        By bybtnRegisterCource = By.xpath("//button[text()='Đăng ký']");
        WebElement btnFirstClick = wait.until(ExpectedConditions.elementToBeClickable(bybtnRegisterCource));
        btnFirstClick.click();

        // Step 4: Login
        login(driver);
        System.out.println("Đã đăng nhập thành công với tài khoản: " + ACCOUNT);

        // Step 5: Click "Đăng ký" button again after login
        WebElement btnSecondClick = wait.until(ExpectedConditions.elementToBeClickable(bybtnRegisterCource));
        btnSecondClick.click();
        System.out.println("Đã click Đăng ký lần 2 sau khi đăng nhập");

        // Step 6: Wait and Verify message ---
        By bySuccesMsg = By.xpath("//div[@class='swal-title']");
        WebElement succesMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(bySuccesMsg));
        Assert.assertEquals(succesMsg.getText(), "Đăng kí thành công", "Thông báo đăng ký không chính xác!");

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
            for (int i = 0; i < enrolledCourseNames.size(); i++) {
                String courseName = enrolledCourseNames.get(i).getText().trim();
                System.out.println("Khóa học thứ " + (i + 1) + ": " + courseName);
            }

            // Check the comparison logic.
            boolean isMatch = enrolledCourseNames.stream().anyMatch(elementName -> elementName.getText().trim().equalsIgnoreCase(expectedCourseName));

            // Process notifications based on results.
            if (isMatch) {
                // Notification when you pass
                System.out.println("Đã tìm thấy khóa học '" + expectedCourseName + "' ở trang My Account");
            } else {
                // Print the actual list for easier debugging when it fails (Optional)
                System.out.println("KHÔNG tìm thấy khóa học mong đợi: '" + expectedCourseName + "'.");
                System.out.println("Danh sách thực tế đang có: ");
                enrolledCourseNames.forEach(e -> System.out.println("- " + e.getText().trim()));
            }

            // Use Assert to mark the Test Case status in the Report (TestNG/JUnit)
            Assert.assertTrue(isMatch, "FAIL: Khóa học '" + expectedCourseName + "' không tìm thấy hoặc hiển thị sai tên tại trang My Account!");
        }
    }
}