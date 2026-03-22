package testcases.search;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ResultPage;
import pages.SearchPage;

public class Search_Uppercase_KeyWord extends BaseTest {
    @Test
    public void TC06_Search_With_Uppercase_KeyWord() throws InterruptedException {

        String keyword = "JAVA";

        SearchPage searchPage = new SearchPage(DriverFactory.getDriver());

        ResultPage resultPage = searchPage.search(keyword);

        int totalCourse = resultPage.getTotalCourses();

        System.out.println("Search chữ in hoa: " + totalCourse);

        Assert.assertTrue(totalCourse > 0);
    }
}