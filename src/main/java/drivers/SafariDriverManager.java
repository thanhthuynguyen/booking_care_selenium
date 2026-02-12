package drivers;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.safari.SafariDriver;

public class SafariDriverManager extends DriverManager{
    @Override
    public void createDriver() {
        SafariDriver safariDriver = new SafariDriver();
        this.driver = safariDriver;
    }
}
