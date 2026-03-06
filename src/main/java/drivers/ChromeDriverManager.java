package drivers;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class ChromeDriverManager extends DriverManager {

    @Override
    public void createDriver() {

        ChromeOptions options = new ChromeOptions();

        // Disable automation bar
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);

        // Disable password save dialog
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        // ===== FIX unstable Selenium =====

        // Fix renderer timeout
        options.addArguments("--disable-gpu");

        // Fix Chrome sandbox issues
        options.addArguments("--no-sandbox");

        // Fix memory issue (very important)
        options.addArguments("--disable-dev-shm-usage");

        // Disable extensions
        options.addArguments("--disable-extensions");

        // Allow remote origin (Only Chrome needs this)
        options.addArguments("--remote-allow-origins=*");

        // Don't wait for the full JS to load. (very important for React)
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        this.driver = new ChromeDriver(options);
    }
}