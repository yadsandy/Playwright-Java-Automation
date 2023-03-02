package base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

import reports.ExtentManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class Base {
	public static Playwright pw;
	public Properties prop;
	public static BrowserContext browserContext;
	public static Page page;

	@BeforeSuite
	public void beforeExecution() throws IOException {
		ExtentManager.getReporter();
	}

	public Page initializeBrowser() throws IOException {
		prop = loadConfig();
		String browserName = prop.getProperty("browser");
		boolean headless = Boolean.parseBoolean(prop.getProperty("headless"));
		pw = Playwright.create();
		LaunchOptions lp = new LaunchOptions();
		lp.setHeadless(headless);
		Browser browser = null;
		if (browserName.equals("chrome")) {
			browser = pw.chromium().launch(lp);
		} else if (browserName.equals("firefox")) {
			browser = pw.firefox().launch(lp);
		} else if (browserName.equals("webkit")) {
			browser = pw.webkit().launch(lp);
		} else {
			System.out.println(browserName + " is not a valid browser");
		}
		browserContext = browser.newContext();
		page = browserContext.newPage();
		page.setDefaultNavigationTimeout(20000);
		return page;

	}

	public Properties loadConfig() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/resources/config.properties");
		prop.load(fis);
		return prop;
	}

	@AfterMethod
	public void closeMethod() {
		page.close();
		browserContext.close();
		pw.close();
	}
	
	@AfterSuite()
	public void afterSuite() {
		ExtentManager.endReport();
		
	}
}
