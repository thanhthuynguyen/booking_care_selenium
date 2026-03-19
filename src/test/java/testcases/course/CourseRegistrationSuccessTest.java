package testcases.course;

import base.BaseTest;
import drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.CourseDetailPage;
import pages.LoginPage;

public class CourseRegistrationSuccessTest extends BaseTest {

    private static final String ACCOUNT = "thanhthuy01"; // account chưa đăng ký
    private static final String PASSWORD = "Admin@123456";
    private static final String COURSE_ID = "01230123";

    private void login(WebDriver driver) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLoginLink();
        loginPage.login(ACCOUNT, PASSWORD);
    }

    @Test(description = "DKKH_02 - Register course successfully", groups = {"smoke","course"})
    public void registerCourseSuccessfully() {

        WebDriver driver = DriverFactory.getDriver();

        // Step 1: Login
        login(driver);

        // Step 2: Open course detail
        CourseDetailPage page = new CourseDetailPage(driver);
        page.openCourseDetail(COURSE_ID);

        // Step 3: Register
        page.clickbtnRegisterCourse("Đăng kí thành công");
    }
}