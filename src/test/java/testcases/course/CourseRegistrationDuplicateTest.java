package testcases.course;

import base.BaseTest;
import drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.CourseDetailPage;
import pages.LoginPage;

public class CourseRegistrationDuplicateTest extends BaseTest {

    private static final String ACCOUNT = "thanhthuy01"; // account đã đăng ký
    private static final String PASSWORD = "Admin@123456";
    private static final String COURSE_ID = "000123456";

    private void login(WebDriver driver) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLoginLink();
        loginPage.login(ACCOUNT, PASSWORD);
    }

    @Test(description = "DKKH_04 - Register duplicate course", groups = {"smoke","course"})
    public void registerDuplicateCourseShouldShowError() {

        WebDriver driver = DriverFactory.getDriver();

        // Step 1: Login
        login(driver);

        // Step 2: Open course detail
        CourseDetailPage page = new CourseDetailPage(driver);
        page.openCourseDetail(COURSE_ID);

        // Step 3: Register
        page.clickbtnRegisterCourse("Đã đăng ký khóa học này rồi!");
    }
}