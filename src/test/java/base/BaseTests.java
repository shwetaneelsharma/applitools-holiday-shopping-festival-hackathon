package base;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultContainer;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.visualgrid.model.DesktopBrowserInfo;
import com.applitools.eyes.visualgrid.model.IosDeviceInfo;
import com.applitools.eyes.visualgrid.model.IosDeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import com.google.common.io.Files;

public class BaseTests {
	private final int concurrentSessions = 6;
	private final int viewPortWidth = 1200;
	private final int viewPortHeight = 800;
	String myEyesServer = "https://eyes.applitools.com/"; // set to your
															// server/cloud URL
	String autURL = "https://demo.applitools.com/tlcHackathonMasterV2.html";
	String appName = "AppliFashion";
	String batchName = "Holiday Shopping";
	/*
	 * Assign your API key to apiKey. If you have defined the environment
	 * variable APPLITOOLS_API_KEY then the SDK will access it automatically and
	 * you don't need to set the apiKey explicitly.
	 */
	private String apiKey = System.getenv("MY_APPLITOOLS_API_KEY");
	private EyesRunner runner = null;
	private Configuration suiteConfig;
	protected Eyes eyes;
	protected WebDriver webDriver;

	protected By blackColor = By.id("SPAN__checkmark__107");
	protected By filterButton = By.id("filterBtn");
	protected By shoesGrid = By.id("product_grid");
	protected By firstShoe = By.id("IMG__imgfluid__215");

	@BeforeSuite
	public void beforeTestSuite() {
		Boolean useVisualGrid = true;

		// 1. Create the runner that manages multiple tests
		if (useVisualGrid) {
			runner = new VisualGridRunner(concurrentSessions);
		} else {
			runner = new ClassicRunner();
		}

		// 2. Create a configuration object, we will use this when setting up
		// each test
		suiteConfig = new Configuration();

		// 3. Set the various configuration values
		suiteConfig
				// 4. Add Visual Grid browser configurations
				.addBrowser(new DesktopBrowserInfo(viewPortWidth,
						viewPortHeight, BrowserType.CHROME))
				.addBrowser(new DesktopBrowserInfo(viewPortWidth,
						viewPortHeight, BrowserType.FIREFOX))
				.addBrowser(new DesktopBrowserInfo(viewPortWidth,
						viewPortHeight, BrowserType.EDGE_CHROMIUM))
				.addBrowser(new DesktopBrowserInfo(viewPortWidth,
						viewPortHeight, BrowserType.SAFARI))
				.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_X,
						ScreenOrientation.LANDSCAPE))
				.addBrowser(new IosDeviceInfo(IosDeviceName.iPhone_X,
						ScreenOrientation.PORTRAIT))

				// 5. set up default Eyes configuration values
				.setApiKey(apiKey).setServerUrl(myEyesServer)
				.setAppName(appName)
				.setViewportSize(
						new RectangleSize(viewPortWidth, viewPortHeight))
				.setBatch(new BatchInfo(batchName));
	}

	@BeforeMethod
	public void beforeEachTest(ITestResult result) {
		// 1. Create the Eyes instance for the test and associate it with the
		// runner
		eyes = new Eyes(runner);

		// 2. Set the configuration values we set up in beforeTestSuite
		eyes.setConfiguration(suiteConfig);

		// 3. Create a WebDriver for the test
		System.setProperty("webdriver.chrome.driver", "resources/chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		webDriver = new ChromeDriver(options);
	}

	@AfterMethod
	public void afterEachTest(ITestResult result) {
		// check if an exception was thrown
		if (result.getStatus() != ITestResult.FAILURE) {
			// 1. Close the Eyes instance, no need to wait for results, we'll
			// get those at the end in afterTestSuite
			eyes.closeAsync();
		} else {
			var camera = (TakesScreenshot) webDriver;
			File screenshot = camera.getScreenshotAs(OutputType.FILE);
			try {
				Files.move(screenshot, new File(
						"resources/screenshots/" + result.getName() + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 2. There was an exception so the test may be incomplete - abort
			// the test
			eyes.abortIfNotClosed();
		}
		webDriver.quit();
	}

	@AfterSuite
	public void afterTestSuite(ITestContext testContext) {
		// Wait until the test results are available and retrieve them
		TestResultsSummary allTestResults = runner.getAllTestResults(false);
		for (TestResultContainer result : allTestResults) {
			handleTestResults(result);
		}
	}

	public void setConfiguration(String testName) {
		Configuration testConfig = eyes.getConfiguration();
		testConfig.setTestName(testName);
		eyes.setConfiguration(testConfig);

		webDriver = eyes.open(webDriver);
		webDriver.get(autURL);
	}

	void handleTestResults(TestResultContainer summary) {
		Throwable ex = summary.getException();
		if (ex != null) {
			System.out.printf("System error occured while checking target.\n");
		}
		TestResults result = summary.getTestResults();
		if (result == null) {
			System.out.printf("No test results information available\n");
		} else {
			System.out.printf(
					"AppName = %s, testname = %s, Browser = %s,OS = %s, viewport = %dx%d, matched = %d,mismatched = %d, missing = %d,aborted = %s\n",
					result.getAppName(), result.getName(), result.getHostApp(),
					result.getHostOS(), result.getHostDisplaySize().getWidth(),
					result.getHostDisplaySize().getHeight(),
					result.getMatches(), result.getMismatches(),
					result.getMissing(),
					(result.isAborted() ? "aborted" : "no"));
		}
	}
}
