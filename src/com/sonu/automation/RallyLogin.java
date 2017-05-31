/*This class opens url "http://agilecentral.deere.com" which is
for internal use and inaccessible from outside deere network*/
package com.sonu.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class RallyLogin {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);

        driver.get("http://agilecentral.deere.com");

        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys(args[0]);

        WebElement password = driver.findElement(By.name("PASSWORD"));
        password.sendKeys(args[1]);

        WebElement login_btn = driver.findElement(By.className("call-to-action "));
        login_btn.click();

        //driver.close();
    }
}
