package com.sonu.automation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class MoneyControl {
	private static WebDriver driver;
	private static int tabIndex = 0;
	private static int outputFileIndex = 1;
	private static List<String> urls = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		setUp();

		clearLogFileContents();

		writeBookmarksToFile(1);
		writeBookmarksToFile(2);

		populateUrlsFromFile(args[0]);

		urls.forEach(url -> {
			try {
				openURLandPerformActions(url);
			} catch (TimeoutException e) {
				logErrorToFile(e);
			}
			openAndSwitchToNewTab();
		});
	}

	private static void logErrorToFile(TimeoutException e) {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(new FileWriter(new File("logs/logs.txt"), true));
		} catch (IOException ex) {}
		printWriter.write(driver.getTitle());
		printWriter.write("\n");
		e.printStackTrace(printWriter);
		printWriter.write("\n");
		printWriter.close();
	}

	private static void clearLogFileContents() throws FileNotFoundException {
		PrintWriter printWriter = new PrintWriter(new File("logs/logs.txt"));
		printWriter.close();
	}

	private static void setUp() {
		System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
	}

	private static void writeBookmarksToFile(int readFileIndex) throws IOException {
		int urlCount = 0;
		String line = "";

		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("data/bookmarks" + readFileIndex + ".txt")));
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("data/stock" + outputFileIndex + ".txt")));

		while ((line = bufferedReader.readLine()) != null) {
			bufferedWriter.write(extractUrl(line));
			bufferedWriter.write("\n");
			urlCount++;

			if (urlCount == 25) {
				bufferedWriter.close();
				bufferedWriter = new BufferedWriter(new FileWriter(new File("data/stock" + ++outputFileIndex + ".txt")));
				urlCount = 0;
			}
		}
		outputFileIndex++;
		bufferedReader.close();
		bufferedWriter.close();
	}

	private static String extractUrl(String line) {
		int startIndex = line.indexOf("HREF");
		int endIndex = line.indexOf("#");
		if (endIndex == -1) {
			endIndex = line.indexOf("\"", 50);
		}
		return line.substring(startIndex + 6, endIndex) + "#compare_frm";
	}

	private static void populateUrlsFromFile(String readFileIndex) throws IOException {
		String url = "";
		String filename = "data/stock" + readFileIndex + ".txt";
		BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filename)));
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
