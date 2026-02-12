package drivers;

import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverManager extends DriverManager {

    @Override
    public void createDriver() {
        FirefoxDriver firefoxDriver = new FirefoxDriver();
        this.driver = firefoxDriver;
    }
}
