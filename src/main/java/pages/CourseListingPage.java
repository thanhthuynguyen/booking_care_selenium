package pages;

import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CourseListingPage {

    WebDriver driver;
    WebDriverWait wait;

    public CourseListingPage(WebDriver driver) {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(15));
    }

    // locator
    private By courseCardTitle = By.cssSelector(".cardEffect");
    private By courseTitle = By.xpath("//a[@class='cardGlobal' and contains(@href, '000123456')]");

    // Step 1: Open course page
    public void openCoursePage() {
        driver.get("https://demo2.cybersoft.edu.vn/khoahoc");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(courseCardTitle)
        );
    }

    public void clickCourseItem() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement lnkCourse = wait.until(ExpectedConditions.visibilityOfElementLocated(courseTitle));
        lnkCourse.click();
    }

    public void clickCourseItem(int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(courseCardTitle)).get(index).click();
    }
}