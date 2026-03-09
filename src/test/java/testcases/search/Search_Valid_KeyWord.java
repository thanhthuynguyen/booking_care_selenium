package testcases.search;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ResultPage;
import pages.SearchPage;

public class Search_Valid_KeyWord extends BaseTest {
    @Test
    public void TC01_Search_With_Valid_Keyword() throws InterruptedException {
        /*
        1 Mở website
        2 Nhập keyword "test"
        3 Nhấn ENTER
        4 Chuyển sang URL /timkiem
        5 Trang ResultPage load
        6 Hiển thị số lượng kết quả
        7 Verify số kết quả > 0
         */
        String keyword = "test";

        SearchPage searchPage = new SearchPage(DriverFactory.getDriver());

        // thực hiện search
        ResultPage resultPage = searchPage.search(keyword);

        Thread.sleep(3000); // dừng 3s để xem kết quả

        // 1. Verify chuyển sang URL search
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("timkiem/test"), "URL search không đúng");

        // 1. Verify chuyển sang URL search
        String resultText = resultPage.getResultCountText();

        System.out.println("Search keyword hợp lệ: " + resultText);

        Assert.assertFalse(resultText.contains("0"), "Search không trả kết quả");
    }
}
