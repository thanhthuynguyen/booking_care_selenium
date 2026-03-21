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
import pages.LoginPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Course_05_Register_Multiple_Courses_Authenticated_User_Success extends BaseTest {

    private static final String ACCOUNT = "thanhthuy01";
    private static final String PASSWORD = "Admin@123456";

    private static final List<String> COURSE_POOL = new ArrayList<>(Arrays.asList(
            "000", "000123456", "01230123", "09876788", "100999", "1009991", "10099922", "100999999","1111111111", "111111111111"
    ));

    private static final Random RAND = new Random();

    public static synchronized String getRandomUniqueCourseId() {
        if (COURSE_POOL.isEmpty()) throw new RuntimeException("LỖI: Hết ID!");
        return COURSE_POOL.remove(RAND.nextInt(COURSE_POOL.size()));
    }

    private void login(WebDriver driver) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLoginLink();
        loginPage.login(ACCOUNT, PASSWORD);
    }

    @Test(description = "DKKH_05 - Register Multiple Courses Authenticated User Success", groups = {"smoke", "course"})
    public void registerMultipleCoursesAuthenticatedUserSuccessfully() {
        WebDriver driver = DriverFactory.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // STEP 1: Login (Chỉ cần login 1 lần duy nhất)
        login(driver);

        // Danh sách lưu tên các khóa học để đối soát My Account ở bước cuối (nếu cần)
        List<String> registeredCourseNames = new ArrayList<>();

        // THỰC HIỆN ĐĂNG KÝ 2 KHÓA HỌC KHÁC NHAU
        for (int i = 1; i <= 3; i++) {
            System.out.println("\n--- Bắt đầu quy trình đăng ký khóa học lần " + i + " ---");

            // STEP 2: Click Menu để vào Danh sách khóa học
            By byMenuCourse = By.xpath("//ul[@class='menuHeader']//a[contains(text(),'Khóa học')]");
            // Sử dụng JS click cho Menu để tránh tuyệt đối việc bị các overlay li ti chặn
            WebElement menuCourse = wait.until(ExpectedConditions.elementToBeClickable(byMenuCourse));
            js.executeScript("arguments[0].click();", menuCourse);
            System.out.println("Đã click vào Menu Danh sách khóa học.");

            // STEP 3: Lấy ID ngẫu nhiên mới và click vào khóa học trong danh sách
            String targetCourseId = getRandomUniqueCourseId();
            By byCourseInList = By.xpath("//a[@class='cardGlobal' and contains(@href, '/chitiet/" + targetCourseId + "')]");

            WebElement courseItem = wait.until(ExpectedConditions.presenceOfElementLocated(byCourseInList));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", courseItem);
            courseItem.click();
            System.out.println("Đã tìm thấy và click vào khóa học ID: " + targetCourseId);

            // STEP 4: Lấy tên khóa học tại trang Chi tiết
            By byDetailTitle = By.xpath("//h4[@class='titleDetailCourse']");
            String courseName = wait.until(ExpectedConditions.visibilityOfElementLocated(byDetailTitle)).getText().trim();
            registeredCourseNames.add(courseName);
            System.out.println("Tên khóa học mục tiêu: " + courseName);

            // STEP 5: Click Đăng ký
            By byBtnRegister = By.xpath("//button[contains(normalize-space(),'Đăng ký')]");
            WebElement btnRegister = wait.until(ExpectedConditions.presenceOfElementLocated(byBtnRegister));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnRegister);

            // Đợi nút sẵn sàng và Click
            wait.until(ExpectedConditions.elementToBeClickable(btnRegister)).click();
            System.out.println("Đã click vào nút Đăng ký khóa học.");

            // QUAN TRỌNG: Chờ lớp phủ (Overlay) biến mất hoàn toàn trước khi lặp lại lần sau
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("swal-overlay")));
            System.out.println("Hoàn tất đăng ký khóa " + i);
        }

        // Sau khi kết thúc vòng lặp, bạn có thể thêm bước đi tới My Account để verify tại đây
        System.out.println("\n--- Tất cả khóa học đã đăng ký: " + registeredCourseNames);

        // --- STEP 7: Go to my account page
        driver.get("https://demo2.cybersoft.edu.vn/thongtincanhan");

        // Click the Courses tab
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Khóa học']"))).click();
        System.out.println("Đã click vào Tab Khóa học trong My Account.");

        // Verify the course name in the My Account list.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("myCourseItem")));
        List<WebElement> enrolledElements = driver.findElements(By.xpath("//div[@class='myCourseItem']//h6"));

        // Chuyển danh sách WebElement thành danh sách String để dễ so sánh
        List<String> actualEnrolledNames = enrolledElements.stream()
                .map(e -> e.getText().trim().toLowerCase())
                .collect(java.util.stream.Collectors.toList());

        // Kiểm tra xem TẤT CẢ các khóa đã đăng ký có nằm trong My Account không
        boolean allMatched = registeredCourseNames.stream()
                .allMatch(expectedName -> actualEnrolledNames.contains(expectedName.toLowerCase()));

        if (allMatched) {
            System.out.println("PASS: Tất cả khóa học " + registeredCourseNames + " đều tìm thấy trong My Account.");
        } else {
            System.out.println("FAIL: Có khóa học trong danh sách " + registeredCourseNames + " bị thiếu hoặc sai tên.");
            System.out.println("Danh sách thực tế trong Account: " + actualEnrolledNames);
        }

        Assert.assertTrue(allMatched, "Lỗi: Danh sách khóa học không khớp! Mong đợi: " + registeredCourseNames + " | Thực tế: " + actualEnrolledNames);

    }
}
