package pages.components;

import base.BasePage;
import constants.WaitTimeOut;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

public class TopBarNavigation extends BasePage {

    private By byLnkRegister = By.xpath("//a[h3[text()='Đăng Ký']]");
    private By byLnkLogin = By.xpath("//a[h3[text()='Đăng Nhập']]");
    private By byLnkLogout = By.xpath("//a[h3[text()='Đăng xuất']]");
    public TopBarNavigation(WebDriver driver) {
        super(driver);
    }

    public void navigateRegisterPage() {
        click(byLnkRegister);
    }

    public void navigateLoginPage() {
        click(byLnkLogin);
    }

    public boolean isLogoutLinkDisplayed() {
        try {
            return waitForVisibilityOfElementLocated(byLnkLogout, WaitTimeOut.MEDIUM_TIMEOUT).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
