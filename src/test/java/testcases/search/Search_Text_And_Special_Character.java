package testcases.search;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ResultPage;
import pages.SearchPage;

public class Search_Text_And_Special_Character extends BaseTest {
    @Test
    public void TC05_Search_With_Text_And_Special_Character() throws InterruptedException {

        String keyword = "test@#$";

        SearchPage searchPage = new SearchPage(DriverFactory.getDriver());

        ResultPage resultPage = searchPage.search(keyword);

        String currentUrl = DriverFactory.getDriver().getCurrentUrl();

        System.out.println("Kết quả tìm kiếm: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("timkiem"),"Không chuyển sang trang kết quả search");

    }
}