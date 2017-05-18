package com.sonu.automation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class MoneyControl {
	private static WebDriver driver;
	private static int tabIndex = 0;
	private static List<String> urls = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		setUp();
		writeBookmarksToFile();
		readFile(args[0]);

		urls.forEach(url -> {

			openURLandPerformActions(url);

			openAndSwitchToNewTab();
		});
	}

	private static void writeBookmarksToFile() throws IOException {
		int urlCount = 0;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("data/bookmarks.txt")));
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("data/stock1.txt")));

		String line = "";
		while ((line = bufferedReader.readLine()) != null) {
			int startIndex = line.indexOf("HREF");
			int endIndex = line.indexOf("#");
			String url = "";
			if (endIndex == -1) {
				endIndex = line.indexOf("\"", 50);
			}
			url = line.substring(startIndex + 6, endIndex) + "#compare_frm";
			bufferedWriter.write(url);
			bufferedWriter.write("\n");
			urlCount++;

			if (urlCount == 25) {
				bufferedWriter.close();
				bufferedWriter = new BufferedWriter(new FileWriter(new File("data/stock2.txt")));
			} else if (urlCount == 50) {
				bufferedWriter.close();
				bufferedWriter = new BufferedWriter(new FileWriter(new File("data/stock3.txt")));
			} else if (urlCount == 75) {
				bufferedWriter.close();
				bufferedWriter = new BufferedWriter(new FileWriter(new File("data/stock4.txt")));
			} else if (urlCount == 100) {
				bufferedWriter.close();
				bufferedWriter = new BufferedWriter(new FileWriter(new File("data/stock5.txt")));
			}
		}
		bufferedReader.close();
		bufferedWriter.close();
	}

	private static void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
	}

	private static void readFile(String index) throws IOException {
		String filename = "data/stock" + index + ".txt";
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filename)));
		String url = "";
		while ((url = bufferedReader.readLine()) != null) {
			urls.add(url);
		}
		bufferedReader.close();
	}

	private static void openURLandPerformActions(String url) {
		driver.get(url);

		hoverOver("chartType");
		click("candlestick");

		hoverOver("indicators");
		click("volume");

		hoverOver("low-box");
	}

	private static void openAndSwitchToNewTab() {
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(++tabIndex));
	}

	private static void hoverOver(String elementId) {
		Actions builder = new Actions(driver);
		WebElement element = driver.findElement(By.id(elementId));
		builder.moveToElement(element).build().perform();
	}

	private static void click(String elementId) {
		WebElement element = driver.findElement(By.id(elementId));
		element.click();
	}
}
