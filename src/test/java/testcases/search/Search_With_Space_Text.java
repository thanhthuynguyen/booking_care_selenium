package testcases.search;

import base.BaseTest;
import drivers.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ResultPage;
import pages.SearchPage;

public class Search_With_Space_Text extends BaseTest {
    @Test
    public void TC07_Search_With_Space_Text() throws InterruptedException {
        String keyword = " test ";

        SearchPage searchPage = new SearchPage(DriverFactory.getDriver());

        ResultPage resultPage = searchPage.search(keyword);

        int totalCourse = resultPage.getTotalCourses();

        System.out.println("Search có khoảng trắng: " + totalCourse);

        Assert.assertTrue(totalCourse > 0);
    }
}
