package com.sonu.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GoogleSearch {
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");

		WebDriver driver = new ChromeDriver(options);

		driver.get("https://www.google.co.in");

		WebElement searchInput = driver.findElement(By.name("q"));
		searchInput.sendKeys("Selenium Automation");
		searchInput.submit();

		Thread.sleep(15000);

		driver.close();
	}
}