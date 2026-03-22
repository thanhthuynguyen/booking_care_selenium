package testcases.search;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ResultPage;
import pages.SearchPage;

public class Search_Long_KeyWord extends BaseTest {

    @Test
    public void TC08_Search_With_Long_Keyword() throws InterruptedException {
        String keyword = "automationtestingcourse";

        SearchPage searchPage = new SearchPage(DriverFactory.getDriver());

        ResultPage resultPage = searchPage.search(keyword);

        String resultText = resultPage.getResultCountText();

        System.out.println("Search với text dài: " + resultText);

        // verify page still shows result area
        Assert.assertTrue(resultText.contains("Hiển thị"));
    }
}