package testcases.register;

import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegisterPage;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Register01_Verify_Register_Successfully_backup {


        @Test(description = "Verify that user can register successfully with valid information")
        public void Register01_Register_Successfully() {


            UUID uuid = UUID.randomUUID();
            //System.out.println(uuid);
            String account = uuid.toString();
            String email = account + "@example.com";

            System.out.println("account: " + account);
            System.out.println("email: " + email);

            WebDriver driver = null;

            try {

                ChromeOptions options = new ChromeOptions();
                // Disable automation bar
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                options.setExperimentalOption("useAutomationExtension", false);


                // Disable password save dialog
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                options.setExperimentalOption("prefs", prefs);


                driver = new ChromeDriver(options);
                driver.manage().window().maximize();


                // Step 1: Go to: https://demo1.cybersoft.edu.vn/
                System.out.println("Step1");
                driver.get("https://demo1.cybersoft.edu.vn/");

                // Cách 1 dùng: WebDriverWait
                //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                // Cách 2 dùng: FluentWait
                // viết kiểu xuống dòng . hết câu mới ; gọi là: chaining
                Wait <WebDriver> wait= new FluentWait<>(driver)
                        .withTimeout(Duration.ofSeconds(30))
                        .pollingEvery(Duration.ofMillis(500))
                        .ignoring(NotFoundException.class);


                // Step 2: Click "Đăng ký" link on top right
                System.out.println("Step2");
                By byLnkRegister = By.xpath("//a[h3[text()='Đăng Ký']]");
                WebElement lnkRegister = wait.until(ExpectedConditions.visibilityOfElementLocated(byLnkRegister));
                lnkRegister.click();



                // step 3: Enter account name
                System.out.println("Step3");
                By byTxtAccount = By.id("taiKhoan");
                WebElement txtAccount = wait.until(ExpectedConditions.visibilityOfElementLocated(byTxtAccount));
                txtAccount.sendKeys(account);


                // Step 4: Enter password
                System.out.println("Step4");
                By byTxtPassword = By.id("matKhau");
                WebElement txtPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(byTxtPassword));
                txtPassword.sendKeys("123456");


                // Step5: Enter confirm password
                System.out.println("Step5");
                By byTxtConfirmPassword = By.id("confirmPassWord");
                WebElement txtConfirmPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(byTxtConfirmPassword));
                txtConfirmPassword.sendKeys("123456");


                // Step 6: Enter Full name
                System.out.println("Step6");
                String fullname = "Join";
                By byTxtFullName = By.id("hoTen");
                WebElement txtFullName = wait.until(ExpectedConditions.visibilityOfElementLocated(byTxtFullName));
                txtFullName.sendKeys(fullname);


                //Thread.sleep(3000);

                // Step 7: Enter email
                System.out.println("Step7");
                By byTxtEmail = By.id("email");
                WebElement txtEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(byTxtEmail));
                txtEmail.sendKeys(email);


                // Step 8; Click "Đăng ký"
                System.out.println("Step8");
                By byBtnRegister = By.xpath("//button[span[text()='Đăng ký']]");
                WebElement btnRegister = wait.until(ExpectedConditions.visibilityOfElementLocated(byBtnRegister));
                btnRegister.click();

                // Từ step 9 trở đi là test theo test case:
                //Step 9: Verify user registers successfully
                System.out.println("Step 9: Verify user registers successfully");
                //VP1: Check 'Dang Ky Thanh Cong' message display
                System.out.println("VP1: Check 'Dang Ky Thanh Cong' message display");
                By byLblRegisterMsg = By.id("swal2-title");
                WebElement lblRegisterMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(byLblRegisterMsg));
                String recordedMsg = lblRegisterMsg.getText();
                Assert.assertEquals(recordedMsg, "Đăng ký thành công", "Incorrect register message !");
                // wait cho dialog biến mất trước khi làm step tiếp theo: invisibilityOfElementLocated
                wait.until(ExpectedConditions.invisibilityOfElementLocated(byLblRegisterMsg));

                //VP2: Check login successfully with new created account
                System.out.println("VP2: Check login successfully with new created account");
                //9.1. Click 'Dang Nhap' link
                By byLnkLogin = By.xpath("//a[h3[text()='Đăng Nhập']]");
                WebElement lnkLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(byLnkLogin));
                lnkLogin.click();

                // 2. Enter account
                System.out.println("Step3");
                By byTxtAccountLogin = By.id("taiKhoan");
                WebElement txtAccountLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(byTxtAccountLogin));
                txtAccountLogin.sendKeys(account);
                // 3. Enter password

                By byTxtPasswordLogin = By.id("matKhau");
                WebElement txtPasswordLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(byTxtPasswordLogin));
                txtPasswordLogin.sendKeys("123456");

                // 4. Click dang nhap button
                By byBtnLogin = By.xpath("//button[span[text()='Đăng nhập']]");
                WebElement btnLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(byBtnLogin));
                btnLogin.click();
                System.out.println("Login success!!!");

                // VP3: check "Dang nhap thanh cong" message display
                //System.out.println("Step9: Verify user register successfully");
                By byLblLoginMsg = By.id("swal2-title");
                WebElement lblLoginMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(byLblLoginMsg));
                String recorderLoginMsg = lblLoginMsg.getText();
                Assert.assertEquals(recorderLoginMsg, "Đăng nhập thành công", "Incorrect register message !");
                // wait cho dialog biến mất trước khi làm step tiếp theo: invisibilityOfElementLocated
                wait.until(ExpectedConditions.invisibilityOfElementLocated(byLblLoginMsg));

                // VP4: Check "Dang xuat" link display
                By byLnkLogout = By.xpath("//a[h3[text()='Đăng xuất']]");
                WebElement lnkLogout = wait.until(ExpectedConditions.visibilityOfElementLocated(byLnkLogout));
                Assert.assertTrue(lnkLogout.isDisplayed(), "Log out link not display");
                // VP5
                By byLblUserProfile = By.xpath("//a/h3[text()='" + fullname + "']");
                WebElement lblUserProfile = wait.until(ExpectedConditions.visibilityOfElementLocated(byLblUserProfile));
                Assert.assertTrue(lnkLogout.isDisplayed(), "User profile link not display");


                Thread.sleep(3000);

            } catch (Exception e) {
                // Capture screenshot luc failed
                // Log loi
                // Clean up
                throw new RuntimeException(e);
            } finally {
                driver.quit();
            }

        }
}
