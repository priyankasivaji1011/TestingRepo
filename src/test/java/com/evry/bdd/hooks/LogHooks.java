package com.evry.bdd.hooks;

import com.aventstack.extentreports.Status;
import com.evry.bdd.utils.ExtentTestManager;
import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;

public class LogHooks {

    @BeforeStep
    public void beforeStep(Scenario scenario) {
        ExtentTestManager.getTest().log(Status.INFO, "STEP STARTED: " + scenario.getName());
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            ExtentTestManager.getTest().log(Status.FAIL, "STEP FAILED: " + scenario.getName());
        } else {
            ExtentTestManager.getTest().log(Status.PASS, "STEP PASSED");
        }
    }

}
