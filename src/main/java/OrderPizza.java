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

    private static void setBrowser() {
        browser = "Firefox";
    }

    private static void setBrowserConfig() {
        if (browser.contains("Chrome")) {
            driver = new ChromeDriver();
        }
        if (browser.contains("Firefox")) {
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

        type(By.cssSelector("#login_form > div:nth-child(1) > input[type=\"text\"]:nth-child(1)"), config.get("un"));
        type(By.cssSelector("#login_form > div:nth-child(1) > input[type=\"password\"]:nth-child(2)"), config.get("pw"));
        click(By.id("login_button"));

        /* MAGYAROS PIZZA
        driver.findElement(By.id("pizzak")).click();
        driver.findElement(By.cssSelector("#pizzak_list_1 > tbody > tr:nth-child(6) > td:nth-child(3) > div > div:nth-child(9)")).click();*/

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

        type(By.name("megjegyzes"),
                "Légyszíves hívjatok fel ha megérkeztetek, mert rossz a kapucsengőm. Fél perc és kint vagyok! Köszi!");
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
