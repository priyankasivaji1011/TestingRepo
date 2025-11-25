package com.evry.bdd.runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/*@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.evry.bdd.steps", "com.evry.bdd.hooks"},
          plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json",
                "junit:target/cucumber.xml",
                "tech.grasshopper.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)*/@CucumberOptions(
	    features = "src/test/resources/features",
	    glue = {"com.evry.bdd.steps","com.evry.apis","com.evry.bdd.hooks"}, 
	    plugin = {
	            "pretty",
	            "html:target/cucumber-html-report",
	            "json:target/cucumber.json",
	            "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"  // <-- THIS LINE ADDS FAILURE LOGGING
	    },
	    monochrome = true
	    //tags = "@smoke"
	)

public class TestRunner extends AbstractTestNGCucumberTests {
}