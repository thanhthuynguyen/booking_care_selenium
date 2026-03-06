package testcases.course;

import base.BaseTest;
import drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.CourseListingPage;
import pages.LoginPage;


public class CourseRegistrationTest extends BaseTest {
    @Test
    public void CourseRegistrationTestSuccessfully() {
        String account = "thanhthuy01";
        String password = "Admin@123456";

        LOG.info("Course registration test executed successfully!");
        WebDriver driver = DriverFactory.getDriver();
        // step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLoginLink();
        loginPage.login(account, password);

        // Step 2: Navigate to course page

        CourseListingPage courseListingPage = new CourseListingPage();
        courseListingPage.openCoursePage();
        courseListingPage.clickCourseItem();

        // Step 2: Select course
        //courseListingPage.selectCourse(String.valueOf(driver));

        // Step 3: Register course
        //courseDetailPage.clickRegisterButton();

        // Step 4: Verify success message
        //courseDetailPage.verifyRegistrationSuccess();

        LOG.info("Test finished");

    }
}
