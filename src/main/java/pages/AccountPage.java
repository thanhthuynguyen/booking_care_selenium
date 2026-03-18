package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AccountPage extends BasePage {

    private final By accountPageLink = By.xpath("//a[@href='/thong-tin-ca-nhan']");
    private final String courseInAccountXpath = "//div[@class='course-item__title' and text()='%s']";

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public void openAccountPage() {
        wait.until(ExpectedConditions.elementToBeClickable(accountPageLink)).click();
    }

    public boolean isCourseDisplayed(String courseName) {
        By courseLocator = By.xpath(String.format(courseInAccountXpath, courseName));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(courseLocator)).isDisplayed();
    }
}

