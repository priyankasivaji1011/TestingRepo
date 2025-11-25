package com.evry.bdd.utils;

import com.aventstack.extentreports.Status;

public class Log {

    public static void info(String message) {
        System.out.println("INFO: " + message);
        ExtentTestManager.getTest().log(Status.INFO, message);
    }

    public static void pass(String message) {
        System.out.println("PASS:" +message);
        ExtentTestManager.getTest().log(Status.PASS, message);
    }

    public static void fail(String message) {
        System.out.println("FAIL: " +message);
        ExtentTestManager.getTest().log(Status.FAIL, message);
    }
}
