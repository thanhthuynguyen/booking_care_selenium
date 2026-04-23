package drivers;

import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class SafariDriverManager extends DriverManager {

    @Override
    public void createDriver() {

        SafariOptions options = new SafariOptions();

        // Enable automatic inspection (optional)
        options.setAutomaticInspection(false);

        // Enable profiling (optional)
        options.setAutomaticProfiling(false);

        this.driver = new SafariDriver(options);
    }
}
