package drivers;

import org.openqa.selenium.edge.EdgeDriver;

public class EdgeDriverManager extends DriverManager{
    @Override
    public void createDriver() {
        EdgeDriver edgeDriver = new EdgeDriver();
        this.driver = edgeDriver;
    }
}
