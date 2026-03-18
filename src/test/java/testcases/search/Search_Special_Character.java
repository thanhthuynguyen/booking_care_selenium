package testcases.search;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ResultPage;
import pages.SearchPage;

public class Search_Special_Character extends BaseTest {
    @Test
    public void TC03_Search_With_Special_Character() throws InterruptedException {
        String keyword = "@@@###";

        SearchPage searchPage = new SearchPage(DriverFactory.getDriver());

        ResultPage resultPage = searchPage.search(keyword);

        String resultText = resultPage.getResultCountText();

        System.out.println("Search ký tự đặc biệt: " + resultText);

        Assert.assertTrue(resultPage.isNoResult());
    }
}
