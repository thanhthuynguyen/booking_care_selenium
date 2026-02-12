package drivers;

import org.openqa.selenium.WebDriver;

public abstract class DriverManager {
    protected WebDriver driver;

    public WebDriver getDriver() {
        return this.driver;
    }

    public  abstract void createDriver();
}
