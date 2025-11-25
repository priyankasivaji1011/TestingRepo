package com.evry.bdd.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {

    // ✅ Thread-safe variable for parallel test support
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // ✅ Lazy-load instance (avoid creating multiple ExtentReports)
    private static ExtentReports extent;

    public static synchronized void startTest(String testName) {
        if (extent == null) {
            extent = ExtentManager.getInstance();
        }
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
    }

    public static synchronized ExtentTest getTest() {
        return test.get();
    }

    // ⚠️ Optional: don’t flush here (let Hooks @AfterAll handle it)
    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}
