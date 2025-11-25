package com.evry.bdd.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.evry.pageobjects.ElementsPage;

public class GenericPage {
	WebDriver driver;
	private static WebDriverWait wait;
	ElementsPage epage;
	private static final Logger log = LoggerHelper.getLogger(GenericPage.class);
	public GenericPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		epage = new ElementsPage(driver);  
	}

	public static void pageRefresh(WebDriver driver) {
		driver.navigate().refresh();
	}

	public static void driverWait(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
	}

	public static void clickElement(WebDriver driver, By element) {
		driver.findElement(element).click();
	}

	public boolean openURL(String url) {
		driver.get(url);
		log.info("Logged in Sucessfully" +ConfigReader.getProperty("url"));
		return true;
	}

	public void changeLanguage() {
		epage.getLanguageButton().click();
		log.info("Language Changed to English");
		takeScreenshot("test");
		handleCookiePopupIfPresent();
		
	}


	public void WhenloginTextExists() throws InterruptedException {
		Thread.sleep(10000);
		epage.getLoginText().click();
	}


	public void selectElementsFromList(String productName) throws InterruptedException {
		log.info("Selecting Elements from AllProducts");
		WebElement allProducts = wait.until(ExpectedConditions.elementToBeClickable(epage.getAllProducts()));
		GenericPage.action(driver, allProducts);
		if(productName.contains("Accessories")) {
			log.info("Selecting  productName from AllProducts");
			WebElement accessoriesCategory = wait.until(ExpectedConditions.elementToBeClickable(epage.getAccessories()));
			GenericPage.action(driver, accessoriesCategory);
			WebElement showButton = wait.until(ExpectedConditions.elementToBeClickable(epage.getShowMore()));
			GenericPage.action(driver, showButton);
			WebElement link = wait.until(ExpectedConditions.elementToBeClickable(epage.getParallelCable()));
			GenericPage.action(driver, link);

		} else if (productName.contains("Server & Network")) {
			log.info("Selecting +"+productName+"+ from AllProducts");
			WebElement serverSelection = wait.until(ExpectedConditions.elementToBeClickable(epage.getServerSelection()));
			Thread.sleep(1000);
			GenericPage.action(driver, serverSelection);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			WebElement showButton = wait.until(ExpectedConditions.elementToBeClickable(epage.getShowServer()));
			GenericPage.action(driver, showButton);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			WebElement link1 = wait.until(ExpectedConditions.elementToBeClickable(epage.getServerNetworkSelection()));
			GenericPage.action(driver, link1);
		}
	}

	public static void action(WebDriver driver ,WebElement element) throws InterruptedException {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}

	public void search() throws InterruptedException {
		WebElement searchElement = epage.getSearch();
		searchElement.sendKeys(ConfigReader.getProperty("searchPartNumber"));
		searchElement.sendKeys(Keys.ENTER);
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"es-result\"]/div[3]/ul/li/div[2]/div[4]/div/div[1]"))).click();
	}

	public void updateQty(String quantity) {
		WebElement qty = wait.until(ExpectedConditions.elementToBeClickable(epage.getQty()));
		qty.clear();
		qty.sendKeys(quantity);
	}

	public void addToCart() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(epage.getAddToCart())).click();
	}

	public void clearFilter() {
		waitForSpinnerToDisappear(driver);
		WebElement filterElement = epage.getClearFilter();
		filterElement.click();
		filterElement.clear();

	}
	public static void waitForSpinnerToDisappear(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".content-spinner")));
	}

	public void filterByPrice() {
		WebElement minInput = driver.findElement(By.id("slider-min-price"));
		WebElement maxInput = driver.findElement(By.id("slider-max-price"));
		minInput.clear();
		minInput.sendKeys("18883");
		maxInput.clear();
		maxInput.sendKeys("18884");
		// Press Enter or confirm filter
		maxInput.sendKeys(Keys.ENTER);
		takeScreenshot("filter");
	}
	
	public void clickMiniBasket() {
	    WebElement miniBasket = driver.findElement(By.xpath("//*[@id=\"menu-minibasket-dropdown\"]/div/div[3]/div/a[2]"));
	    miniBasket.click();
	    takeScreenshot("cart");
	}

	public void clickOkOnModal() {
		// TODO Auto-generated method stub
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.modal-confirm-ok")));
		    okButton.click();
	}
	
	 
	 
	public void takeScreenshot(String name) {
	    try {
	        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	        String filePath = "test-output/screenshots/" + name + "_" + timestamp + ".png";
	        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	        Files.copy(srcFile.toPath(), Paths.get(filePath));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public void clickWhenReady(WebElement element, By spinner) {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(spinner));
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}
	public void handleCookiePopupIfPresent() {
	    WebDriver driver = DriverManager.getDriver();
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

	    try {
	        // Wait up to 5 seconds for the cookie popup
	        WebElement acceptCookies = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
	        if (acceptCookies.isDisplayed()) {
	            acceptCookies.click();
	            System.out.println("✅ Accepted cookies popup");
	            // Optional: wait until overlay disappears
	            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("onetrust-button-group")));
	        }
	    } catch (Exception e) {
	        // Popup not present, proceed normally
	        System.out.println("⚠️ Cookies popup not present");
	    }
	}

}