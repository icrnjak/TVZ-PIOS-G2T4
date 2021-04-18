package hr.tvz.keepthechange;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AutomaticRegistration {

    @Test
    public void login() {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
        WebDriver driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/login");
        WebElement registration=driver.findElement(By.id("registracija"));
        registration.click();
        driver.get("http://localhost:8080/registration");
        WebElement usernameReg=driver.findElement(By.id("username"));
        WebElement passwordReg=driver.findElement(By.id("password"));
        WebElement passwordReg2=driver.findElement(By.id("passwordRe"));
        WebElement wallet=driver.findElement(By.id("wallet"));
        usernameReg.sendKeys("fran");
        passwordReg.sendKeys("fran");
        passwordReg2.sendKeys("fran");
        wallet.sendKeys("fran");
        WebElement send=driver.findElement(By.id("send"));
        send.click();
        driver.get("http://localhost:8080/login");
        WebElement username=driver.findElement(By.id("username"));
        WebElement password=driver.findElement(By.id("password"));
        WebElement login=driver.findElement(By.id("login"));
        username.sendKeys("fran");
        password.sendKeys("fran");
        login.click();
        String actualUrl="http://localhost:8080/index";
        String expectedUrl= driver.getCurrentUrl();
        Assert.assertEquals(expectedUrl,actualUrl);
    }

}
