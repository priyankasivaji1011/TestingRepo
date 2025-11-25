package com.evry.bdd.steps;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.evry.bdd.pages.ProductListPage;
import com.evry.bdd.utils.ConfigReader;
import com.evry.bdd.utils.DriverManager;
import com.evry.bdd.utils.ExtentTestManager;
import com.evry.bdd.utils.GenericPage;
import com.evry.bdd.utils.Log;
import com.evry.pageobjects.ElementsPage;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
public class ProductSteps {

	WebDriver driver = DriverManager.getDriver();
	GenericPage gpage = new GenericPage(driver);
	ElementsPage epage = new ElementsPage(driver);  
	ProductListPage productListPage = new ProductListPage(driver);
	private String productName;  

	@Given("user launches the application")
	public void user_launches_the_application() {
		String url = ConfigReader.getProperty("url");
		boolean status = gpage.openURL(url);
		if(status){
			Log.pass("✅ Application launched successfully: " + url);
			ExtentTestManager.getTest().log(Status.INFO, ">>> This is a test log <<<");
			System.out.println("Log printed");
		} else {
			Log.fail("❌ Failed to launch application");
		}
	}


	@When("user changes the language")
	public void changLanguage() throws InterruptedException{
		ExtentTestManager.getTest().log(Status.INFO, "Changing language");
		gpage.changeLanguage();

		// ✅ Take screenshot after language change
		try {
			String screenshotBase64 = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
			ExtentTestManager.getTest().addScreenCaptureFromBase64String(screenshotBase64, "Language Changed Successfully");
		} catch (Exception e) {
			ExtentTestManager.getTest().log(Status.WARNING, "Screenshot capture failed after language change");
		}
	}


	@Then("validate page title")
	public void validatePageTitle() {
		try {
			WebDriver driver = DriverManager.getDriver(); // use your active driver
			String actualTitle = driver.getTitle();
			String expectedTitle = ConfigReader.getProperty("tempUrl");
			String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
			if (!actualTitle.equals(expectedTitle)) {
				// Capture screenshot as Base64

				ExtentTestManager.getTest().log(Status.FAIL,
						"Page title mismatch. Expected: " + expectedTitle + ", Actual: " + actualTitle);
				ExtentTestManager.getTest().addScreenCaptureFromBase64String(base64Screenshot, "Title Mismatch");
				System.err.println("Page title mismatch. Expected: " + expectedTitle + ", Actual: " + actualTitle);
			} else {
				ExtentTestManager.getTest().log(Status.PASS, "Page title validated successfully: " + actualTitle);
				System.out.println("Page title validated successfully: " + actualTitle);
				ExtentTestManager.getTest().addScreenCaptureFromBase64String(base64Screenshot, "Title Mismatch");
			}
		} catch (Exception e) {
			ExtentTestManager.getTest().log(Status.WARNING, "Exception during title validation: " + e.getMessage());
		}
	}



	@When("login text exists")
	public void WhenloginTextExists() throws InterruptedException {
		Log.info("Checking if login text exists");
		gpage.WhenloginTextExists();
		Log.pass("Login text verified successfully");
	}


	@Then("select accessories from the panel {string}")
	public void selectElementsFromList(String product) throws InterruptedException {
		this.productName = product;
		Log.info("Selecting product: " + productName);
		gpage.selectElementsFromList(productName);
		Log.pass("Selected product successfully: " + productName);
	}


	@Then("add to Cart")
	public void addToCart() throws InterruptedException {
		Log.info("Adding product to cart");
		productListPage.addToCart();
		Log.pass("Product added to cart");
	}

	@Then("navigate to other screen and add product")
	public void addToCartFromOtherScreen() throws InterruptedException, IOException {
		Log.info("Navigating to second screen to add product");
		productListPage.navigateAndAdd();
		Log.pass("Product added from second screen");
	}


	@Then("search by partnumber and update and add to cart")
	public void searchByPartNumber() throws InterruptedException, IOException {
		Log.info("Searching product by part number");
		gpage.search();
		gpage.updateQty("15");
		gpage.addToCart();
		gpage.clearFilter();
		Log.pass("Search and add to cart completed successfully");
	}

	@When("select filters")
	public void selectFilters() throws InterruptedException {
		productListPage.filterByOptions();
		Log.pass("All filters applied successfully");
	}

	@Then("compare products and add to card and checkout")
	public void compareProdCheckOutMultiple() throws InterruptedException {
		Log.info("Comparing products and proceeding to checkout");
		productListPage.compareProduct();
		Log.pass("Compared products and checkout process started");
	}


	@Then("countnumberoflinks")

	@Then("click to compare products")
	public void clickToCompareProducts() throws InterruptedException {
		Log.info("Clicking compare button");
		productListPage.clickToCompareProducts();
		Log.pass("Compare button clicked successfully");
	}

 
}
