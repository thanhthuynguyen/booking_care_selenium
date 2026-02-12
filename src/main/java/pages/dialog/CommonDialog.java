package pages.dialog;

import base.BasePage;
import constants.WaitTimeOut;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CommonDialog  extends BasePage {


    private By byLblMsgText = By.id("swal2-title");

    public CommonDialog(WebDriver driver) {
        super(driver);
    }

    public String getTextMessage() {
        return getText(byLblMsgText);
    }

    public void waitDialogDisappear() {
        waitForInvisibilityOfElementLocated(byLblMsgText, WaitTimeOut.DEFAULT_TIMEOUT);
    }
}
