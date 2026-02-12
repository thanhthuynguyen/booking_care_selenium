package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import pages.components.TopBarNavigation;

public class CommonPage extends BasePage {

    private TopBarNavigation topBarNavigation;

    public CommonPage(WebDriver driver) {
        super(driver);
        this.topBarNavigation = new TopBarNavigation(driver);
    }

    public TopBarNavigation getTopBarNavigation() {
        return this.topBarNavigation;
    }
}
