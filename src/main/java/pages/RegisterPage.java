package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage extends CommonPage {

    // Locator dựa trên thuộc tính 'name' từ HTML mới
    private By byTxtAccount = By.name("taiKhoan");
    private By byTxtFullName = By.name("hoTen");
    private By byTxtPassword = By.name("matKhau");
    private By byTxtEmail = By.name("email");
    private By byTxtPhone = By.name("soDT");
    private By bySelectGroup = By.name("maNhom");
    private By byBtnRegister = By.xpath("//div[contains(@class,'sign-up-container')]//button[text()='Đăng ký']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    // Nhập tài khoản
    public void enterAccount(String account) {
        sendKeys(byTxtAccount, account);
    }

    // Nhập họ tên
    public void enterFullName(String fullname) {
        sendKeys(byTxtFullName, fullname);
    }

    // Nhập mật khẩu
    public void enterPassword(String password) {
        sendKeys(byTxtPassword, password);
    }

    // Nhập lại mật khẩu
    public void enterConfirmPassword(String password) {
        sendKeys(byTxtPassword, password);
    }

    // Nhập Email
    public void enterEmail(String email) {
        sendKeys(byTxtEmail, email);
    }

    // Nhập Số điện thoại
    public void enterPhone(String phone) {
        sendKeys(byTxtPhone, phone);
    }

    /**
     * Chọn mã nhóm từ Dropdown <select>
     * @param groupCode Ví dụ: "GP01", "GP02"...
     */
    public void selectGroup(String groupCode) {
        // Sử dụng class Select của Selenium để thao tác với dropdown
        Select select = new Select(getWebElement(bySelectGroup));
        select.selectByValue(groupCode);
    }

    private WebElement getWebElement(By bySelectGroup) {
        return driver.findElement(bySelectGroup);
    }

    // Click nút Đăng ký
    public void clickRegister() {
        click(byBtnRegister);
    }

    /**
     * Hàm gộp để thực hiện đăng ký nhanh trong 1 dòng code
     */
    public void registerNewAccount(String account, String name, String pass, String email, String phone, String group) {
        enterAccount(account);
        enterFullName(name);
        enterPassword(pass);
        enterEmail(email);
        enterPhone(phone);
        selectGroup(group);
        clickRegister();
    }

}
