package com.evry.bdd.hooks;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.evry.bdd.utils.DriverManager;
import com.evry.bdd.utils.ExtentManager;
import com.evry.bdd.utils.ExtentTestManager;

import io.cucumber.java.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Hooks {

    private static ExtentReports extent;

    // Initialize Extent Report once before all tests
    @BeforeAll
    public static void setupReport() {
        extent = ExtentManager.getInstance();
        System.out.println("📊 Extent Spark Report initialized.");
    }

    // Open browser before each scenario
    @Before
    public void setUp(Scenario scenario) {
        if (DriverManager.getDriver() == null) {
            // Use WebDriverManager to set up ChromeDriver automatically
            WebDriverManager.chromedriver().setup();

            // Run Chrome in headless mode for Jenkins
            ChromeOptions options = new ChromeOptions();
           // options.addArguments("--headless");
            //push
			/*
			 * options.addArguments("--disable-gpu");
			 * options.addArguments("--window-size=1920,1080");
			 * options.addArguments("--no-sandbox");
			 * options.addArguments("--disable-dev-shm-usage");
			 */
            WebDriver driver = new ChromeDriver(options);
            DriverManager.setDriver(driver);
            System.out.println("🌐 Headless Chrome launched successfully.");
        }

        // Start scenario in Extent
        ExtentTestManager.startTest(scenario.getName());
        ExtentTestManager.getTest().log(Status.INFO, "🚀 Scenario Started: " + scenario.getName());
    }

    // Take screenshot after each step
    @AfterStep
    public void captureStepScreenshot(Scenario scenario) {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver != null) {
                String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                ExtentTestManager.getTest().addScreenCaptureFromBase64String(base64Screenshot);
            }
        } catch (Exception e) {
            if (ExtentTestManager.getTest() != null) {
                ExtentTestManager.getTest().log(Status.WARNING, "Screenshot capture failed: " + e.getMessage());
            }
        }
    }

    // Handle pass/fail reporting after each scenario
    @After
    public void tearDownScenario(Scenario scenario) {
        try {
            if (DriverManager.getDriver() != null) {
                if (scenario.isFailed()) {
                    byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                            .getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", "Failure Screenshot");
                    ExtentTestManager.getTest().log(Status.FAIL,
                            "❌ Scenario Failed: " + scenario.getName());
                    ExtentTestManager.getTest().addScreenCaptureFromBase64String(
                            java.util.Base64.getEncoder().encodeToString(screenshot));
                } else {
                    ExtentTestManager.getTest().log(Status.PASS, "✅ Scenario Passed");
                }
            }
        } catch (Exception e) {
            if (ExtentTestManager.getTest() != null) {
                ExtentTestManager.getTest().log(Status.WARNING,
                        "Error in tearDownScenario: " + e.getMessage());
            }
        }
    }

    // Flush report and close browser after all tests
    @AfterAll
    public static void tearDownAll() {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver != null) {
                driver.quit();
                DriverManager.setDriver(null);
                System.out.println("🛑 Browser closed.");
            }
        } catch (Exception ignored) {}

        if (extent != null) {
            extent.flush();
            System.out.println("✅ Spark Report generated at: target/ExtentReport/SparkReport.html");
        }
    }
}
