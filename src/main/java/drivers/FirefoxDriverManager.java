package drivers;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class FirefoxDriverManager extends DriverManager {

    @Override
    public void createDriver() {

        FirefoxOptions options = new FirefoxOptions();

        // ===== Disable automation detection =====
        options.addPreference("dom.webdriver.enabled", false);
        options.addPreference("useAutomationExtension", false);

        // ===== Disable password save popup =====
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("signon.rememberSignons", false);
        profile.setPreference("signon.autofillForms", false);
        profile.setPreference("signon.autologin.proxy", false);

        options.setProfile(profile);

        // ===== FIX unstable Selenium =====

        // Reduce page load waiting (similar to Chrome EAGER)
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        // Disable GPU (optional but safe)
        options.addArguments("--disable-gpu");

        // Disable extensions
        options.addArguments("--disable-extensions");

        options.addArguments("--no-sandbox");

        // Memory fix (Linux/container useful)
        options.addArguments("--disable-dev-shm-usage");

        // Optional: headless mode
        // options.addArguments("--headless");

        this.driver = new FirefoxDriver(options);
    }
}
