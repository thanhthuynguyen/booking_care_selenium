package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class SearchPageCopy extends CommonPage {
    private By byTxtSearchInput = By.cssSelector("input.searchForm");

    public SearchPageCopy(WebDriver driver) {
        super(driver);
    }

    // Search key
    public void enterSpecialText(String account) {
        sendKeys(byTxtSearchInput, account);
    }

    public void pressEnterToSearch() {
        driver.findElement(byTxtSearchInput).sendKeys(Keys.ENTER);
    }

    public ResultPage search(String keyword) {
        sendKeys(byTxtSearchInput, keyword, 200);
        driver.findElement(byTxtSearchInput).sendKeys(Keys.ENTER);
        return new ResultPage(driver);
    }
}
