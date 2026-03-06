package testcases.search;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ResultPage;
import pages.SearchPage;

public class SearchInvalidKeyWord extends BaseTest {
    @Test
    public void TC02_Search_With_Invalid_Keyword() throws InterruptedException {

        String keyword = "mncsd123";

        SearchPage searchPage = new SearchPage(DriverFactory.getDriver());
        ResultPage resultPage = searchPage.search(keyword);

        Thread.sleep(2000);

        String resultText = resultPage.getResultCountText();
        System.out.println(resultText);

        Assert.assertTrue(resultText.contains("0"), "Hiển thị 0 kết quả");
    }
}
