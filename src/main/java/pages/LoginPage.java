package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage extends CommonPage {


    private By byLnkLogin = By.xpath("//button[@class='btnGlobal'][a[@href='/login']]");
    private By byTxtAccountLogin = By.xpath("//div[contains(@class, 'sign-in-container')]//input[@name='taiKhoan']");
    private By byTxtPasswordLogin = By.xpath("//div[contains(@class, 'sign-in-container')]//input[@name='matKhau']");
    private By byBtnLogin = By.xpath("//form[@class='formLoginUser']//button[text()='Đăng nhập']");
    //    private By byLblLoginMsg = By.id("swal2-title");
    private By byLnkLogout = By.xpath("//a[h3[text()='Đăng xuất']]");
    //private By byLblUserProfile = By.xpath("//a/h3[text()='" + fullname + "']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void clickLoginLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement lnkLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(byLnkLogin));
        lnkLogin.click();
    }

    public void enterAcount(String account) {
        sendKeys(byTxtAccountLogin, account);
    }

    public void enterPassword(String password) {
        sendKeys(byTxtPasswordLogin, password);
    }

    public void clickLogin() {
        click(byBtnLogin);
    }

    public void login(String username, String password) {
        enterAcount(username);
        enterPassword(password);
        clickLogin();
    }

//    public void getLogoutLink() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
//        WebElement lnkLogout = wait.until(ExpectedConditions.visibilityOfElementLocated(byLnkLogout));
//        Assert.assertTrue(lnkLogout.isDisplayed(), "Log out link not display");
//    }

}
