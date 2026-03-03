package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchPage extends CommonPage {
    private By byTxtSearchInput = By.xpath("//input[@placeholder='Tìm kiếm...']");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    // Search key
    public void enterSpecialText(String account) {
        sendKeys(byTxtSearchInput, account);
    }

}
