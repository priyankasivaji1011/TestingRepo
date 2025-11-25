package com.evry.bdd.pages;

import java.io.File;
import com.evry.bdd.utils.Log;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.evry.bdd.utils.ConfigReader;
import com.evry.bdd.utils.DriverManager;
import com.evry.bdd.utils.ExtentTestManager;
import com.evry.bdd.utils.GenericPage;
import com.evry.bdd.utils.LoggerHelper;
import com.evry.pageobjects.ElementsPage;

public class ProductListPage {

	private WebDriver driver;
	private static WebDriverWait wait;
	private static final Logger log = LoggerHelper.getLogger(ProductListPage.class);
	ElementsPage epage;

	GenericPage gpage;
	public ProductListPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		epage = new ElementsPage(driver);  
		gpage = new GenericPage(driver);
	}


	public void addToCart() {

		String partNumber=ConfigReader.getProperty("partNumber");
		WebDriver driver = DriverManager.getDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		try {
			log.info("Adding Products to the list");
			// Move to product section
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"es-result\"]/div[3]/ul/li[6]/div[2]/div[1]/div[3]")));
			js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
			element.click();
			// Get all products
			List<WebElement> productList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					By.cssSelector("ul.product-list__items > li")));
			boolean productFound = false;
			for (WebElement product : productList) {
				try {
					WebElement partNoElement = wait.until(ExpectedConditions.visibilityOf(
							product.findElement(By.cssSelector(".product-list__partnumber"))));

					if (partNoElement.getText().contains(partNumber)) {
						WebElement descriptionElem = wait.until(ExpectedConditions.visibilityOf(
								product.findElement(By.cssSelector(".product-list__description"))));
						WebElement stockElem = wait.until(ExpectedConditions.visibilityOf(
								product.findElement(By.cssSelector(".product__stock-label"))));

						Log.pass("Description: " + descriptionElem.getText());
						Log.pass("Stock: " + stockElem.getText());

						// Update quantity
						WebElement qtyInput = wait.until(ExpectedConditions.elementToBeClickable(
								product.findElement(By.cssSelector("input[name='forms.qty.quantity']"))));
						qtyInput.clear();
						qtyInput.sendKeys("2");

						// Add to cart
						WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
								product.findElement(By.cssSelector("button.buy-button"))));
						js.executeScript("arguments[0].click();", addToCartBtn);

						// Capture screenshot in Extent
						String screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
						ExtentTestManager.getTest().log(Status.PASS, "Product added successfully: " + partNumber);
						ExtentTestManager.getTest().addScreenCaptureFromBase64String(screenshot, "Product Added");

						productFound = true;
						break;
					}

				} catch (Exception e) {
					System.err.println("⚠️ Error handling product: " + e.getMessage());
				}
			}

			if (!productFound) {
				log.warn("Product with Part No: " + partNumber + " not found.");
			}

		} catch (Exception e) {
			String screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
			ExtentTestManager.getTest().log(Status.FAIL, "Error adding product: " + e.getMessage());
			ExtentTestManager.getTest().addScreenCaptureFromBase64String(screenshot, "Add Product Failure");
		}
	}


	public void navigateAndAdd() throws IOException {
		WebDriver driver = DriverManager.getDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		try {
			// Click "Products" menu
			WebElement productsMenu = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//*[@id='menu-products-button']")));
			js.executeScript("arguments[0].scrollIntoView({block:'center'});", productsMenu);
			productsMenu.click();

			// Click "Screens & Projectors"
			WebElement screensProjectors = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//a[contains(text(),'Screens & Projectors')]")));
			screensProjectors.click();

			// Click "Screens"
			WebElement screens = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//a[contains(@href,'/Projectors/Screens')]")));
			screens.click();

			// Sort by Price (cheapest first)
			WebElement sortDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.id("es-sort-order")));
			Select select1 = new Select(sortDropdown);
			select1.selectByVisibleText("Price (cheapest first)");

			// Select specific product
			WebElement productImg = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//img[@alt='DE-1123EGA - projection screen']")));
			productImg.click();

			// Increase quantity by 2
			WebElement plusBtn = wait.until(ExpectedConditions.elementToBeClickable(
					By.cssSelector("i.fas.fa-plus.basket-qty-plus")));
			js.executeScript("arguments[0].click();", plusBtn);
			js.executeScript("arguments[0].click();", plusBtn);

			// Add to cart
			WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//*[@id=\"body\"]/div[2]/div/main/div[3]/div[2]/div[1]/span/div[2]/div[2]/div/table/tbody/tr/td[4]/div/p/button")));
			js.executeScript("arguments[0].click();", addToCartBtn);

			// Open mini basket
			WebElement miniBasket = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//*[@id=\"menu-minibasket-button\"]/div[1]/div")));
			js.executeScript("arguments[0].click();", miniBasket);

			// Click "To checkout"
			WebElement checkoutLink = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//*[@id=\"menu-minibasket-dropdown\"]/div/div[3]/div/a[2]")));
			js.executeScript("arguments[0].click();", checkoutLink);

			// Optional: click "download file" or perform other actions
			WebElement downloadLink = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//*[@id=\"body\"]/div[2]/div/main/div[3]/div/div/div[2]/div[4]/a")));
			js.executeScript("arguments[0].click();", downloadLink);

			log.info("Reading the downloaded file and writing to log");

			// Read the latest downloaded Excel file
			File downloadFolder = new File("C:\\Users\\EI06754\\Downloads");
			File[] files = downloadFolder.listFiles();
			File latestFile = null;

			for (File file : files) {
				if (file.isFile() && file.getName().endsWith(".xls")) {
					if (latestFile == null || file.lastModified() > latestFile.lastModified()) {
						latestFile = file;
					}
				}
			}

			if (latestFile != null) {
				FileInputStream fis = new FileInputStream(latestFile);
				Workbook workbook = new HSSFWorkbook(fis);
				Sheet sheet = workbook.getSheetAt(0);

				for (Row row : sheet) {
					for (Cell cell : row) {
						System.out.print(cell + "\t");
						log.info(cell + "\t");
					}
					System.out.println();
				}

				workbook.close();
				fis.close();
			} else {
				log.warn("No Excel file found in Downloads folder!");
				System.out.println("No Excel file found in Downloads folder!");
			}

		} catch (Exception e) {
			String screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
			ExtentTestManager.getTest().log(Status.FAIL, "Error during navigation/add: " + e.getMessage());
			ExtentTestManager.getTest().addScreenCaptureFromBase64String(screenshot, "Navigate/Add Failure");
		}
	}


	public void filterByOptions() throws InterruptedException {

		String optionGroup = ConfigReader.getProperty("filter.showOnly.group");
		String filterValue = ConfigReader.getProperty("filter.showOnly.value");
		Thread.sleep(600);
		Log.pass("Filtering by Show & Maufacturers");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Map filterGroup name to container ID
		Map<String, String> containerMap = Map.of("Show only", "es-show-only","Manufacturer", "es-manufacturers");
		String container = containerMap.get(optionGroup);
		if (container == null) {
			throw new RuntimeException("No container found for filter group: " + optionGroup);
		}
		// Scroll section into view
		WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(container)));
		js.executeScript("arguments[0].scrollIntoView({block:'center'});", section);
		Thread.sleep(600);

		// Expand section if collapsed
		List<WebElement> headerList = driver.findElements(
				By.xpath("//div[@id='" + container + "']/preceding-sibling::div[contains(@class,'filter-header')]"));
		if (!headerList.isEmpty() && headerList.get(0).getAttribute("class").contains("closed")) {
			js.executeScript("arguments[0].click();", headerList.get(0));
			Thread.sleep(600);
		}

		// Click 'Show more' if exists
		List<WebElement> showMoreList = driver.findElements(By.xpath("//div[@id='" + container + "']//button[contains(normalize-space(),'Show more')]"));
		if (!showMoreList.isEmpty()) {
			js.executeScript("arguments[0].click();", showMoreList.get(0));
			Thread.sleep(600);
		}

		// Locate the label directly
		WebElement optionLabel = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='" + container + "']//label[contains(normalize-space(),'" + filterValue.trim() + "')]")));

		// Click using JS
		js.executeScript("arguments[0].click();", optionLabel);
		Thread.sleep(1000);

		// Wait for products reload (spinner disappears)
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".content-spinner")));
		} catch (Exception ignored) {}

		System.out.println("✅ Selected filter: " + filterValue + " under " + optionGroup);
		//setPriceRange(100, 1000);
	}


	public void compareProduct() throws InterruptedException {
		Thread.sleep(1000);
		Thread.sleep(1000);
		List<String> partNumbers = Arrays.asList("U3GX4E", "U3WY8E" ,"H1YP0E"); // add more part numbers as needed

		for (String partNumber : partNumbers) {
			boolean found = false;

			List<WebElement> productList = driver.findElements(By.cssSelector("ul.product-list__items > li"));

			for (WebElement product : productList) {
				try {
					WebElement partNoElement = product.findElement(By.xpath(".//div[@class='product-list__partnumber']"));
					String actualPart = partNoElement.getText().replace("Part no: ", "").trim();

					if (actualPart.equals(partNumber)) {
						// Get description and stock
						String description = product.findElement(By.cssSelector(".product-list__description")).getText();
						String stock = product.findElement(By.cssSelector(".product__stock-label")).getText();
						Log.pass("Found Part: " + partNumber);
						Log.pass("Description: " + description);
						Log.pass("Stock: " + stock);

						System.out.println("Found Part: " + partNumber);
						System.out.println("Description: " + description);
						System.out.println("Stock: " + stock);


						WebElement compareBtn = product.findElement(By.xpath(".//div[contains(@class,'product__compare') and @title='Add to compare']"));
						compareBtn.click();
						Thread.sleep(200);
						Thread.sleep(200);
						driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
						// Update quantity to 2
						WebElement qtyInput = product.findElement(By.xpath(".//div[@class='buy-quantity-container']/input[@name='forms.qty.quantity']"));
						qtyInput.click();
						qtyInput.clear();
						Thread.sleep(200);
						qtyInput.sendKeys("2");

						// Click Add to Cart button
						WebElement addToCartBtn = product.findElement(By.xpath("//*[@id=\"es-result\"]/div[3]/ul/li[1]/div[3]/button"));
						addToCartBtn.click();

						found = true;

						break; // Stop searching after finding the current part number


					}

				} catch (StaleElementReferenceException e) {
					System.out.println("⚠️ Stale element, retrying...");
				} catch (Exception e) {
					System.out.println("⚠️ Error handling product: " + e.getMessage());
				}
			}

			if (!found) {
				System.out.println("Product with Part No: " + partNumber + " not found.");
				Log.pass( "Product with Part No: " + partNumber + " not found.");
			}

			// Optional: small wait to allow page/cart to update
			Thread.sleep(1000);
		}
	}

	public void clickToCompareProducts() throws InterruptedException {
		WebDriver driver = DriverManager.getDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Handle cookie popup if present
		gpage.handleCookiePopupIfPresent();

		// Wait for any spinner to disappear
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.content-spinner")));

		// Click the compare icon in header
		WebElement cmpButton = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//*[@id=\"body\"]/header/div/div[2]/div/div/div[4]/i")));
		safeClick(cmpButton);

		// Get all products in comparison table
		List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.cssSelector("thead.product-comparison__header th[scope='col'] .product-comparison-product")));

		double maxPrice = 0;
		WebElement maxPriceProduct = null;

		// Find the product with the highest price
		for (WebElement product : products) {
			try {
				String priceText = product.findElement(By.cssSelector(".product-comparison-price .price")).getText();
				priceText = priceText.replace("kr", "").replace(",", "").trim();
				double price = Double.parseDouble(priceText);

				if (price > maxPrice) {
					maxPrice = price;
					maxPriceProduct = product;
				}
			} catch (Exception e) {
				System.out.println("⚠️ Could not parse price for a product: " + e.getMessage());
			}
		}

		// Click "Add to Cart" for the highest price product
		if (maxPriceProduct != null) {
			System.out.println("Highest price: " + maxPrice);
			Log.pass("Highest price product is added to cart: " + maxPrice);
			WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
					maxPriceProduct.findElement(By.cssSelector("button.buy-button"))));
			safeClick(addToCartBtn);
		}

		// Wait for spinner after adding product
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.content-spinner")));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.findElement(By.xpath("//div[@class='minibasket-icon-linkbox']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.findElement(By.xpath("//a[normalize-space()='To checkout']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.findElement(By.xpath("//button[normalize-space()='Empty']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.findElement(By.xpath("//a[normalize-space()='Ok']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath("//div[@title='Vivicta']")).click();

	}

	/**
	 * Utility method to safely click a WebElement using JS executor
	 */
	private void safeClick(WebElement element) {
		try {
			WebDriver driver = DriverManager.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			JavascriptExecutor js = (JavascriptExecutor) driver;

			wait.until(ExpectedConditions.elementToBeClickable(element));
			js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
			js.executeScript("arguments[0].click();", element);
		} catch (Exception e) {
			System.out.println("⚠️ Could not click element: " + e.getMessage());
		}
	}
}











;
