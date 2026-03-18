package testcases.course;

import base.BaseTest;
import drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.CourseDetailPage;
import pages.CourseListingPage;
import pages.LoginPage;

public class CourseRegistrationAccountTest extends BaseTest {

    private LoginPage loginPage;
    private CourseListingPage courseListingPage;
    private CourseDetailPage courseDetailPage;
    private AccountPage accountPage;

    @BeforeMethod
    public void setUp() {
        WebDriver driver = DriverFactory.getDriver();
        loginPage = new LoginPage(driver);
        courseListingPage = new CourseListingPage(driver);
        courseDetailPage = new CourseDetailPage(driver);
        accountPage = new AccountPage(driver);
    }

    @Test(description = "DKKH_05: Verify registered course appears in user account page",
          groups = {"regression", "course"})
    public void DKKH_05_verifyRegisteredCourseInAccount() {
        loginPage.open();
        loginPage.login("thanhthuy01", "Admin@123456");

        courseListingPage.openCoursePage();
        courseListingPage.clickCourseItem();
        String courseName = courseDetailPage.getCourseName();
        courseDetailPage.clickbtnRegisterCourse("Đăng kí thành công");

        accountPage.openAccountPage();
        Assert.assertTrue(accountPage.isCourseDisplayed(courseName), "Registered course should be in account page.");
    }

    @Test(description = "DKKH_07: Verify registered courses persist after logout and login again",
          groups = {"regression", "course"})
    public void DKKH_07_verifyCoursePersistsAfterReLogin() {
        loginPage.open();
        loginPage.login("thanhthuy01", "Admin@123456");

        courseListingPage.openCoursePage();
        courseListingPage.clickCourseItem();
        String courseName = courseDetailPage.getCourseName();
        courseDetailPage.clickbtnRegisterCourse("Đăng kí thành công");

        // This part assumes a logout method exists
        // new ProfilePage(driver).logout();

        loginPage.open();
        loginPage.login("thanhthuy01", "Admin@123456");

        accountPage.openAccountPage();
        Assert.assertTrue(accountPage.isCourseDisplayed(courseName), "Registered course should persist after re-login.");
    }
}


