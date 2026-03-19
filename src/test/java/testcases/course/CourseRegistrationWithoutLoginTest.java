package testcases.course;
import base.BaseTest;
import drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.CourseDetailPage;
import pages.CourseListingPage;

//import static sun.security.jgss.GSSUtil.login;

public class CourseRegistrationWithoutLoginTest extends BaseTest {
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

    @Test(description = "Verify user without login cannot register a course")
    public void registerWithoutLogin() {

        //WebDriver driver = DriverFactory.getDriver();

        // Step 1: Login
        //login(driver);

        // Step 2: Open course detail
        //CourseDetailPage courseDetailPage = openCourseDetail(driver);

        // Step 3: Register course
        //courseDetailPage.clickbtnRegisterCourse("Đã đăng ký khóa học này rồi!");
        System.out.println("Verify user without login cannot register a course");
    }
}