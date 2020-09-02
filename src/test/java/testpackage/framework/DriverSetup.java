package testpackage.framework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.SessionId;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class DriverSetup {

    public static SessionId sessionId ;
    public static WebDriver browser;

    public static WebDriver GetDriver(String driver)
    {
        switch(driver) {
            case "chrome":
                //Setting up Chrome Driver
                browser = GetChromeDriver(null);
                break;
            case "headless-chrome":
                //Setting up Headless Chrome Driver
                browser = GetChromeDriver("--headless");
                break;
            case "firefox":
                //Setting up Firefox Driver
                browser= GetFirefoxDriver();
                break;
        }
        return ConfigureWaits(browser);
    }

    private static WebDriver GetFirefoxDriver()
    {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        return new FirefoxDriver();
    }

    private static WebDriver GetChromeDriver(String args)
    {
        WebDriverManager.chromedriver().setup();
        ChromeDriverService driverService = ChromeDriverService.createDefaultService();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        if(args != null)  options.addArguments(args);
        return new ChromeDriver(driverService, options);
    }

    private static WebDriver ConfigureWaits(WebDriver driver)
    {
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        return driver;
    }
}
