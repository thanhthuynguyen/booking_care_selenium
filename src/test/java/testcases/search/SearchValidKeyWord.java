package testcases.search;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ResultPage;
import pages.SearchPage;

public class SearchValidKeyWord extends BaseTest {
    @Test
    public void TC01_Search_With_Valid_Keyword() throws InterruptedException {
        //DriverFactory.getDriver().get("https://demo2.cybersoft.edu.vn/");

        String keyword = "test";
        SearchPage searchPage = new SearchPage(DriverFactory.getDriver());
        ResultPage resultPage = searchPage.search(keyword);

        Thread.sleep(3000); // dừng 3s để xem kết quả

        // verify URL đúng trang search
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("timkiem"), "URL không phải trang search");

        // verify có kết quả
        String resultText = resultPage.getResultCountText();
        System.out.println(resultText);
        Assert.assertFalse(resultText.contains("0"), "Search không trả kết quả");
    }
}
