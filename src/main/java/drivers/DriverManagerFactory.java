package drivers;

public class DriverManagerFactory {
    public static DriverManager getDriverManager(String browser) throws Exception {
        if(browser.equals("chrome")) {
            return new ChromeDriverManager();
        } else if (browser.equals("firefox")) {
            return new FirefoxDriverManager();
        } else if (browser.equals("safari")) {
            return new SafariDriverManager();
        } else if (browser.equals("edge")) {
            return new EdgeDriverManager();
        } else {
            throw new Exception("Invalid browser!");
        }
    }
}
