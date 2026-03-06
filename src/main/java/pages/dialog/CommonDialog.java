package pages.dialog;

import base.BasePage;
import constants.WaitTimeOut;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CommonDialog extends BasePage {


//    private By byLblMsgText = By.id("swal2-title");

    public CommonDialog(WebDriver driver) {
        super(driver);
    }
//
//    public String getTextMessage() {
//        return getText(byLblMsgText);
//    }
//
//    public void waitDialogDisappear() {
//        waitForInvisibilityOfElementLocated(byLblMsgText, WaitTimeOut.DEFAULT_TIMEOUT);
//    }

    private By getRegisterCourseMessageLocator(String message) {
        return By.xpath("//div[@class='swal-title' and text()='" + message + "']");
    }

    public String getTextMessageRegisterCourse(String message) {
        return getText(getRegisterCourseMessageLocator(message));
    }

    public void waitDialogRegisterCourseDisappear(String message) {
        waitForInvisibilityOfElementLocated(
                getRegisterCourseMessageLocator(message),
                WaitTimeOut.DEFAULT_TIMEOUT
        );
    }
}
