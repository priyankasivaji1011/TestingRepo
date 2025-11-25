package com.evry.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ElementsPage {

	private WebDriver driver;

	public ElementsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this); // IMPORTANT

	}

	@FindBy(xpath = "//*[@id=\"body\"]/header/div/div[2]/div/div/div[1]/button[2]")
	protected WebElement languageButton;

	@FindBy(xpath = "//*[@id=\"menu-mypages-button\"]")
	static protected WebElement loginText;

	@FindBy(id = "menu-products-button")
	static protected WebElement allProducts;

	@FindBy(xpath = "//a[text()='Accessories']")
	static protected WebElement accessories;

	@FindBy(xpath ="//*[@id='menu-products-dropdown']/div[2]/div[3]/a[2]")
	static protected WebElement showMore;

	@FindBy(xpath ="//*[@id='menu-products-dropdown']/div[2]/div[3]/ul/li[17]/a")
	static protected WebElement parallelCable;

	@FindBy(xpath ="//*[@id='es-result']/div[3]/ul/li[6]/div[2]/div[1]/div[3]")
	static protected WebElement prodOne;

	@FindBy(css ="ul.product-list__items > li")
	protected static List<WebElement> prodList;

	@FindBy(xpath ="//*[@id=\"es-result\"]/div[3]/ul/li[6]/div[2]/div[1]/div[3]")
	static protected WebElement prodPartNumber;

	@FindBy(xpath ="//*[@id=\"es-result\"]/div[3]/ul/li[4]")
	static protected WebElement quantity;

	@FindBy(xpath ="//*[@id=\"es-result\"]/div[3]/ul/li[7]/div[3]/button/i")
	static protected WebElement buyButton;

	@FindBy(xpath ="//*[@id=\"menu-products-dropdown\"]/div[1]/ul/li[2]")
	static protected WebElement serverSelection;

	@FindBy(xpath ="//*[@id=\"menu-products-dropdown\"]/div[2]/div[2]/ul/li[17]/a")
	static protected WebElement serverNetworkSelection;

	@FindBy(xpath ="//*[@id=\"menu-products-dropdown\"]/div[2]/div[2]/a[2]")
	static protected WebElement showServer;


	@FindBy(xpath="//*[@id=\"search-category-container\"]/input")
	static protected WebElement search;

	@FindBy(css =".product-list__description")
	static protected WebElement prodDescription;


	@FindBy(css =".product__stock-label")
	static protected WebElement prodStockNumber;


	@FindBy(xpath ="//*[@id=\"es-result\"]/div[3]/ul/li[1]/div[2]/div[1]/div[3]")
	static protected WebElement partNumber;


	@FindBy(xpath="//*[@id=\"es-result\"]/div[3]/ul/li/div[3]/div/input[1]")
	static protected WebElement qty;

	@FindBy(xpath ="//*[@id=\"es-result\"]/div[3]/ul/li/div[3]/button")
	static protected WebElement addToCart;

	@FindBy(xpath ="//button[contains(@class,'es-clear-all-filters-button')]")
	static protected WebElement clearFilter;

	@FindBy(xpath ="//*[@id=\"es-show-only\"]/fieldset/ul/li[1]/label")
	static protected WebElement stock;

	@FindBy(xpath ="//*[@id=\"es-manufacturers\"]/fieldset/ul/li[1]/label")
	static protected WebElement manufacturer;

	@FindBy(xpath ="//*[@id=\"es-designedFors\"]/fieldset/ul/li[1]/label")
	static protected WebElement designedBy;

	@FindBy(id ="slider-min-price")
	static protected WebElement price;


	@FindBy(xpath=".//div[@class='product-list__partnumber")
	static protected WebElement cmpPartNumber;


	@FindBy(xpath=".//div[contains(@class,'product__compare') and @title='Add to compare']")
	static protected WebElement compareBtn;



	@FindBy(xpath=".//div[@class='buy-quantity-container']/input[@name='forms.qty.quantity']")
	static protected WebElement compareQty;


	@FindBy(xpath="//*[@id=\\\"es-result\\\"]/div[3]/ul/li[1]/div[3]/button")
	static protected WebElement cmpAdd;


	@FindBy(css =".product-list__partnumber")
	static protected WebElement prodListPartNumber;


	@FindBy(css ="input[name='forms.qty.quantity']")
	static protected WebElement frmQty;
	
	
	@FindBy(css="button.buy-button")
	static protected WebElement addToCartBtn;

	public WebElement getAddToCartBtn() {
		return addToCartBtn;
	}

	public static void setAddToCartBtn(WebElement addToCartBtn) {
		ElementsPage.addToCartBtn = addToCartBtn;
	}

	public WebElement getFrmQty() {
		return frmQty;
	}

	public static void setFrmQty(WebElement frmQty) {
		ElementsPage.frmQty = frmQty;
	}

	public WebElement getProdListPartNumber() {
		return prodListPartNumber;
	}

	public static void setProdListPartNumber(WebElement prodListPartNumber) {
		ElementsPage.prodListPartNumber = prodListPartNumber;
	}

	public WebElement getCmpAdd() {
		return cmpAdd;
	}

	public static void setCmpAdd(WebElement cmpAdd) {
		ElementsPage.cmpAdd = cmpAdd;
	}

	public static WebElement getCompareQty() {
		return compareQty;
	}

	public static void setCompareQty(WebElement compareQty) {
		ElementsPage.compareQty = compareQty;
	}

	public WebElement getCompareBtn() {
		return compareBtn;
	}

	public static void setCompareBtn(WebElement compareBtn) {
		ElementsPage.compareBtn = compareBtn;
	}

	public WebElement getCmpPartNumber() {
		return cmpPartNumber;
	}

	public static void setCmpPartNumber(WebElement cmpPartNumber) {
		ElementsPage.cmpPartNumber = cmpPartNumber;
	}

	public static WebElement getStock() {
		return stock;
	}

	public static void setStock(WebElement stock) {
		ElementsPage.stock = stock;
	}

	public static WebElement getManufacturer() {
		return manufacturer;
	}

	public static void setManufacturer(WebElement manufacturer) {
		ElementsPage.manufacturer = manufacturer;
	}

	public static WebElement getDesignedBy() {
		return designedBy;
	}

	public static void setDesignedBy(WebElement designedBy) {
		ElementsPage.designedBy = designedBy;
	}

	public WebElement getPrice() {
		return price;
	}

	public static void setPrice(WebElement price) {
		ElementsPage.price = price;
	}

	public  WebElement getClearFilter() {
		return clearFilter;
	}

	public static void setClearFilter(WebElement clearFilter) {
		ElementsPage.clearFilter = clearFilter;
	}


	public WebElement getShowServer() {
		return showServer;
	}

	public  WebElement getQty() {
		return qty;
	}

	public static void setQty(WebElement qty) {
		ElementsPage.qty = qty;
	}

	public  WebElement getAddToCart() {
		return addToCart;
	}

	public static void setAddToCart(WebElement addToCart) {
		ElementsPage.addToCart = addToCart;
	}

	public static void setShowServer(WebElement showServer) {
		ElementsPage.showServer = showServer;
	}

	public WebElement getServerNetworkSelection() {
		return serverNetworkSelection;
	}

	public static void setServerNetworkSelection(WebElement serverNetworkSelection) {
		ElementsPage.serverNetworkSelection = serverNetworkSelection;
	}

	public  WebElement getServerSelection() {
		return serverSelection;
	}

	public static void setServerSelection(WebElement serverSelection) {
		ElementsPage.serverSelection = serverSelection;
	}

	public WebElement getBuyButton() {
		return buyButton;
	}

	public static void setBuyButton(WebElement buyButton) {
		ElementsPage.buyButton = buyButton;
	}

	public WebElement getQuantity() {
		return quantity;
	}

	public static void setQuantity(WebElement quantity) {
		ElementsPage.quantity = quantity;
	}

	public WebElement getProdStockNumber() {
		return prodStockNumber;
	}

	public static void setProdStockNumber(WebElement prodStockNumber) {
		ElementsPage.prodStockNumber = prodStockNumber;
	}

	public WebElement getProdDescription() {
		return prodDescription;
	}

	public static void setProdDescription(WebElement prodDescription) {
		ElementsPage.prodDescription = prodDescription;
	}

	public  WebElement getProdPartNumber() {
		return prodPartNumber;
	}

	public static void setProdPartNumber(WebElement prodPartNumber) {
		ElementsPage.prodPartNumber = prodPartNumber;
	}

	public List<WebElement> getProdList() {
		return prodList;
	}

	public static void setProdList(List<WebElement> prodList) {
		ElementsPage.prodList = prodList;
	}

	public WebElement getPartNumber() {
		return partNumber;
	}

	public static void setPartNumber(WebElement partNumber) {
		ElementsPage.partNumber = partNumber;
	}

	public  WebElement getProdOne() {
		return prodOne;
	}

	public static void setProdOne(WebElement prodOne) {
		ElementsPage.prodOne = prodOne;
	}

	public  WebElement getParallelCable() {
		return parallelCable;
	}

	public static void setParallelCable(WebElement parallelCable) {
		ElementsPage.parallelCable = parallelCable;
	}

	public WebElement getShowMore() {
		return showMore;
	}

	public static void setShowMore(WebElement showMore) {
		ElementsPage.showMore = showMore;
	}

	public WebElement getAccessories() {
		return accessories;
	}

	public static void setAccessories(WebElement accessories) {
		ElementsPage.accessories = accessories;
	}

	public static void setAllProducts(WebElement allProducts) {
		ElementsPage.allProducts = allProducts;
	}

	public WebElement getAllProducts() {
		return allProducts;
	}

	public WebElement getLanguageButton() {
		return languageButton;
	}

	public void setLanguageButton(WebElement languageButton) {
		this.languageButton = languageButton;
	}

	public WebElement getLoginText() {
		return loginText;
	}

	public static void setLoginText(WebElement loginText) {
		ElementsPage.loginText = loginText;
	}

	public WebElement getSearch() {
		return search;
	}

	public static void setSearch(WebElement search) {
		ElementsPage.search = search;
	}

}
