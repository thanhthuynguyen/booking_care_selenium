package testcases.register;

import base.BaseTest;
import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import pages.dialog.CommonDialog;

import java.time.Duration;
import java.util.UUID;

public class Register01_Verify_Register_Successfully extends BaseTest {


    @Test(description = "Verify that user can register successfully with valid information")
    public void Register01_Register_Successfully() {


        UUID uuid = UUID.randomUUID();
        //System.out.println(uuid);
        String account = uuid.toString();
        String email = account + "@example.com";

        System.out.println("account: " + account);
        System.out.println("email: " + email);

        WebDriver driver = DriverFactory.getDriver();

        HomePage homePage = new HomePage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        CommonDialog dialog = new CommonDialog(driver);

        // Step 1: Go to: https://demo1.cybersoft.edu.vn/
        System.out.println("Step1");
        driver.get("https://demo1.cybersoft.edu.vn/");

        // Cách 1 dùng: WebDriverWait
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Cách 2 dùng: FluentWait
        // viết kiểu xuống dòng . hết câu mới ; gọi là: chaining
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NotFoundException.class);


        // Step 2: Click "Đăng ký" link on top right
        System.out.println("Step2");
        registerPage.getTopBarNavigation().navigateRegisterPage();



        // step 3: Enter account name
        System.out.println("Step3");
        registerPage.enterAccount(account);


        // Step 4: Enter password
        System.out.println("Step4");
        registerPage.enterPassword("123456");


        // Step5: Enter confirm password
        System.out.println("Step5");
        registerPage.enterConfirmPassword("123456");


        // Step 6: Enter Full name
        System.out.println("Step6");
        String fullname = "Join";
        registerPage.enterFullName(fullname);

        // Step 7: Enter email
        System.out.println("Step7");
        registerPage.enterEmail(email);

        // Step 8; Click "Đăng ký"
        System.out.println("Step8");
        registerPage.clickRegister();

    }
}
