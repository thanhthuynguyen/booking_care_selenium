package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.dialog.CommonDialog;

import java.time.Duration;

public class CourseDetailPage extends CommonPage {

    private By byBtnRegisterCourse = By.xpath("//button[text()='Đăng ký']");
    private By courseName = By.xpath("//h2[@class='course-detail-info__title']");

    public CourseDetailPage(WebDriver driver) {
        super(driver);
    }

    public void clickbtnRegisterCourse(String expectedMessage) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Click register
        WebElement btnRegisterCourse =
                wait.until(ExpectedConditions.elementToBeClickable(byBtnRegisterCourse));
        btnRegisterCourse.click();

        // Verify message
        CommonDialog dialog = new CommonDialog(driver);
        String actualMessage = dialog.getTextMessageRegisterCourse(expectedMessage);

        Assert.assertEquals(actualMessage, expectedMessage, "Dialog message is not correct!");

        // Step 4: Wait dialog disappear
        dialog.waitDialogRegisterCourseDisappear(expectedMessage);

    }

    public void clickRegisterButtonAndExpectRedirect() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement btnRegisterCourse = wait.until(ExpectedConditions.elementToBeClickable(byBtnRegisterCourse));
        btnRegisterCourse.click();
    }

    public String getCourseName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(courseName)).getText();
    }
}