package testcases.course;

import base.BaseTest;
import drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.CourseDetailPage;
import pages.CourseListingPage;
import pages.LoginPage;
import report.ExtentReportManager;

public class CourseRegistrationTest extends BaseTest {

    @Test
    public void CourseRegistrationTestSuccessfullyFirstTime() {

        String account = "thanhthuy01";
        String password = "Admin@123456";

        WebDriver driver = DriverFactory.getDriver();

        LOG.info("===== START TEST: CourseRegistrationTestSuccessfullyFirstTime =====");
        ExtentReportManager.info("Start course registration test");

        // Step 1: Login
        LOG.info("Step 1: Login with account: " + account);
        ExtentReportManager.info("Step 1: Login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLoginLink();
        loginPage.login(account, password);

        // Step 2: Navigate to course page
        LOG.info("Step 2: Navigate to course listing page");
        ExtentReportManager.info("Step 2: Open course listing");

        CourseListingPage courseListingPage = new CourseListingPage();
        courseListingPage.openCoursePage();

        // Step 3: Select course
        LOG.info("Step 3: Select course item");
        ExtentReportManager.info("Step 3: Click course");

        courseListingPage.clickCourseItem();

        // Step 4: Register course
        LOG.info("Step 4: Register course");
        ExtentReportManager.info("Step 4: Click register course");

        CourseDetailPage courseDetailPage = new CourseDetailPage(driver);
        courseDetailPage.clickbtnRegisterCourse("Đăng kí thành công");

        LOG.info("Test finished successfully");
        ExtentReportManager.pass("Course registration completed");

        LOG.info("===== END TEST =====");
    }
}