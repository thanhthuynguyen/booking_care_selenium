package base;

import constants.WaitTimeOut;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForVisibilityOfElementLocated(By locator, long timeOutInSec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSec));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForInvisibilityOfElementLocated(By locator, long timeOutInSec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSec));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator, long timeOutInSec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSec));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForPresenceOfElementLocated(By locator, long timeOutInSec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSec));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * SendKeys action
     * @param locator - need By locator
     * @param value
     * @param timeOutInSec - time out value in seconds
     */
    public void senKeys(By locator, String value, long timeOutInSec)  {
        WebElement element = waitForVisibilityOfElementLocated(locator, timeOutInSec);
        element.sendKeys(value);
    }

    public void senKeys(By locator, String value)  {
        senKeys(locator, value, WaitTimeOut.DEFAULT_TIMEOUT);
    }

    public void click(By locator, long timeOutInSec) {
        WebElement element = waitForClickable(locator, timeOutInSec);
        element.click();
    }

    public void click(By locator) {
        click(locator, WaitTimeOut.DEFAULT_TIMEOUT);
    }

    /**
     * If isDisplayed is false, action will not wait for visibility
     * Else action will wait for element is presented in DOM before clicking
     * @param locator
     * @param isDisplayed
     * @param timeOutInSec
     */
    public void click(By locator, boolean isDisplayed, long timeOutInSec) {
        if(isDisplayed) {
            click(locator, timeOutInSec);
        } else {
            WebElement element = waitForPresenceOfElementLocated(locator, timeOutInSec);
            element.click();
        }
    }

    public void click(By locator, boolean isDisplayed) {
        click(locator, isDisplayed, WaitTimeOut.DEFAULT_TIMEOUT);
    }

    public String getText(By locator, long timeOutInSec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSec));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element.getText();
    }

    public String getText(By locator) {
        return getText(locator, WaitTimeOut.DEFAULT_TIMEOUT);
    }

}
