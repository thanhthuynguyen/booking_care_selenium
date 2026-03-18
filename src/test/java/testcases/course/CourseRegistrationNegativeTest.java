package testcases.course;

import base.BaseTest;
import drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CourseDetailPage;
import pages.CourseListingPage;

public class CourseRegistrationNegativeTest extends BaseTest {

    @Test(description = "DKKH_01: Register course without login → should redirect to login page",
          groups = {"smoke", "regression", "course"})
    public void DKKH_01_registerWithoutLogin() {
        WebDriver driver = DriverFactory.getDriver();

        // Navigate to course listing page
        CourseListingPage courseListingPage = new CourseListingPage(driver);
        courseListingPage.openCoursePage();

        // Click on a course
        courseListingPage.clickCourseItem();

        // Attempt to register and verify redirect
        CourseDetailPage courseDetailPage = new CourseDetailPage(driver);
        courseDetailPage.clickRegisterButtonAndExpectRedirect();

        // Assert that the URL is the login page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl != null && currentUrl.contains("/login"), "User should be redirected to the login page.");
    }
}


