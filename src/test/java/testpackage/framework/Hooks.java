package testpackage.framework;

import io.cucumber.java.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class Hooks {

    public static WebDriver driver;

    @Before
    public static void Initialize()
    {
        driver =  DriverSetup.GetDriver("chrome");
    }

    @After
    public static void TearDown()
    {
        if (driver != null){
            driver.quit();
        }
    }
}
