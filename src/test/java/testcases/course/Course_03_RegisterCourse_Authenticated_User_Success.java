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
import pages.LoginPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Course_03_RegisterCourse_Authenticated_User_Success extends BaseTest {
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

    @Test(description = "DKKH_03 - Luồng đăng ký chuẩn cho User đã Login", groups = {"smoke","course"})
    public void registerCourseAuthenticatedUserSuccessfully() {
        WebDriver driver = DriverFactory.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        String targetCourseId = getRandomUniqueCourseId();

        // STEP 1: Login
        login(driver);

        // STEP 2: Click Menu to access the Course List page.
        By byMenuCourse = By.xpath("//ul[@class='menuHeader']//a[contains(text(),'Khóa học')]");
        WebElement menuCourse = wait.until(ExpectedConditions.visibilityOfElementLocated(byMenuCourse));
        menuCourse.click();
        System.out.println("Đã click vào Menu Danh sách khóa học.");

        // STEP 3: Find the course in the list and click on it to go to the Details page.
        By byCourseInList = By.xpath("//a[@class='cardGlobal' and @href='/chitiet/" + targetCourseId + "']");
        // Scroll down to the course (if the list is long) and click.
        WebElement courseItem = wait.until(ExpectedConditions.presenceOfElementLocated(byCourseInList));
        courseItem.click();
        System.out.println("Đã tìm thấy và click vào khóa học ID: " + targetCourseId);

        // STEP 4: Get the course name from the Details page.
        By byDetailTitle = By.xpath("//h4[@class='titleDetailCourse']");
        String expectedCourseName = wait.until(ExpectedConditions.visibilityOfElementLocated(byDetailTitle)).getText().trim();
        System.out.println("Tên khóa học mục tiêu: " + expectedCourseName);

        // STEP 5: Click to register for the course.
        By byBtnRegister = By.xpath("//button[contains(normalize-space(),'Đăng ký')]");
        WebElement btnRegister = wait.until(ExpectedConditions.elementToBeClickable(byBtnRegister));
        // Scroll so that the button is in the center of the screen instead of near the top edge.
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btnRegister);

        btnRegister.click();
        System.out.println("Đã click vào nút Đăng ký khóa học.");

        // STEP 6: Verify message
        By byPopup = By.xpath("//div[@class='swal-title']");
        WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(byPopup));
        Assert.assertEquals(msg.getText(), "Đăng kí thành công");
        System.out.println("Hiển thị popup thông báo đăng ký thành công.");

        // --- STEP 7: Go to my account page
        driver.get("https://demo2.cybersoft.edu.vn/thongtincanhan");

        // Click the Courses tab
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Khóa học']"))).click();
        System.out.println("Đã click vào Tab Khóa học trong My Account.");

        // Verify the course name in the My Account list.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("myCourseItem")));
        List<WebElement> enrolledNames = driver.findElements(By.xpath("//div[@class='myCourseItem']//h6"));

        boolean isMatch = enrolledNames.stream()
                .anyMatch(e -> e.getText().trim().equalsIgnoreCase(expectedCourseName));

        if (isMatch) {
            System.out.println("PASS: Tìm thấy khóa học '" + expectedCourseName + "' trong My Account.");
        } else {
            System.out.println("FAIL: Không tìm thấy khóa học '" + expectedCourseName + "'.");
        }

        Assert.assertTrue(isMatch, "Khóa học: " + expectedCourseName + " không tồn tại trong My Account!");
    }
}