package com.salesforce.testScripts;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import java.awt.AWTException;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import com.salesforce.base.BaseMethods;


public class UserMenuDropDownAutomationScripts extends BaseMethods{
	//User menu drop down  begin...................................................................................................
	@BeforeMethod
	public void setUpForUserMenuDropDown() {
		checkIfCorrectPageDisplayed("loginPage");
		loginSalesforce(false);	
		checkIfCorrectPageDisplayed("homePage");
		checkUserMenuDropDownOptions();	
	}
	
	@Test(enabled=true)
	public static void TC05() {

//		launchAndOpenBrowser("Chrome");
//		//Test case Condition 1..........................................................
//		goToSFLoginPage();		
//		checkIfCorrectPageDisplayed("loginPage");
//		loginSalesforce(false);	
//		checkIfCorrectPageDisplayed("homePage");
//		checkUserMenuDropDownOptions();	
	}

	@Test(enabled=true)
	public void TC06() throws AWTException, InterruptedException, IOException {

		WebElement myProfile = findElementBy("userMenuMyProfile");
		clickOnElement(myProfile,"myProfile");
		waitForPageToLoad(10);
		WebElement profileName = findElementBy("profilePageFistLast");
	
		checkIfCorrectPageWithNameisDisplayed(profileName,"profilePage");
		
				String ExpectedTitle = "User: "+getText(profileName)+"~ Salesforce - Developer Edition";
				String actualTitle = getPageTitle();
				softassertObject.assertEquals(actualTitle, ExpectedTitle);
		
				WebElement editContactLink = findElementBy("contactEditLink");
				clickOnElement(editContactLink,"editContactLink");
				
				waitForPageToLoad(30);
				
				switchToNewFrame("contactInfoContentId");		
				
				waitForPageToLoad(30);
				
				checkIfElementDisplayedByLocator("xpath","//*[@id='contactTab']/a");
				checkIfElementDisplayedByLocator("aboutTab");
				
				WebElement about =  findElementBy("aboutTab");
				clickOnElement(about,"about");
				
				JavascriptExecutor js = (JavascriptExecutor)driver;
				String fName = js.executeScript("return document.getElementById('firstName').value").toString();
		
				
				System.out.println("FirstName: "+fName);
				WebElement lastName = findElementBy("lastName");
				String lName="Last";
				enterText(lastName,lName);
				
				WebElement saveAllButton = findElementBy("saveAllButton");
				clickOnElement(saveAllButton,"saveAllButton");
		
				profileName = findElementBy("profilePageFistLast");	
				String actualText = getText(profileName);
				
				String expectEdText=fName+" "+lName;
				System.out.println(expectEdText);
				softassertObject.assertEquals(actualText.trim(),expectEdText);
				
				switchToDefaultContent();
				
				//Post Link condition..................................................
				WebElement postLink = findElementBy("profilePagePostLink");
				clickOnElement(postLink,"postLink");
				
				WebElement iframe = findElementBy("xpath","//iframe[1][@class='cke_wysiwyg_frame cke_reset']");
				switchToNewFrame(iframe);
				
				WebElement textArea = findElementBy("xpath","/html/body");
				clickOnElement(textArea,"textArea");
				String enterContent = "Hello There!";
				enterText(textArea,enterContent);
				 
				switchToDefaultContent();
				
				WebElement shareButton = findElementBy("id","publishersharebutton");
				clickOnElement(shareButton,"shareButton");
				
				refreshPage();
			Thread.sleep(3000);
				
				WebElement content = findElementBy("xpath","//div[@id='feedwrapper']/div/div[5]/div/div/div/div[2]/div/span/p");
				String actualContent = getText(content);
				captureScreenshot("UserMenuDropDown/TC06_PostLInk_ContentPosted");
				softassertObject.assertEquals(actualContent,enterContent);	
				
		
				//File Link...........................................................
				driver.navigate().refresh();
				WebElement fileLink = findElementBy("xpath","//*[@id=\"publisherAttachContentPost\"]");
				Actions a = new Actions(driver);
				a.moveToElement(fileLink).build().perform();
				clickOnElement(fileLink,"fileLink");

				WebElement uploadFileFromComputerLink = findElementBy("xpath","//*[@id=\"chatterUploadFileAction\"]");
				clickOnElement(uploadFileFromComputerLink,"uploadFileFromComputerLink");
		
				WebElement chooseFile = findElementBy("id","chatterFile");
				chooseFile.sendKeys("/Users/ramyabharath/Downloads/download.jpeg");
		
				System.out.println("crossed the file sendkeys");
		
				/*
				//For windows OS
				StringSelection ss = new StringSelection("/Users/ramyabharath/Downloads/P7040299.jpg");
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,null);
		
				Robot robo = new Robot();
				robo.keyPress(KeyEvent.VK_CONTROL);
				robo.keyPress(KeyEvent.VK_V);
				robo.delay(2000);
				robo.keyRelease(KeyEvent.VK_CONTROL);
				robo.keyRelease(KeyEvent.VK_V);
				robo.delay(2000);
				robo.keyPress(KeyEvent.VK_ENTER);
				robo.keyRelease(KeyEvent.VK_ENTER);
				 */	
		
		
				WebElement fileLinkShareButton = findElementBy("fileLinkShareButton");
				clickOnElement(fileLinkShareButton,"fileLinkShareButton");
				captureScreenshot("UserMenuDropDown/TC06_fileupload");
		
				Thread.sleep(3000);
		
				driver.navigate().refresh();
				waitForPageToLoad(5);
		
				WebElement postfileMessage = findElementBy("postfileMessage");
				String actualMessage=getText(postfileMessage);
		
				WebElement userMenu = findElementBy("userMenuDropDown");
				String userName = getText(userMenu);
				String expectedMessage= userName+" posted a file.";
				
				captureScreenshot("UserMenuDropDown/TC06_filepost");
				softassertObject.assertEquals(actualMessage,expectedMessage);
				
		
				//Photo Upload .......................................................
				WebElement profilePageMyProfilePhoto=findElementBy("profilePageMyProfilePhoto");
				mouseOverUsingActions(profilePageMyProfilePhoto);
		
				WebElement profilePageAddPhotoLink = findElementBy("profilePageAddPhotoLink");
				mouseOverAndClickUsingActions(profilePageAddPhotoLink,"profilePageAddPhotoLink");
		
				switchToNewFrame("uploadPhotoContentId");
		
				WebElement profilePageAddPhotoChooseFile = findElementBy("profilePageAddPhotoChooseFile");
				enterText(profilePageAddPhotoChooseFile,"/Users/ramyabharath/Downloads/download.jpeg");
		
				WebElement profilePageUploadPhotoSaveButton = findElementBy("profilePageUploadPhotoSaveButton");
				clickOnElement(profilePageUploadPhotoSaveButton,"profilePageUploadPhotoSaveButton");
		
				waitForPageToLoad(20);
				
			
				WebElement cropHandleTopRight = findElementBy("xpath","//*[@id=\"j_id0:outer\"]/div[1]/div/div/div/div[5]/div[2]");
				captureScreenshot("UserMenuDropDown/TC06_PhotoUpload_BeforeCrop");
				Actions action = new Actions(driver);
				action.dragAndDropBy(cropHandleTopRight, -20, -20)
				.build().perform();
		
				captureScreenshot("UserMenuDropDown/TC06_PhotoUpload_AfterCrop");
		
				WebElement profilePageCropPhotoSaveButton = findElementBy("profilePageCropPhotoSaveButton");
				clickOnElement(profilePageCropPhotoSaveButton,"profilePageCropPhotoSaveButton");
				waitForPageToLoad(10);
				captureScreenshot("UserMenuDropDown/TC06_PhotoUpload_AfterCropUpload");
				softassertObject.assertAll();
	}

