package testcases.search;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SearchPage;

public class Search_Empty_KeyWord extends BaseTest {
    @Test
    public void TC04_Search_With_Empty_Keyword() throws InterruptedException {
        String keyword = "";

        SearchPage searchPage = new SearchPage(DriverFactory.getDriver());

        searchPage.search(keyword);

        String currentUrl = DriverFactory.getDriver().getCurrentUrl();

        System.out.println("Search keyword rỗng " + currentUrl);

        Assert.assertFalse(currentUrl.contains("timkiem"),"Keyword rỗng nhưng vẫn chuyển sang trang search");
    }
}
