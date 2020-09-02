package testpackage.framework;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SeleniumWrapper {

    WebDriver driver;
    int timeoutMS=5000;

    public SeleniumWrapper(WebDriver _driver)
    {
        this.driver = _driver;
    }

    public WebElement WaitAndFind(By simpleBy) throws TimeoutException
    {
        WebElement element = null;
        System.out.println("Trying to find the element with the locator: "+simpleBy);
        try {
            element = (new WebDriverWait(driver, timeoutMS))
                    .until(ExpectedConditions.visibilityOfElementLocated(simpleBy));
        } catch (Exception e) {
            Assert.assertTrue("the Element was not visible and could not be found, locator: "+ simpleBy.toString(), false);
            e.printStackTrace();
            throw e;
        }
        return element;
    }

    public boolean ValidateElementVisibility(By simpleBy)
    {
        List<WebElement> elements = driver.findElements(simpleBy);
        if(elements.size()>0){
            return elements.stream().findFirst().get().isDisplayed();
        }
        return false;
    }

    /**method to validate visibility of item without taking too much time, works only with Css Selectors*/
    public boolean ValidateElementExists(By locator)
    {
        boolean flag = true;
        String html = GetDriver().getPageSource();
        Document doc = Jsoup.parse(html);
        String locatorParsed = locator.toString().replace("By.cssSelector: ", "");
        /**here is grabbing the href attribute of the button with the linkname specified by parameter in order to redirect to the page*/
        try {
            flag = doc.select(locatorParsed).stream().findFirst().isPresent();
        } catch (Exception e) {
        }
        return flag;
    }

    public void Assert_ElementVisibility(By simpleBy, String message)
    {
        WebElement element = null;
        System.out.println("Trying to find the element with the locator: "+simpleBy);
        try {
            element = (new WebDriverWait(driver, timeoutMS))
                    .until(ExpectedConditions.visibilityOfElementLocated(simpleBy));
        } catch (Exception e) {
            Assert.assertTrue(message,false);
            throw e;
        }
        Assert.assertTrue(message,element != null);
    }

    public void Assert_ElementDissapears(By simpleBy, String message)
    {
        boolean visible;
        System.out.println("Trying to find the element with the locator: "+simpleBy);
        try {
            visible = (new WebDriverWait(driver, 5))
                    .until(ExpectedConditions.invisibilityOfElementLocated(simpleBy));
        } catch (Exception e) {
            Assert.assertTrue(message,false);
            throw e;
        }
        Assert.assertTrue(message,visible);
    }

    public boolean ValidateElementVisibilityInObject(By simpleBy, WebElement context)
    {
        return (new WebDriverWait(driver, timeoutMS))
                .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(context, simpleBy)).stream().findFirst().get().isDisplayed();
    }

    public void Assert_ElementVisibilityInObject(By simpleBy, WebElement context, String message)
    {
        Assert.assertTrue(message, ValidateElementVisibilityInObject(simpleBy, context));
    }


    public WebElement WaitAndFind(By simpleBy, WebElement context)
    {
        WebElement element = null;
        System.out.println("Trying to find the element with the locator: "+simpleBy);
        try {
            element = (new WebDriverWait(driver, timeoutMS))
                    .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(context, simpleBy)).stream().findFirst().get();
        } catch (Exception e) {
            Assert.assertTrue("the Element was not visible in the context and could not be found, locator: "+ simpleBy.toString(),false);
            e.printStackTrace();
        }
        return element;
    }

    public List<WebElement> WaitAndFindElements(By simpleBy)
    {
        List<WebElement> elements = null;
        System.out.println("Trying to find the element with the locator: "+simpleBy);
        try {
            elements = (new WebDriverWait(driver, timeoutMS))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(simpleBy));
        } catch (Exception e) {
            Assert.assertFalse("the Elements could not be found, list came out empty with the locator: "+ simpleBy.toString(),elements.isEmpty());
            e.printStackTrace();
        }
        return elements;
    }

    public List<WebElement> WaitAndFindElements(WebElement context, By simpleBy)
    {
        List<WebElement> elements = null;
        System.out.println("Trying to find the element with the locator: "+simpleBy);
        try {
            elements = (new WebDriverWait(driver, timeoutMS))
                    .until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(context,simpleBy));
        } catch (NumberFormatException e) {
            Assert.assertFalse("the Elements could not be found, list came out empty with the locator: "+ simpleBy.toString(),elements.isEmpty());
            e.printStackTrace();
        }

        return elements;
    }

    public WebElement FindElement(By simpleBy)
    {
        return WaitAndFind(simpleBy);
    }

    public WebElement FindElementWithinContext(By simpleBy, WebElement context)
    {
        return WaitAndFind(simpleBy, context);
    }

    public List<WebElement> FindElementsWithinContext(By simpleBy, WebElement context)
    {
        return WaitAndFindElements(context, simpleBy);
    }

    public void ClickElement(By simpleBy)
    {
        WaitAndFind(simpleBy).click();
    }

    public void ClickElementWithoutVisiblity(By simpleBy)
    {
        WebElement element = null;
        System.out.println("Trying to find the element with the locator: "+simpleBy);
        try {
            element = (new WebDriverWait(driver, timeoutMS))
                    .until(ExpectedConditions.presenceOfElementLocated(simpleBy));
        } catch (NumberFormatException e) {
            Assert.assertTrue("the Element was not visible in the context and could not be found, locator: "+ simpleBy.toString(),element != null);
            e.printStackTrace();
        }
        element.click();
    }

    public void ClickElementWithinContext(By simpleBy, WebElement context)
    {
        WaitAndFind(simpleBy, context).click();
    }

    public void KeyboardPress(By simpleBy, String keys)
    {
        driver.findElement(simpleBy).sendKeys(Keys.chord(Keys.CONTROL, keys));
    }


    //
    public void SelectDropdownOptionByText(By _dropdown,By _dropdownMenuItems, String option)
    {
        WebElement dropdown =  FindElement(_dropdown);
        dropdown.click();
        List<WebElement> dropdownMenu =  WaitAndFindElements(_dropdownMenuItems);
        dropdownMenu.stream().filter(x->x.getAttribute("innerText").contains(option)).findFirst().get().click();
    }

    public WebDriver GetDriver()
    {
        return this.driver;
    }

    public void SmallWait(int wait)
    {
        try{Thread.sleep(wait+timeoutMS);}catch (Exception e){}
    }

    public String getAtributte(By simpleBy, String _attributte)
    {
        return FindElement(simpleBy).getAttribute(_attributte);
    }

    public void WriteInput(By _simpleBy, String text)
    {
        WebElement input = FindElement(_simpleBy);
        input.clear();
        input.sendKeys(text);
    }

    public void WriteInputWithSmallWait(By _simpleBy, String text)
    {
        WebElement input = FindElement(_simpleBy);
        input.clear();
        int i = 0;
        while(text.length() > i)
        {
            SmallWait(10);
            input.sendKeys(Character.toString(text.charAt(i)));
            i++;
        }
    }

    public boolean isElementPresentWithWait(By locator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.ignoring(NoSuchElementException.class).until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void SwitchToTab(int tab)
    {
        ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(tab));
    }

    public void CloseTab(int tab)
    {
        ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(tab));
        driver.close();
    }

    public void JsClickElement(By simpleBy)
    {
        WebElement element = driver.findElement(simpleBy);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void SelectDropdownOptions(By _simpleby, String option)
    {
        Select dropdown = new Select(FindElement(_simpleby));
        dropdown.selectByVisibleText(option);
    }

    public void SelectDropdownByIndex(By _simpleby, int index)
    {
        Select dropdown = new Select(FindElement(_simpleby));
        dropdown.selectByIndex(index);
    }

    public WebElement FindInvisibleElement(final By locator) {
        WebElement foo = null;
        try{
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(NoSuchElementException.class);
            foo = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver driver) {
                    return driver.findElement(locator);
                }
            });
        }
        catch (Exception e)
        {
            return foo;
        }
        return  foo;
    }

    public void PressEnter(By locator)
    {
        driver.findElement(locator).sendKeys(Keys.ENTER);
    }

    public void PressDown(By locator)
    {
        driver.findElement(locator).sendKeys(Keys.ARROW_DOWN);
    }

    public void SelectRadioButton(By _simpleBy)
    {
        WebElement element = WaitAndFind(_simpleBy);
        element.click();
        boolean boo = element.isSelected();
        if (!boo) element.click();
        Assert.assertTrue("The radio button is not after being clicked", boo);
    }

    public void MouseOver(By _simpleBy)
    {
        Actions action = new Actions(driver);
        WebElement we = driver.findElement(_simpleBy);
        action.moveToElement(we).build().perform();
    }
}
