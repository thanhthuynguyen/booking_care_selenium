package testcases.course;

import base.BaseTest;
import drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.CourseDetailPage;
import pages.CourseListingPage;
import pages.LoginPage;

public class CourseRegistrationTest extends BaseTest {

    private static final String ACCOUNT = "thanhthuy01";
    private static final String PASSWORD = "Admin@123456";

    private void login(WebDriver driver) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLoginLink();
        loginPage.login(ACCOUNT, PASSWORD);
    }

    private CourseListingPage openCourseListing(WebDriver driver) {

        CourseListingPage courseListingPage = new CourseListingPage(driver);
        courseListingPage.openCoursePage();

        return courseListingPage;
    }

    private CourseDetailPage openCourseDetail(WebDriver driver) {

        CourseListingPage courseListingPage = openCourseListing(driver);
        courseListingPage.clickCourseItem();

        return new CourseDetailPage(driver);
    }

    @Test(description = "Verify user can successfully register a course when the course has not been registered before")
    public void registerCourseSuccessfully() {

        WebDriver driver = DriverFactory.getDriver();

        // Step 1: Login
        login(driver);

        // Step 2: Open course detail
        CourseDetailPage courseDetailPage = openCourseDetail(driver);

        // Step 3: Register course
        courseDetailPage.clickbtnRegisterCourse("Đăng kí thành công");
    }

    @Test(description = "Verify user cannot register a course when the course has been registered before")
    public void registerDuplicateCourseShouldShowError() {

        WebDriver driver = DriverFactory.getDriver();

        // Step 1: Login
        login(driver);

        // Step 2: Open course detail
        CourseDetailPage courseDetailPage = openCourseDetail(driver);

        // Step 3: Register course
        courseDetailPage.clickbtnRegisterCourse("Đã đăng ký khóa học này rồi!");
    }

}