	@Test(enabled=true)
	public void TC07() throws IOException, InterruptedException {

		WebElement mySettings=findElementBy("userMenuMySettings");
		clickOnElement(mySettings,"mySettings");

		WebElement mySettingsName = findElementBy("mySettingsName");		
		checkIfCorrectPageWithNameisDisplayed(mySettingsName,"MySettingsPage");

		//File download......................................

				WebElement personalLink = findElementBy("personalLink");
				clickOnElement(personalLink,"personalLink");

				WebElement loginHistoryLink = findElementBy("loginHistoryLink");
				clickOnElement(loginHistoryLink,"loginHistoryLink");
				
				WebElement downloadLoginLink = findElementBy("downloadLoginLink");
				clickOnElement(downloadLoginLink,"downloadLoginLink");
				
				webDriverWait(10);
				
				captureScreenshot("UserMenuDropDown/TC07_AfterFileDownLoad");
				String fileDownloadPath = "/Users/ramyabharath/Downloads";
				softassertObject.assertTrue(isFileDownloaded(fileDownloadPath,"LoginHistory","csv"));

				//add reports to tabbar
				WebElement displayAndLayout = findElementBy("displayAndLayout");
				clickOnElement(displayAndLayout,"displayAndLayout");
				
				WebElement customizeMyTabs = findElementBy("customizeMyTabs");
				clickOnElement(customizeMyTabs,"customizeMyTabs");
				
				WebElement customApp = findElementBy("customAppDropDown");
				String visibleText="Salesforce Chatter";
				selectOptionByVisibleText(customApp,visibleText);
				
				WebElement availableTabsList = findElementBy("availableTabsList");
				visibleText="Reports";
				selectOptionByVisibleText(availableTabsList,visibleText);
				
				WebElement addArrow = findElementBy("addArrow");
				waitSecondsForElementVisible(5, addArrow);
				clickOnElement(addArrow,"addArrow");
				
				WebElement selectedTabsList = findElementBy("selectedTabsList");
				List<String >listOfitems=getDropDownOptionsTextUsingSelect(selectedTabsList);
				captureScreenshot("UserMenuDropDown/TC07_RefundsSelectedTabList");		
				softassertObject.assertTrue(listOfitems.contains(visibleText));
				
				WebElement customizeMyTabsSaveButton = findElementBy("customizeMyTabsSaveButton");
				clickOnElement(customizeMyTabsSaveButton,"customizeMyTabsSaveButton");
				
				webDriverWait(5);
				
								
				WebElement pagesdropDownButton = findElementBy("pagesDropDownButton");
				clickOnElement(pagesdropDownButton,"pagesDropDownButton");
				
				//salesforce chatter page
				boolean flag = clickOnNonSelectTagDropDownUsingVisibleText("pagesDropDownOptions","Salesforce Chatter");
				waitForPageToLoad(5);
				
				if(flag) {
				WebElement closePopUp = findElementBy("id","tryLexDialogX");
				clickOnElement(closePopUp,"closePopUp");
				}
				
				captureScreenshot("UserMenuDropDown/TC07_ReportsInTabBar_SalesForceChatterPage");
				softassertObject.assertTrue(getTabBarOptions().contains(visibleText),"chatter tab bar options");
				
				//sales Page

				pagesdropDownButton = findElementBy("pagesDropDownButton");
				clickOnElement(pagesdropDownButton,"pagesDropDownButton");
				
				flag =clickOnNonSelectTagDropDownUsingVisibleText("pagesDropDownOptions","Sales");
				waitForPageToLoad(5);
				
//				if(flag) {
//					WebElement closePopUp = findElementBy("id","tryLexDialogX");
//					clickOnElement(closePopUp,"closePopUp");
//					}
				
				captureScreenshot("UserMenuDropDown/TC07_ReportsInTabBar_SalesPage");
				softassertObject.assertTrue(getTabBarOptions().contains(visibleText),"Tab bar options");
				
				//Marketing page
				pagesdropDownButton = findElementBy("pagesDropDownButton");
				clickOnElement(pagesdropDownButton,"pagesDropDownButton");
				
				flag = clickOnNonSelectTagDropDownUsingVisibleText("pagesDropDownOptions","Marketing");
				waitForPageToLoad(5);
				
//				if(flag) {
//					WebElement closePopUp = findElementBy("id","tryLexDialogX");
//					clickOnElement(closePopUp,"closePopUp");
//					}
				
				captureScreenshot("UserMenuDropDown/TC07_ReportsInTabBar_MarketingPage");
				softassertObject.assertTrue(getTabBarOptions().contains(visibleText),"Tab bar options");
				
				removeOptionFromSelectedTabsCustomizeMyTabs("Salesforce Chatter","Reports");
				
				pagesdropDownButton = findElementBy("pagesDropDownButton");
				clickOnElement(pagesdropDownButton,"pagesDropDownButton");
				
				flag = clickOnNonSelectTagDropDownUsingVisibleText("pagesDropDownOptions","Content");
	 

				//Email
				WebElement userMenuDropDown = findElementBy("userMenuDropDown");
				clickOnElement(userMenuDropDown,"userMenuDropDown");
				
				mySettings=findElementBy("userMenuMySettings");
				clickOnElement(mySettings,"mySettings");
				
				WebElement emailLink = findElementBy("emailLink");
				clickOnElement(emailLink,"emailLink");
				
				WebElement myEmailSettingsLink = findElementBy("myEmailSettingsLink");
				clickOnElement(myEmailSettingsLink,"myEmailSettingsLink");
				
				WebElement emailNameTextBox = findElementBy("emailNameTextBox");
				enterText(emailNameTextBox,"First Last");
				
				WebElement emailAddress = findElementBy("emailAddress");
				enterText(emailAddress,"ramyarraman@gmail.com");
				
				WebElement automaticBccRadioButtonYes = findElementBy("automaticBccRadioButtonYes");
				clickOnElement(automaticBccRadioButtonYes,"automaticBccRadioButtonYes");
				
				WebElement myEmailSettingsSaveButton = findElementBy("myEmailSettingsSaveButton");
				clickOnElement(myEmailSettingsSaveButton,"myEmailSettingsSaveButton");
				
				WebElement pageSettingsSavedMessage = findElementBy("pageSettingsSavedMessage");
				String expectedMessage = "Your settings have been successfully saved.";
				
				captureScreenshot("UserMenuDropDown/TC07_MyEmailSettingsSavedPage");	
				Assert.assertEquals(pageSettingsSavedMessage.getText(),expectedMessage);
				boolean flag1=checkIfElementSelected("automaticBccRadioButtonYes");
				Assert.assertTrue(flag1);
				
				softassertObject.assertAll();

		//Calender And Reminder
		WebElement calendersAndRemindersLink = findElementBy("calendersAndRemindersLink");
		clickOnElement(calendersAndRemindersLink,"calendersAndRemindersLink");

		WebElement activityRemindersLink = findElementBy("activityRemindersLink");
		clickOnElement(activityRemindersLink,"activityRemindersLink");

		WebElement openATestReminder = findElementBy("openATestReminder");
		clickOnElement(openATestReminder,"openATestReminder");
		Thread.sleep(5000);
		

		Thread.sleep(3000);
		switchToNewWindow();
		System.out.println(getPageTitle());
		captureScreenshot("UserMenuDropDown/TC07_OpenATestReminder_SampleEventPage");
		webDriverWait(5);	
		WebElement sampleEventWindow = findElementBy("sampleEventWindow");
		String expectedText="Sample Event.";
		Assert.assertEquals(getText(sampleEventWindow).trim(),expectedText);	

		softassertObject.assertAll();


	}
	
	

	@Test(enabled=true)
	public void TC08() throws IOException {

		WebElement developerConsole = findElementBy("userMenuDeveloperConsole");
		clickOnElement(developerConsole,"userMenuDeveloperConsole");

		switchToNewWindow();
		captureScreenshot("UserMenuDropDown/TC08_developerConsole");
		checkIfCorrectPageDisplayed("developerConsoleWindow");
		closeDriver();

	}

	@Test(enabled=true)
	public void TC09() throws InterruptedException {
//		launchAndOpenBrowser("Chrome");
//		goToSFLoginPage();	
//		loginSalesforce(false);	
		logoutSalesForce();
	}

}
