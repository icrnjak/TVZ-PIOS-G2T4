package hr.tvz.keepthechange;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
public class AutomaticSearch {
    @Test
    public void search() {
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