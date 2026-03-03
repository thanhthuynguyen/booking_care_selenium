package testcases.booking;

import base.BaseTest;
import drivers.DriverFactory;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import report.ExtentReportManager;

import java.time.Duration;

public class Booking_Successfully extends BaseTest {

    @Test(description = "Verify that user can login successfully with valid account")
    public void Booking_Successfully() {

        String account = "testdemo6";
        String password = "123456";

        WebDriver driver = DriverFactory.getDriver();

        HomePage homePage = new HomePage(driver);

        //Step 1: Go to https://demo6.cybersoft.edu.vn/
        LOG.info("Step 1: Go to https://demo6.cybersoft.edu.vn/");
        ExtentReportManager.info("Step 1: Go to https://demo6.cybersoft.edu.vn/");
        driver.get("https://demo6.cybersoft.edu.vn/");

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NotFoundException.class);

        //Step 2: Click 'Đặt lịch khám' link on the navigation bar
        LOG.info("Step 2: Click 'Đặt lịch khám' link on the navigation bar");
        ExtentReportManager.info("Step 2: Click 'Đặt lịch khám' link on the navigation bar");
        homePage.getTopBarNavigation().navigateBookingPage();

        //Step 3: Login
        LoginPage loginPage = new LoginPage(driver);;
        loginPage.login(account, password);

    }
}
