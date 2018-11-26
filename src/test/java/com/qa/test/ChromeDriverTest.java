package com.qa.test;

import com.qa.base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;


public class ChromeDriverTest extends TestBase {

    private String chromeHome;
    private String url;
    private WebDriver driver;
    private ChromeOptions chromeOptions;

    @BeforeClass
    public void setUp(){
        chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--test-type","--start-maximized","no-default-browser-check");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("chrome.switches", Collections.singletonList("--start-maximized"));
        chromeHome = prop.getProperty("chromeHome");
        url=prop.getProperty("url");
        System.setProperty("webdriver.chrome.driver",chromeHome);
        driver = new ChromeDriver(chromeOptions);
    }

    @Test
    public void chromeDriver(){
        WebDriverWait webDriverWait = new WebDriverWait(driver,5);
        driver.get(url);
//        driver.findElement(By.id("su")).click();
        try {
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            WebElement element = driver.findElement(By.xpath("//div[@class='el-scrollbar__view']/ul/div/li[6]/div/i"));
            element.getTagName();
            element.click();
            WebElement element1 = driver.findElement(By.xpath("//div[@class='el-scrollbar__view']/ul/div/li[6]/ul/a/li"));
            webDriverWait.until(ExpectedConditions.elementToBeClickable(element1));
            element1.click();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getCause().toString());
        }finally {
        }
    }
}
