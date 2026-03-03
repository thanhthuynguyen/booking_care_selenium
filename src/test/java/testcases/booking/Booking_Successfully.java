package testcases.booking;

import base.BaseTest;
import drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BookingPage;
import pages.LoginPage;

public class Booking_Successfully extends BaseTest {

    private BookingPage bookingPage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setupBookingTest() {

        WebDriver driver = DriverFactory.getDriver();
        bookingPage = new BookingPage(driver);
        bookingPage.clickBookingNavigate();
        loginPage = new LoginPage(driver);
        loginPage.login("testdemo6", "123456");
        bookingPage.clickBookingNavigate();
    }

    @Test
    public void Booking_Verify_Successfully() {

        System.out.println("Booking page loaded successfully!");

        //  booking test case
    }
}

