package com.evry.bdd.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    private static ExtentReports extent;

    // ✅ Singleton pattern to ensure only one instance of ExtentReports
    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    // ✅ Create Extent report with custom theme and folder structure
    private static ExtentReports createInstance() {
        try {
            Files.createDirectories(Paths.get("target/ExtentReport"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String reportPath = "target/ExtentReport/SparkReport.html";
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("BDD Automation Test Report");
        spark.config().setReportName("API + DB Automation Suite");
        spark.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(spark);

        // ✅ Add some system info
        extent.setSystemInfo("Tester", "Priyanka S");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Framework", "Cucumber + Selenium + RestAssured");

        return extent;
    }

    // ✅ Capture screenshot and save under /target/screenshots
    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        String path = "target/screenshots/" + screenshotName + ".png";
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.createDirectories(Paths.get("target/screenshots/"));
            Files.copy(src.toPath(), Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
