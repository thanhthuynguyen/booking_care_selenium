package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookingPage extends CommonPage {

    private By byLnkBooking = By.xpath("//a[text()='Đặt lịch khám']");

    public BookingPage(WebDriver driver) {
        super(driver);
    }

    public void clickBookingLink() {
        click(byLnkBooking);
    }

    public void clickBookingNavigate() {
        clickBookingLink();
    }

}
