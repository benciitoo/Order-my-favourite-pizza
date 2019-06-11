import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;

public class OrderPizza {

    private static String browser;
    private static WebDriver driver;
    private static HashMap<String, String> config = new HashMap<>();

    public static void main(String[] args) throws Exception {
        setBrowser();
        setBrowserConfig();
        runPizzaOrderMachine();
    }

    //Works fine with Firefox at the moment, do not change
    private static void setBrowser() {
        browser = "Firefox";
    }

    private static void setBrowserConfig() {
        if (browser.contains("Chrome")) {
            driver = new ChromeDriver();
        }
        if (browser.contains("Firefox")) {
            //Change the path in next line in order to use geckodriver.exe for Firefox
            //System.setProperty("webdriver.gecko.driver", "/your/path/for/geckodriver.exe");
            driver = new FirefoxDriver();
        }
        if (browser.contains("Edge")) {
            driver = new EdgeDriver();
        }
    }

    private static void runPizzaOrderMachine() throws Exception {
        PropertyUtility.getProperties(config);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        driver.manage().window().maximize();
        driver.get("https://pizzaforte.hu/");

        type(By.xpath("//*[@id='login_form']//*[@name='felhasznalonev']"), config.get("un"));
        type(By.xpath("//*[@id='login_form']//*[@name='jelszo']"), config.get("pw"));
        click(By.id("login_button"));
        
        click(By.id("kreator"));
        ((JavascriptExecutor) driver).executeScript("document.querySelector('#pastas_panel').style.display=\"none\"");
        ((JavascriptExecutor) driver).executeScript("document.querySelector('#toppings_panel').style.display=\"block\"");
        Thread.sleep(1000);

        click(By.id("hus_300"));
        click(By.id("hus_306"));
        click(By.id("hus_307"));
        click(By.id("hus_309"));

        click(By.id("zoldsegek_tab"));
        click(By.id("zoldseg_407"));
        click(By.id("zoldseg_408"));

        click(By.id("sajtok_tab"));
        click(By.id("sajt-dupla_500"));

        click(By.id("creator_order_button"));
        ((JavascriptExecutor) driver).executeScript("document.querySelector('#creator_order_button').style.display=\"none\"");

        WebElement checkoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("order_paying_button")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkoutButton);

        type(By.xpath("//*[@id='order_form']/textarea[@name='megjegyzes']"),
                config.get("comments"));
        Thread.sleep(150);
        WebElement szeleteld = driver.findElement((By.cssSelector("#slices")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", szeleteld);
    }

    private static void click(By by) throws Exception {
        Thread.sleep(150);
        driver.findElement(by).click();
    }

    private static void type(By by, String value) throws Exception {
        Thread.sleep(150);
        driver.findElement(by).sendKeys(value);
    }
}
