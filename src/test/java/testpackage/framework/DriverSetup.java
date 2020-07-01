package testpackage.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.SessionId;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class DriverSetup {

    public static SessionId sessionId ;
    public static WebDriver browser;

    public static String GetHostname()
    {
        String hostname = "Unknown";
        try
        {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        }
        catch (UnknownHostException ex)
        {
            System.out.println("Hostname can not be resolved");
        }
        return hostname;
    }

    public static WebDriver GetDriver(String driver)
    {
        String hostname = GetHostname();
        switch(driver) {
            case "chrome":
                //Setting up Chrome Driver
                browser = GetChromeDriver(false);
                break;
            case "headless-chrome":
                //Setting up Headless Chrome Driver
                browser = GetChromeDriver(true);
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
        FirefoxOptions options = new FirefoxOptions();
        String strFFBinaryPath = "/src/test/resources/webdrivers/firefox.exe";
        options.setBinary(strFFBinaryPath);
        return new FirefoxDriver(options);
    }

    private static WebDriver GetChromeDriver(boolean headless)
    {
        String path = "";
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")){
            path = System.getProperty("user.dir") + "/src/test/resources/webdrivers";
        }
        else if (os.contains("mac os x")){
            path = "/Users/webdrivers/chromedriver";
        }
        else if (os.contains("nix") || os.contains("aix") || os.contains("nux")){
            path ="/home/webdrivers/chromedriver";
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        if(headless) {
            options.addArguments("--headless");
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +
                                         "/src/test/resources/webdrivers/chromedriver.exe");
        }
        else {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/webdrivers/chromedriver.exe");
        }
        return new ChromeDriver(options);
    }

    private static WebDriver ConfigureWaits(WebDriver driver)
    {
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        return driver;
    }
}
