package com.agog.base;

import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jsoup.select.Evaluator.IsEmpty;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.agog.utilities.ExtentReport;
import com.agog.utilities.TestUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {

	/*
	 * WebDriver - done Properties - done Logs - log4j jar, .log files(App and Sele)
	 * ,log4j.properties, Logger class ExtentReports DB Excel Mail
	 * ReportNG,ExtentReports Jenkins
	 * 
	 */

	public static WebDriver driver;
	public static WebElement ele;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static String browser;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public ExtentReports report = ExtentReport.getInstance();
	public static ExtentTest test;

	@BeforeSuite
	public void setUp() {

		if (driver == null) {

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\main\\java\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\main\\java\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {

				browser = System.getenv("browser");
			} else {

				browser = config.getProperty("browser");

			}

			config.setProperty("browser", browser);

			if (config.getProperty("browser").equals("Chrome")) {
				System.setProperty("webdriver.chrome.driver",
						"C:\\Users\\Terralogic\\Downloads\\chromedriver_win32 (2)\\chromedriver.exe");
				driver = new ChromeDriver();
				log.debug("Browser launched successfully!!!");
			} else if (config.getProperty("browser").equals("Firefox")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\src\\main\\java\\executables\\geckodriver.exe");
				driver = new FirefoxDriver();
				log.debug("Browser launched successfully");
			} else if (config.getProperty("browser").equals("IE")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\src\\main\\java\\executables\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				log.debug("Browser launched successfully");
			}

			driver.get(config.getProperty("siteurl"));
			log.debug("Navigated to Agog site");
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicitwait")),
					TimeUnit.SECONDS);

		}

	}

	@AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
			log.debug("Test execution completed!!!");
		}
	}

	public boolean isElementPreset(By by) {

		try {

			driver.findElement(by);
			return true;

		} catch (NoSuchElementException e) {
			return false;

		}

	}

	public void ClickElement(String locator) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_xpath")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_id")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}

		test.log(LogStatus.INFO, "Clicking on " + locator);
	}

	public void SendKeys(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(config.getProperty(value));
		} else if (locator.endsWith("_xpath")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(config.getProperty(value));
		} else if (locator.endsWith("_id")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(config.getProperty(value));
		}
		test.log(LogStatus.INFO, "Clicking on " + locator + " Typing value as " + config.getProperty(value));
	}

	// Adding multiple failures and SS for the same test case calling below method
	public static void verifyAssert(String actual, String expected) throws IOException {

		try {

			Assert.assertEquals(actual, expected);

		} catch (Throwable t) {

			TestUtils.captureScreenshot();
			// ReportNG
			Reporter.log("<br>" + "Verification failure: " + t.getMessage().toUpperCase() + "<br>");
			Reporter.log("<a target=\"_blank\" href=" + TestUtils.SSName + "><img src=" + TestUtils.SSName
					+ " height=200 width=200></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			// ExtentReports
			test.log(LogStatus.FAIL, "Verification Failed with Exception: " + t.getMessage().toUpperCase());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtils.SSName));
		}

	}

}
