package testcases.search;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ResultPage;
import pages.SearchPage;

public class Search_Invalid_KeyWord extends BaseTest {
    @Test
    public void TC02_Search_With_Invalid_Keyword() throws InterruptedException {

        String keyword = "mncsd123";

        SearchPage searchPage = new SearchPage(DriverFactory.getDriver());

        ResultPage resultPage = searchPage.search(keyword);

        Thread.sleep(2000);

        String resultText = resultPage.getResultCountText();
        System.out.println("Search keyword không tồn tại: " + resultText);

        Assert.assertTrue(resultPage.isNoResult(), "kết quả là 0");
    }
}
