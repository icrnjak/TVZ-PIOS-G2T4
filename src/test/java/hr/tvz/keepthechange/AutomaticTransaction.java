package hr.tvz.keepthechange;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
public class AutomaticTransaction {
    @Test
    public void newTransaction() {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
        WebDriver driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/login");
        WebElement username=driver.findElement(By.id("username"));
        WebElement password=driver.findElement(By.id("password"));
        WebElement login=driver.findElement(By.id("login"));
        username.sendKeys("admin");
        password.sendKeys("adminpass");
        login.click();
        driver.get("http://localhost:8080/index");
        WebElement newT=driver.findElement(By.id("newT"));
        newT.click();
        driver.get("http://localhost:8080/transaction/new");
        WebElement nazivT=driver.findElement(By.id("name"));
        nazivT.sendKeys("SeleniumTest");
        Select dropdownT = new Select(driver.findElement(By.id("vrstaT")));
        dropdownT.selectByIndex(1);
        Select dropdownCategoryT = new Select(driver.findElement(By.id("categoryT")));
        dropdownCategoryT.selectByIndex(4);
        WebElement iznosT=driver.findElement(By.id("iznosT"));
        iznosT.sendKeys("1234");
        WebElement sendT=driver.findElement(By.id("sendT"));
        sendT.click();
        driver.get("http://localhost:8080/index");
        Select dropdown = new Select(driver.findElement(By.id("izbor")));
        dropdown.selectByValue("category");
        Select dropdownCategory = new Select(driver.findElement(By.id("category")));
        dropdownCategory.selectByIndex(4);
        WebElement search=driver.findElement(By.id("basic-text1"));
        search.click();

        String actualUrl="http://localhost:8080/?category=GROCERIES";
        String expectedUrl= driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl,actualUrl);
    }
}