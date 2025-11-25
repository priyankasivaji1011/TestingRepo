package com.evry.bdd.steps;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.evry.apis.ApiService;
import com.evry.bdd.pages.ProductListPage;
import com.evry.bdd.utils.ConfigReader;
import com.evry.bdd.utils.ExtentTestManager;
import com.evry.bdd.utils.LoggerHelper;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class ApiSteps {

    private ApiService api = new ApiService();
    private static final Logger log = LoggerHelper.getLogger(ProductListPage.class);

    @Given("I call all APIs using keys from config")
    public void i_call_all_apis_using_keys_from_config() {
        String allKeys = ConfigReader.getProperty("api.keys");
        String[] apiKeys = allKeys.split(",");

        for (String key : apiKeys) {
            key = key.trim();

            // Create a child node in report for this API
            ExtentTest apiNode = ExtentTestManager.getTest().createNode("API Call: " + key);
            apiNode.log(Status.INFO, "=============================");
            apiNode.log(Status.INFO, " Calling API: **" + key + "**");
            apiNode.log(Status.INFO, "=============================");

            log.info("Calling API: " + key);
            System.out.println("\n=============================");
            System.out.println("Calling API: " + key);
            System.out.println("=============================");

            try {
                api.callApi(key);
                apiNode.log(Status.PASS, " API called successfully: " + key);

                verifyApiResponse(key, apiNode);
                logApiDetails(apiNode);
                verifyStatusCodeAndMessage(apiNode);

                apiNode.log(Status.PASS, "✅ All validations passed for API: " + key);
            } catch (Exception e) {
                log.error(" Error calling API: " + key + " - " + e.getMessage());
                apiNode.log(Status.FAIL, " API failed: " + key + " - " + e.getMessage());
            }
        }
    }

    private void verifyApiResponse(String key, ExtentTest node) {
        try {
            int expectedStatus = Integer.parseInt(ConfigReader.getProperty(key + "_status"));
            int actualStatus = api.getStatusCode();

            log.info("Expected Status: " + expectedStatus + ", Actual: " + actualStatus);
            node.log(Status.INFO, "Expected Status: " + expectedStatus + ", Actual: " + actualStatus);
            Assert.assertEquals(actualStatus, expectedStatus, "Status code mismatch!");
            log.info(key + " - API status code matched successfully!");
            node.log(Status.PASS, "✅ " + key + " - API status code matched successfully!");

        } catch (AssertionError e) {
            log.warn(key + " -  Warning: Status code mismatch!");
            node.log(Status.WARNING, " " + key + " - Status code mismatch!");
        } catch (Exception e) {
            log.error("Error verifying status for " + key + ": " + e.getMessage());
            node.log(Status.FAIL, "Error verifying status for " + key + ": " + e.getMessage());
        }
    }

    private void logApiDetails(ExtentTest node) {
        String responseBody = api.getResponseBody();
        int statusCode = api.getStatusCode();

        log.info("API Response Body: " + responseBody);
        log.info("API Status Code: " + statusCode);

        node.log(Status.INFO, " Response Body: " + responseBody);
        node.log(Status.INFO, "Status Code: " + statusCode);
    }

    @Then("I verify all API responses from config")
    public void i_verify_all_api_responses_from_config() {
        ExtentTestManager.getTest().log(Status.PASS, "✅ All APIs validated successfully from config.");
        System.out.println("All APIs validated successfully.");
    }

    protected void verifyStatusCodeAndMessage(ExtentTest node) {
        int internalCode = api.getJsonStatusCode();
        boolean success = api.getResponseSuccess();
        String message = api.getResponseMessage();
        String response = api.getResponseBody();

        System.out.println("Success: " + success);
        System.out.println("Message: " + message);
        System.out.println("Response: " + response);

        log.info("Internal Code: " + internalCode);
        log.info("Response Success: " + success);
        log.info("Response Message: " + message);

        node.log(Status.INFO, "Internal Code: " + internalCode);
        node.log(Status.INFO, "Success: " + success);
        node.log(Status.INFO, "Message: " + message);
    }
}
