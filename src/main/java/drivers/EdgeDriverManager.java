package drivers;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.HashMap;
import java.util.Map;

public class EdgeDriverManager extends DriverManager {

    @Override
    public void createDriver() {

        EdgeOptions options = new EdgeOptions();

        // ===== Disable automation bar =====
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        // ===== Disable password save dialog =====
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        // ===== FIX unstable Selenium =====

        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        options.addArguments("--remote-allow-origins=*");

        // Optimize load (React app)
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        // Optional headless
        // options.addArguments("--headless=new");

        this.driver = new EdgeDriver(options);
    }
}
