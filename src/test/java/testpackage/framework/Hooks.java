package testpackage.framework;

import io.cucumber.java.*;
import org.openqa.selenium.WebDriver;

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
