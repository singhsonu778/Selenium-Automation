package com.sonu.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class GoogleSearch {
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.get("https://www.google.co.in");
		WebElement searchInput = driver.findElement(By.name("q"));
		searchInput.sendKeys("Selenium Automation");
		searchInput.submit();

		Thread.sleep(15000);
		driver.close();
	}
}