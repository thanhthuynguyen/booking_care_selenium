package testcases.course;

import base.BaseTest;
import drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CourseDetailPage;
import pages.CourseListingPage;
import pages.LoginPage;

public class CourseRegistrationMultipleTest extends BaseTest {

    private LoginPage loginPage;
    private CourseListingPage courseListingPage;
    private CourseDetailPage courseDetailPage;

    @BeforeMethod
    public void setUp() {
        WebDriver driver = DriverFactory.getDriver();
        loginPage = new LoginPage(driver);
        courseListingPage = new CourseListingPage(driver);
        courseDetailPage = new CourseDetailPage(driver);
    }

    @Test(description = "DKKH_03: Register multiple courses in one session",
          groups = {"regression", "course", "smoke"})
    public void DKKH_03_registerMultipleCourses() {
        // Login
        loginPage.open();
        loginPage.login("thanhthuy01", "Admin@123456");

        // Course 1
        courseListingPage.openCoursePage();
        courseListingPage.clickCourseItem(0); // Assuming this clicks the first course
        courseDetailPage.clickbtnRegisterCourse("Đăng kí thành công");

        // Course 2
        courseListingPage.openCoursePage();
        courseListingPage.clickCourseItem(1); // Assuming this clicks the second course
        courseDetailPage.clickbtnRegisterCourse("Đăng kí thành công");
    }

    @Test(description = "DKKH_06: Multiple accounts register the same course",
          groups = {"regression", "course"})
    public void DKKH_06_multipleUsersRegisterSameCourse() {
        // User 1 registers
        loginPage.open();
        loginPage.login("thanhthuy01", "Admin@123456");
        courseListingPage.openCoursePage();
        courseListingPage.clickCourseItem();
        courseDetailPage.clickbtnRegisterCourse("Đăng kí thành công");

        // This part assumes a logout method exists
        // new ProfilePage(driver).logout();

        // User 2 registers for the same course
        loginPage.open();
        loginPage.login("testuser02", "password123"); // Dummy credentials
        courseListingPage.openCoursePage();
        courseListingPage.clickCourseItem();
        courseDetailPage.clickbtnRegisterCourse("Đăng kí thành công");
    }
}


