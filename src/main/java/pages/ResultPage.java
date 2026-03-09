package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ResultPage extends CommonPage {

    // text hiển thị số kết quả
    private By txtResultCount = By.xpath("//*[contains(text(),'Hiển thị')]");

    // title khóa học
    private By courseTitles = By.cssSelector(".titleCourse, h3");

    public ResultPage(WebDriver driver) {
        super(driver);
    }

    // lấy text hiển thị số kết quả
    public String getResultCountText() {
        waitForVisibilityOfElementLocated(txtResultCount, 10);
        return getText(txtResultCount);
    }

    // lấy danh sách title khóa học
    public List<String> getCourseTitles() {
        List<WebElement> elements = driver.findElements(courseTitles);
        return elements.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    // đếm số khóa học hiển thị
    public int getTotalCources() {
        return driver.findElements(courseTitles).size();
    }

    // kiểm tra không có kết quả
    public boolean isNoResult() {
        return getResultCountText().contains("0");
    }
}