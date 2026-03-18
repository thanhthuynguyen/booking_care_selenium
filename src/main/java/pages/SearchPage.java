package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import constants.WaitTimeOut;

import java.time.Duration;

public class SearchPage extends CommonPage {
    // locator mới theo class
    private By txtSearch = By.xpath("//input[contains(@class,'searchForm')]");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public ResultPage search(String keyword) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WaitTimeOut.DEFAULT_TIMEOUT));

        wait.until(ExpectedConditions.visibilityOfElementLocated(txtSearch));

        sendKeys(txtSearch, keyword);
        Thread.sleep(2000);

        driver.findElement(txtSearch).sendKeys(Keys.ENTER);
        Thread.sleep(2000);

        return new ResultPage(driver);
    }
}
