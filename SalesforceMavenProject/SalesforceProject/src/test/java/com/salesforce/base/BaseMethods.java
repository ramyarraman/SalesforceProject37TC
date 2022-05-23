package com.salesforce.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Parameters;

import com.salesforce.utilities.CommonUtilities;
import com.salesforce.utilities.GenerateReports;
import com.salesforce.utilities.SalesForceConstants;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseMethods {

	protected static WebDriver driver;
	protected static String locatorName, locatorValue;
	protected static SoftAssert softassertObject = new SoftAssert();
	public static GenerateReports report;
	
	@BeforeTest
	public void setUpTestAllTest() {
		report = GenerateReports.getInstance();	
//		report.startExtentReport();
	}

	@BeforeMethod
	@Parameters("browser")
	public void setUpForAllMethods(String browser, Method method) {
		report.startSingleTestReport(method.getName());
		launchAndOpenBrowser(browser);
		goToSFLoginPage();	
	}

//	@AfterMethod
//	public void actionAfterTest() {
//		webDriverWait(5);
//		closeAllDriver();
//	}	
	@DataProvider(name="TC19data")
	public  String[][] TC19() {
		return new String[][]  {{"Current FQ","all"},
								{"Current FQ","open"},
								{"Current FQ","closed"},
								{"Next FQ","all"},
								{"Next FQ","open"},
								{"Next FQ","closed"}};			
	}
	
	/* Name of the method: launchAndOpenBrowser
	 * Brief Description : create browser instance based on the browser name passed.
	 * Arguments 		 : browser name
	 * created by	 	 : Ramya
	 * created date		 : 5/7/2022
	 * Last Modified date: 5/7/2022
	 */

	public static void launchAndOpenBrowser(String browser) {

		switch(browser.toLowerCase()) {

		case "chrome":		
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		case "firefox": 
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "safari":
			driver = new SafariDriver();
			break;
		default:
			System.out.println("Invalid browser");
//			report.logTestInfo("Invalid Browser");
		}
		System.out.println("Launched browser"+browser);
//		report.logTestInfo(browser+"browser launched");
		driver.manage().window().maximize();	
	}
	//Go to SalesForce login page
	public static void goToSFLoginPage() {
		String url = CommonUtilities.getProperty("url",SalesForceConstants.SALESFORCE_PROPERTIES_PATH);
		goToUrl(url);
	}
	//go to any page
	public static void goToPage(String pageLinkNameAsInPropertiesFile) {
		WebElement pageName = findElementBy(pageLinkNameAsInPropertiesFile);
		clickOnElement(pageName,pageLinkNameAsInPropertiesFile);
	}

	//Get url.................................................
	public static void goToUrl(String url) {
		driver.get(url);
		System.out.println("Fetched url "+url);
//		report.logTestInfo("Fetched url "+url);
	}

	//SalesForce login
	public static void loginSalesforce(boolean checkBoxSelection){

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

		String userNameValue = CommonUtilities.getProperty("userName",SalesForceConstants.SALESFORCE_PROPERTIES_PATH);

		WebElement userName = findElementBy("userName");
		clearText(userName);
		enterText(userName,userNameValue);

		String passwordValue = CommonUtilities.getProperty("password",SalesForceConstants.SALESFORCE_PROPERTIES_PATH);
		WebElement password = findElementBy("password");
		clearText(password);
		enterText(password,passwordValue);

		WebElement rememberMe = findElementBy("rememberMeCheckBox");

		//select check box if checkBoxSelection is true
		if(checkBoxSelection) {	
			if(!rememberMe.isSelected())
				rememberMe.click();
		}else {
			if(rememberMe.isSelected())
				rememberMe.click();			
		}

		webDriverWait(5);
		WebElement loginButton= findElementBy("loginButton");		
		clickOnElement(loginButton,"loginButton");	

		System.out.println("Login clicked");
		report.logTestInfo("Login clicked");
	}
	//Log out of salesforce
	public static void logoutSalesForce() throws InterruptedException {
		WebElement userMenu = findElementBy("userMenuDropDown");
		checkIfElementDisplayedByLocator("userMenuDropDown");
		clickOnElement(userMenu,"userMenu");
		
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ARROW_DOWN)
		.sendKeys(Keys.ARROW_DOWN)
		.sendKeys(Keys.ARROW_DOWN)
		.click()
		.build().perform();
		
//		WebElement logout = findElementBy("logout");
//		mouseOverAndClickUsingActions(logout, "logout");
//		clickOnElement(logout,"logout");
//		checkIfCorrectPageDisplayed("loginPage");
	}
	//get tab bar options
	public static List<String> getTabBarOptions() {		
		List<WebElement >tabBarOptionslist=findElementsBy("tabBarOptions");
		List<String> tabBarOptionsTextlist= new ArrayList<String>();
		for(WebElement option:tabBarOptionslist) {
			tabBarOptionsTextlist.add(option.getText());
//			System.out.println(option.getText());
		}
		return tabBarOptionsTextlist;
	}

	//GetTitle of page 
	public static String getPageTitle() {
		return driver.getTitle();
	}
	//Check if correct page is displayed
	public static void checkIfCorrectPageDisplayed(String pageNameAsInPropertiesFile) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String expectedTitle = CommonUtilities.getProperty(pageNameAsInPropertiesFile,SalesForceConstants.SALESFORCE_PROPERTIES_PATH);
		//		System.out.println("ExpectedTitle: "+ExpectedTitle);
		By locator = By.tagName("title");
		waitSecondsForElementLocatedBy(30,locator);
		String actualTitle = driver.getTitle();
		System.out.println("Actual Title Displayed --  "+actualTitle);
		report.logTestInfo("Actual Title Displayed --  "+actualTitle);
		softassertObject.assertEquals(actualTitle, expectedTitle);
		

	}		

	public static void checkIfCorrectPageWithNameisDisplayed(WebElement nameInPage, String pageNameAsInPropertiesFile) {

		String expectedTitle = CommonUtilities.getProperty(pageNameAsInPropertiesFile,SalesForceConstants.SALESFORCE_PROPERTIES_PATH);
		String actualName = getText(nameInPage);
		expectedTitle = expectedTitle.replaceFirst("@",actualName);

		String actualTitle = getPageTitle();
		Assert.assertEquals(actualTitle, expectedTitle);
	}

	//Check UserMenu dropdown contents
	public static void checkUserMenuDropDownOptions() {

		WebElement userMenu = findElementBy("userMenuDropDown");
		checkIfElementDisplayedByLocator("userMenuDropDown");
		clickOnElement(userMenu,"userMenu");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<WebElement> dropDownEntries = findElementsBy("xpath","//div[@id='userNav-menuItems']/a");
		ArrayList<String> dropDownEntryTexts = new ArrayList<String>();

		for(WebElement value:dropDownEntries) {			
			String text = value.getText();
			dropDownEntryTexts.add(text);
		}	

		ArrayList<String> expectedOptions = new ArrayList<String> (Arrays.asList(SalesForceConstants.USER_MENU_DROPDOWN_OPTIONS));
		System.out.println("dropDownEntryTexts"+dropDownEntryTexts);
		System.out.println("expectedOptions"+expectedOptions);
//		report.logTestInfo("dropDownEntryTexts"+dropDownEntryTexts);
//		report.logTestInfo("expectedOptions"+expectedOptions);
		
		boolean flag = dropDownEntryTexts.containsAll(expectedOptions);

		//System.out.println(flag);
		softassertObject.assertTrue(flag,"Drop down options not as expected");
		softassertObject.assertAll();
	}
	//Waits................................................
	public static void waitForPageToLoad(int seconds) {
		driver.manage().timeouts().pageLoadTimeout(seconds,TimeUnit.SECONDS);
	}
	public static void waitSecondsForElementToBeClickable(int seconds, String  elementNameAsInObjectRepository) {
		WebElement webElement = findElementBy(elementNameAsInObjectRepository);
		new WebDriverWait(driver,seconds).until(ExpectedConditions.elementToBeClickable(webElement));
	}

	public static void waitSecondsForElementToBeClickable(int seconds, WebElement webElement) {
		new WebDriverWait(driver,seconds).until(ExpectedConditions.elementToBeClickable(webElement));
	}

	public static void waitSecondsForElementVisible(int seconds, String  elementNameAsInObjectRepository) {
		WebElement webElement = findElementBy(elementNameAsInObjectRepository);
		new WebDriverWait(driver,seconds).until(ExpectedConditions.visibilityOf(webElement));
	}

	public static void waitSecondsForElementVisible(int seconds, WebElement webElement) {
		new WebDriverWait(driver,seconds).until(ExpectedConditions.visibilityOf(webElement));
	}

	public static void waitSecondsForElementLocatedBy(int seconds, By locator) {
		new WebDriverWait(driver,seconds).until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	public static void webDriverWait(int seconds) {
		new WebDriverWait(driver,seconds);
	}
	//Element manipulaions.................................

	//Find element
	public static WebElement findElementBy(String elementNameAsInObjectRepository) {
		locatorName = CommonUtilities.getProperty(elementNameAsInObjectRepository+"Locator", SalesForceConstants.SALESFORCE_OBJECTREPOSITORY_PATH);
		locatorValue = CommonUtilities.getProperty(elementNameAsInObjectRepository+"LocatorValue", SalesForceConstants.SALESFORCE_OBJECTREPOSITORY_PATH);
		return findElementBy(locatorName,locatorValue);
	}

	public static WebElement findElementBy(String locatorName,String locatorValue) {
		By locator=null;
		switch(locatorName.toLowerCase()) {
		case "id":
			locator = By.id(locatorValue);
			break;
		case "name":
			locator = By.name(locatorValue);
			break;
		case "classname":
			locator = By.className(locatorValue);
			break;
		case "tagname":
			locator = By.tagName(locatorValue);
			break;
		case "linktext":
			locator = By.linkText(locatorValue);
			break;
		case "partiallinktext":
			locator = By.partialLinkText(locatorValue);
			break;	
		case "xpath":		
			locator = By.xpath(locatorValue);
			break;
		case "cssselector":
			locator = By.cssSelector(locatorValue);
			break;
		default:
			System.out.println("Invalid locator");
			report.logTestInfo("Invalid Locator");
		}
		waitSecondsForElementLocatedBy(30,locator);
		return driver.findElement(locator);
	}
	//find elements

	public static List<WebElement> findElementsBy(String elementNameAsInObjectRepository) {
		locatorName = CommonUtilities.getProperty(elementNameAsInObjectRepository+"Locator", SalesForceConstants.SALESFORCE_OBJECTREPOSITORY_PATH);
		locatorValue = CommonUtilities.getProperty(elementNameAsInObjectRepository+"LocatorValue", SalesForceConstants.SALESFORCE_OBJECTREPOSITORY_PATH);
		return findElementsBy(locatorName,locatorValue);
	}
	public static List<WebElement> findElementsBy(String locatorName, String locatorValue) {
		By locator=null;
		switch(locatorName.toLowerCase()) {
		case "id":
			locator = By.id(locatorValue);
			break;
		case "name":
			locator = By.name(locatorValue);
			break;
		case "classname":
			locator = By.className(locatorValue);
			break;
		case "tagname":
			locator = By.tagName(locatorValue);
			break;
		case "linktext":
			locator = By.linkText(locatorValue);
			break;
		case "partiallinktext":
			locator = By.partialLinkText(locatorValue);
			break;	
		case "xpath":		
			locator = By.xpath(locatorValue);
			break;
		case "cssselector":
			locator = By.cssSelector(locatorValue);
			break;
		default:
			System.out.println("Invalid locator");	
			report.logTestInfo("Invalid locator");
		}
		waitSecondsForElementLocatedBy(30,locator);
		return driver.findElements(locator);
	}
	//Enter text in webElement
	public static void enterText(WebElement webElement,String text) {
		clearText(webElement);
		webElement.sendKeys(text);
		System.out.println("Text Entered");
//		report.logTestInfo("Text Entered");
	}
	//click on Element
	public static void clickOnElement(WebElement webElement,String elementName) {
		new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(webElement));
		webElement.click();
		System.out.println("clicked on "+elementName);
		report.logTestInfo("clicked on "+elementName);
	}
	//clear the text 
	public static void clearText(WebElement webElement) {
		webElement.clear();
	}
	//check if the element is selected
	public static Boolean checkifSelected(WebElement webElement) {
		return webElement.isSelected();
	}
	//get the text from webElement
	public static String getText(WebElement webElement) {
		System.out.println("Text from webElement: "+webElement.getText());
		return webElement.getText();
	}
	//get text by Locator
	public static String getTextByLocator(By locator) {
		System.out.println("Text from locator: "+driver.findElement(locator).getText());
		return driver.findElement(locator).getText();
	}
	//get the attribute value of the webelement
	public static String getAttributeValueOfElement(WebElement webElement,String attributeName) {
		//		System.out.println(attributeName+" of webElement: "+webElement.getAttribute(attributeName));
		return webElement.getAttribute(attributeName);
	}

	//Check if element is displayed
	public static boolean checkIfElementDisplayedByLocator(String locatorName,String locatorValue) {
		return findElementBy(locatorName,locatorValue).isDisplayed();
	}

	public static boolean checkIfElementDisplayedByLocator(String elementNameAsInObjectRepository ) {
		return findElementBy(elementNameAsInObjectRepository).isDisplayed();
	}
	//Check if element is selected
	public static boolean checkIfElementSelectedByLocator(String locatorName,String locatorValue) {
		return findElementBy(locatorName,locatorValue).isSelected();
	}
	public static boolean checkIfElementSelected(String elementNameAsInObjectRepository) {
		return findElementBy(elementNameAsInObjectRepository).isSelected();
	}
	public static boolean checkIfElementSelected(WebElement webElement) {
		return webElement.isSelected();
	}
	//Action class..........................................
	public static void dragAndDrop(WebElement webElementSource,WebElement webElementTarget) {
		Actions action = new Actions(driver);
		action.dragAndDrop(webElementSource, webElementTarget).build().perform();;

		//		action.clickAndHold(webElementSource)
		//		.moveToElement(webElementTarget)
		//		.release()
		//		.build().perform();
	}

	public static void mouseOverUsingActions(WebElement webElement) {
		Actions action = new Actions(driver);
		action.moveToElement(webElement);
	}
	public static void mouseOverAndClickUsingActions(WebElement webElement,String webElementName) {
		Actions action = new Actions(driver);
		action.moveToElement(webElement).click().build().perform();
		System.out.println("clicked on "+webElementName);
		report.logTestInfo("clicked on "+webElementName);
	}

	public static void doubleClickUsingActions(WebElement webElement,String webElementName) {
		Actions action = new Actions(driver);
		action.doubleClick(webElement);
		System.out.println("Double clicked on "+webElementName);
		report.logTestInfo("Double clicked on "+webElementName);
	}
	//Get drop down visible options using select
	public static List<String> getDropDownOptionsTextUsingSelect(WebElement dropDown){
		Select select = new Select(dropDown);
		List<WebElement> dropDownOptions = select.getOptions();
		List<String> dropDownOptionsText = new ArrayList<String>();
		for(WebElement option: dropDownOptions) {
			dropDownOptionsText.add(option.getText());
			//			System.out.println("option: "+option.getText());
		}
		return dropDownOptionsText;
	}

	//Get the selected dropdown option using select
	public static String getTheSelectedDropDownOptionUsingSelect(WebElement webElement) {
		Select select = new Select(webElement);
		return getText(select.getFirstSelectedOption());		
	}

	//select the dropdownoption based on visible text
	public static void selectOptionByVisibleText(WebElement webElement,String visibleText) {
		Select select = new Select(webElement);
		select.selectByVisibleText(visibleText);
		System.out.println("Option Selected:"+visibleText);		
		report.logTestInfo("Option Selected:"+visibleText);
	}

	public static void selectOptionByValue(WebElement webElement,String valueAttribute) {
		Select select = new Select(webElement);
		select.selectByValue(valueAttribute);
		System.out.println("Option Selected:"+valueAttribute);
		report.logTestInfo("Option Selected:"+valueAttribute);
	}
	
	public static boolean clickOnNonSelectTagDropDownUsingVisibleText(String elementNameAsInObjectRepository,String visibleText) {
		List<WebElement> webElementOptions = findElementsBy(elementNameAsInObjectRepository);
		boolean flag=false;
		for(WebElement option: webElementOptions ) {
			String optionText = option.getText();
//			System.out.println(optionText);
			if(optionText.equals(visibleText)) {
				clickOnElement(option,optionText);
				flag=true;
				break;
			}			
		}
		return flag;
	}
	//switch to window using windowHandle
	public static void switchToWindow(String handle) {
		driver.switchTo().window(handle);
	}
	//Switch to another Window
	public static void switchToNewWindow() {
		String currentHandle = driver.getWindowHandle();
		System.out.println("currentHandle"+currentHandle.toString());
		Set<String> handles = driver.getWindowHandles();
		System.out.println("All Handles: "+handles.toString());
		for(String handle:handles) {
			if(!(handle.equals(currentHandle))) {
				driver.switchTo().window(handle);
				System.out.println("Window Switched");
//				report.logTestInfo("Window Switched");
			}
		}		
	}

	//get curentWindowHandle and Switch to another Window
	public static String getCurrentWindowHandleAndSwitchToNewWindow() {
		String currentHandle = driver.getWindowHandle();
		System.out.println("currentHandle"+currentHandle.toString());
		Set<String> handles = driver.getWindowHandles();
		System.out.println("All Handles: "+handles.toString());
		for(String handle:handles) {
			if(!(handle.equals(currentHandle))) {
				driver.switchTo().window(handle);
				System.out.println("Window Switched");
//				report.logTestInfo("Window Switched");
			}
		}		
		return currentHandle;
	}

	//Get the window count
	public static int getCountOfWindows() {
		return driver.getWindowHandles().size();
	}
	//switch to frame
	public static void switchToNewFrame(String nameOrID) {
		driver.switchTo().frame(nameOrID);
		System.out.println("switched to Frame");
		report.logTestInfo(" Switched to Frame");
	}


	public static void switchToNewFrame(int index) {
		driver.switchTo().frame(index);
	}

	public static void switchToNewFrame(WebElement webElement) {
		driver.switchTo().frame(webElement);
	}

	public static void switchToParentFrame() {
		driver.switchTo().parentFrame();
	}

	public static void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	//Alert
	public static void acceptAlert() {
		Alert alert = driver.switchTo().alert();
		System.out.println("Message in Alert: "+alert.getText());
		report.logTestInfo("Message in Alert: "+alert.getText());
		alert.accept();
	}

	//Page refresh..........................................
	public static void refreshPage() {
		driver.navigate().refresh();
	}
	//File download check
	public boolean isFileDownloaded(String fileDownloadpath, String fileName,String extension) {

		boolean flag = false;

		File directory = new File(fileDownloadpath);

		File[] content = directory.listFiles();

		for (int i = 0; i < content.length; i++) {
			String dirFileName=content[i].getName();
			if ((dirFileName.startsWith(fileName)&&dirFileName.endsWith((extension)))) {
				return flag=true;
			}
		}
		return flag;
	}
	//Close driver..........................................
	public static void closeDriver() {
		driver.close();
		System.out.println("Driver closed");

	}	
	public static void closeAllDriver() {
		driver.quit();
		System.out.println("All Drivers closed");
		report.logTestInfo("All Drivers closed");
	}

	//ScreenShot 
	public static void captureScreenshot(String fileName) throws IOException {
		TakesScreenshot captureScreen = (TakesScreenshot)driver;
		File source = captureScreen.getScreenshotAs(OutputType.FILE);
		Date currentdatetime = new Date();
		File destination = new File(SalesForceConstants.SALESFORCE_SCREENSHOTFOLDER_PATH+fileName+currentdatetime);
		FileUtils.copyFile(source, destination);
		report.attachScreeshot(destination.getPath());
	}

	//Get todays month
	public static String getTodaysMonth() {
		Date dt = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("MMM");
		String date = (sf.format(dt));
		return date;
	}
	
	public static void removeOptionFromSelectedTabsCustomizeMyTabs(String customAppVisibleText,String selectedTabsVisibleText) {
		
		WebElement userMenu = findElementBy("userMenuDropDown");
		clickOnElement(userMenu,"userMenu");
		
		WebElement mySettings=findElementBy("userMenuMySettings");
		clickOnElement(mySettings,"mySettings");

		WebElement mySettingsName = findElementBy("mySettingsName");		
		checkIfCorrectPageWithNameisDisplayed(mySettingsName,"MySettingsPage");
		
		WebElement displayAndLayout = findElementBy("displayAndLayout");
		clickOnElement(displayAndLayout,"displayAndLayout");
		
		WebElement customizeMyTabs = findElementBy("customizeMyTabs");
		clickOnElement(customizeMyTabs,"customizeMyTabs");
		
		WebElement customApp = findElementBy("customAppDropDown");
		String visibleText=customAppVisibleText;
		selectOptionByVisibleText(customApp,visibleText);
		
		WebElement selectedTabsList = findElementBy("selectedTabsList");
		visibleText=selectedTabsVisibleText;
		selectOptionByVisibleText(selectedTabsList,visibleText);
		
		WebElement removeArrow = findElementBy("removeArrow");
		waitSecondsForElementVisible(5, removeArrow);
		clickOnElement(removeArrow,"removeArrow");
		
		WebElement customizeMyTabsSaveButton = findElementBy("customizeMyTabsSaveButton");
		clickOnElement(customizeMyTabsSaveButton,"customizeMyTabsSaveButton");
	}
	public static String getUniquedateTimeString() {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("MMMddhhmmss");
		String addToMakeNameUnique = sf.format(date);
		return addToMakeNameUnique;
	}

}
