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

//import static testcases.course.CourseRegistrationDuplicateTest.rand;

public class CourseRegistrationSuccessTest extends BaseTest {


    private static final String ACCOUNT = "thanhthuy01"; // account chưa đăng ký
    private static final String PASSWORD = "Admin@123456";

    private void login(WebDriver driver) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLoginLink();
        loginPage.login(ACCOUNT, PASSWORD);
    }

    // 1. Create a mutable list (ArrayList) from the original array.
    // Use static data so that the data is retained across all test cases.
    private static final List<String> COURSE_POOL = new ArrayList<>(Arrays.asList(
            "01230123", "09876788", "100999", "1009991"
    ));

    private static final Random RAND = new Random();

    /**
     * Lấy một Course ID ngẫu nhiên và XÓA nó khỏi pool để đảm bảo không trùng.
     * synchronized giúp an toàn khi chạy test song song (parallel).
     */
    public static synchronized String getRandomUniqueCourseId() {
        // 2. Kiểm tra nếu "kho" đã hết sạch ID
        if (COURSE_POOL.isEmpty()) {
            throw new RuntimeException("LỖI: Tất cả Course ID đã được sử dụng hết!");
        }

        // 3. Random một vị trí bất kỳ trong số những ID còn lại
        int randomIndex = RAND.nextInt(COURSE_POOL.size());

        // 4. Lấy ra và XÓA LUÔN phần tử tại vị trí đó
        // Hàm remove(index) vừa trả về giá trị, vừa xóa phần tử đó khỏi list
        return COURSE_POOL.remove(randomIndex);
    }

    private static final String COURSE_ID = getRandomUniqueCourseId();

    @Test(description = "DKKH_02 - Register course successfully", groups = {"smoke","course"})
    public void registerCourseSuccessfully() {

        WebDriver driver = DriverFactory.getDriver();

        // Step 1: Login
        login(driver);

        // Step 2: Open course detail
        CourseDetailPage page = new CourseDetailPage(driver);
        page.openCourseDetail(COURSE_ID);
        System.out.println("course ID:" + COURSE_ID);

        // Step 3: Register
        By bybtnRegisterCource = By.xpath("//button[text()='Đăng ký']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(bybtnRegisterCource));

        // step 4: Verify message
        element.click();
        By bySuccesMsg = By.xpath("//div[@class='swal-title']");
        WebElement succesMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(bySuccesMsg));
        Assert.assertEquals(succesMsg.getText(), "Đăng kí thành công", "Incorrect registration message !");

    }
}