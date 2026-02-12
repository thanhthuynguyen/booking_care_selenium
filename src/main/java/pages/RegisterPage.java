package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class RegisterPage extends CommonPage {



    private By byTxtAccount = By.id("taiKhoan");
    private By byTxtPassword = By.id("matKhau");
    private By byTxtConfirmPassword = By.id("confirmPassWord");
    private By byTxtFullName = By.id("hoTen");
    private By byTxtEmail = By.id("email");
    private By byBtnRegister = By.xpath("//button[span[text()='Đăng ký']]");

    //By byLblRegisterMsg = By.id("swal2-title");


    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    // account
    public void enterAccount(String account) {
        senKeys(byTxtAccount, account);
    }

    // password
    public void enterPassword(String password) {
        senKeys(byTxtPassword, password);
    }

    // ConfirmConfirmPassword
    public void enterConfirmPassword(String password) {
        senKeys(byTxtConfirmPassword, password);
    }

    //FullName
    public void enterFullName(String fullname) {
        senKeys(byTxtFullName, fullname);
    }

    //Email
    public void enterEmail(String email) {
        senKeys(byTxtEmail, email);
    }

    // Register
    public void clickRegister() {
        click(byBtnRegister);
    }

}
