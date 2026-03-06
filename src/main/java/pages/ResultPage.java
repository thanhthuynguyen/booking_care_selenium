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

    public String getResultCountText() {
        return getText(txtResultCount);
    }

    public List<String> getCourseTitles() {
        List<WebElement> elements = driver.findElements(courseTitles);
        return elements.stream().map(WebElement::getText).collect(Collectors.toList());
    }
}