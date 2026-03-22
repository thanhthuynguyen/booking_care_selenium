package testcases.search;

import base.BaseTest;
import drivers.DriverFactory;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ResultPage;
import pages.SearchPage;

public class Search_Space_Enter extends BaseTest {
    @Test
    public void TC09_Search_With_Space_Enter() throws InterruptedException {

        String keyword = " ";   // space

        SearchPage searchPage = new SearchPage(DriverFactory.getDriver());

        // nhập space và nhấn enter
        searchPage.search(keyword + Keys.ENTER);

        ResultPage resultPage = new ResultPage(DriverFactory.getDriver());

        String resultText = resultPage.getResultCountText();

        System.out.println("Search space và nhấn enter: " + resultText);

        // verify có hiển thị kết quả
        Assert.assertTrue(resultText.contains("Hiển thị"),"Search result is not displayed");
    }
}