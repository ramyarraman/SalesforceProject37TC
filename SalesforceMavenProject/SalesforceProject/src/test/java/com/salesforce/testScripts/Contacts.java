package com.salesforce.testScripts;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import com.salesforce.base.BaseMethods;

public class Contacts extends BaseMethods {

	@BeforeMethod
	@Parameters("browser")
	public void setUpForContactsTests(String browser) {
//		launchAndOpenBrowser(browser);
//		goToSFLoginPage();	
		loginSalesforce(false);
		checkIfCorrectPageDisplayed("homePage");
		
		WebElement contactsPageLink = findElementBy("contactsPageLink");
		clickOnElement(contactsPageLink,"contactsPageLink");
		checkIfCorrectPageDisplayed("contactsPage");
		
		WebElement closePopUp = findElementBy("id","tryLexDialogX");
		clickOnElement(closePopUp,"closePopUp");
		
	}
	
	@Test
	@Parameters({"TC_25_newContactPageLastNameTextBoxValue","TC_25_newContactPageAccountNameTextBox"})
	public void TC25(String TC_25_newContactPageLastNameTextBoxValue,String TC_25_newContactPageAccountNameTextBox ) throws IOException {
		
		WebElement contactPageNewButton = findElementBy("contactPageNewButton");
		clickOnElement(contactPageNewButton,"contactPageNewButton");
		
		WebElement newContactPageLastNameTextBox = findElementBy("newContactPageLastNameTextBox");
		enterText(newContactPageLastNameTextBox,TC_25_newContactPageLastNameTextBoxValue);
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String actualText =js.executeScript("return document.getElementById('name_lastcon2').value;").toString(); 
		System.out.println(actualText);
		Assert.assertEquals(actualText,TC_25_newContactPageLastNameTextBoxValue);
		
		
		WebElement newContactPageAccountNameTextBox = findElementBy("newContactPageAccountNameTextBox");
		enterText(newContactPageAccountNameTextBox,TC_25_newContactPageAccountNameTextBox);
		
		actualText =js.executeScript("return document.getElementById('con4').value;").toString();
		Assert.assertEquals(actualText, TC_25_newContactPageAccountNameTextBox);
		
		captureScreenshot("Contacts/TC25_LastNameAndAccountNameEnteredBeforeSave");
		
		WebElement newContactPageSaveButton = findElementBy("newContactPageSaveButton");
		clickOnElement(newContactPageSaveButton,"newContactPageSaveButton");
		
		WebElement contactsParticularContactPageNameText = findElementBy("contactsParticularContactPageNameText");
		checkIfCorrectPageWithNameisDisplayed(contactsParticularContactPageNameText,"contactsParticularContactPage");
		captureScreenshot("Contacts/TC25_contactsParticularContactPage");
		
		
	}
	@Test
	@Parameters({"TC_26_contactsCreateNewViewViewNameTextBoxValue","TC_26_contactsCreateNewViewViewUniqueNameTextBoxValue"})
	public void TC26(String TC_26_contactsCreateNewViewViewNameTextBoxValue,String TC_26_contactsCreateNewViewViewUniqueNameTextBoxValue) throws IOException {
		WebElement contactsPageCreateNewViewLink = findElementBy("contactsPageCreateNewViewLink");
		clickOnElement(contactsPageCreateNewViewLink,"contactsPageCreateNewViewLink");
		
		checkIfCorrectPageDisplayed("contactsCreateNewViewPage");
		
		WebElement contactsCreateNewViewViewNameTextBox = findElementBy("contactsCreateNewViewViewNameTextBox");
		enterText(contactsCreateNewViewViewNameTextBox,TC_26_contactsCreateNewViewViewNameTextBoxValue);
		
		WebElement contactsCreateNewViewViewUniqueNameTextBox = findElementBy("contactsCreateNewViewViewUniqueNameTextBox");
		clickOnElement(contactsCreateNewViewViewUniqueNameTextBox,"contactsCreateNewViewViewUniqueNameTextBox");
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String ViewUniqueName =js.executeScript("return document.getElementById('devname').value;").toString();
		System.out.println(ViewUniqueName);
		
		String addToMakeNameUnique = getUniquedateTimeString();		
		String reportUniqueName=TC_26_contactsCreateNewViewViewUniqueNameTextBoxValue+addToMakeNameUnique;
		enterText(contactsCreateNewViewViewUniqueNameTextBox,reportUniqueName);
		
		WebElement contactsCreateNewViewSaveButton = findElementBy("contactsCreateNewViewSaveButton");
		clickOnElement(contactsCreateNewViewSaveButton,"contactsCreateNewViewSaveButton");
		
		captureScreenshot("Contacts/TC26_contactsAfterCreateViewSavePage");
		checkIfCorrectPageDisplayed("contactsAfterCreateViewSavePage");
		
		WebElement contactsPageViewDropDownButton = findElementBy("contactsPageViewDropDownButton");
		String selectedView = getTheSelectedDropDownOptionUsingSelect(contactsPageViewDropDownButton);
		Assert.assertEquals(selectedView, TC_26_contactsCreateNewViewViewNameTextBoxValue);
		
	}
	
	@Test
	@Parameters("TC_27contactsPageRecentlyCreateddDropDownValue")
	public void TC27(String TC_27contactsPageRecentlyCreateddDropDownValue) throws IOException {
		WebElement contactsPageRecentlyCreateddDropDown = findElementBy("contactsPageRecentlyCreateddDropDown");
		selectOptionByVisibleText(contactsPageRecentlyCreateddDropDown, TC_27contactsPageRecentlyCreateddDropDownValue);
		
		captureScreenshot("Contacts/TC27_RecentlyCreatedContactsPage");
		String actualSelectedOption =getTheSelectedDropDownOptionUsingSelect(contactsPageRecentlyCreateddDropDown);
		softassertObject.assertEquals(actualSelectedOption, TC_27contactsPageRecentlyCreateddDropDownValue);
		softassertObject.assertAll();
	}
	@Test
	@Parameters("TC_28_OptionToSelectInViewDropDown")
	public void TC28(String TC_28_OptionToSelectInViewDropDown) throws IOException {
		WebElement contactsPageViewDropDownButton = findElementBy("contactsPageViewDropDownButton");
		selectOptionByVisibleText(contactsPageViewDropDownButton, TC_28_OptionToSelectInViewDropDown);
		
		captureScreenshot("Contacts/TC28_SelectedViewContents");
		contactsPageViewDropDownButton = findElementBy("contactsPageViewDropDownButton");
		String actualSelectedOption =getTheSelectedDropDownOptionUsingSelect(contactsPageViewDropDownButton);
		softassertObject.assertEquals(actualSelectedOption, TC_28_OptionToSelectInViewDropDown);
		softassertObject.assertAll();
	}
	
	@Test
	public void TC29() throws IOException {
		WebElement contactsPageRecentContactsContactNameLink = findElementBy("contactsPageRecentContactsContactNameLink");
		clickOnElement(contactsPageRecentContactsContactNameLink,"contactsPageRecentContactsContactNameLink");
		
		captureScreenshot("Contacts/TC29_contactsParticularContactPage");
		WebElement contactsParticularContactPageNameText = findElementBy("contactsParticularContactPageNameText");
		checkIfCorrectPageWithNameisDisplayed(contactsParticularContactPageNameText,"contactsParticularContactPage");		
	}
	@Test
	@Parameters("TC_30_contactsCreateNewViewViewUniqueNameTextBoxValue")
	public void TC30(String TC_30_contactsCreateNewViewViewUniqueNameTextBoxValue) throws IOException {
		
		WebElement contactsPageCreateNewViewLink = findElementBy("contactsPageCreateNewViewLink");
		clickOnElement(contactsPageCreateNewViewLink,"contactsPageCreateNewViewLink");
		
		checkIfCorrectPageDisplayed("contactsCreateNewViewPage");
		
		WebElement contactsCreateNewViewViewUniqueNameTextBox = findElementBy("contactsCreateNewViewViewUniqueNameTextBox");
		enterText(contactsCreateNewViewViewUniqueNameTextBox,TC_30_contactsCreateNewViewViewUniqueNameTextBoxValue);
		
		WebElement contactsCreateNewViewSaveButton = findElementBy("contactsCreateNewViewSaveButton");
		clickOnElement(contactsCreateNewViewSaveButton,"contactsCreateNewViewSaveButton");
		
		captureScreenshot("Contacts/TC30_contactsAfterCreateViewSavePage");
		WebElement contactsCreateNewViewViewNameErrorMessage = findElementBy("contactsCreateNewViewViewNameErrorMessage");
		String actualErrorMessage=getText(contactsCreateNewViewViewNameErrorMessage);
		String ExpectedErrorMessage = "Error: You must enter a value";
		softassertObject.assertEquals(actualErrorMessage, ExpectedErrorMessage);
		softassertObject.assertAll();
	}
	
	@Test
	@Parameters({"TC_31_contactsCreateNewViewViewNameTextBoxValue","TC_31_contactsCreateNewViewViewUniqueNameTextBoxValue"})
	public void TC31(String TC_31_contactsCreateNewViewViewNameTextBoxValue,String TC_31_contactsCreateNewViewViewUniqueNameTextBoxValue) throws IOException {
		WebElement contactsPageCreateNewViewLink = findElementBy("contactsPageCreateNewViewLink");
		clickOnElement(contactsPageCreateNewViewLink,"contactsPageCreateNewViewLink");
		
		checkIfCorrectPageDisplayed("contactsCreateNewViewPage");
		
		WebElement contactsCreateNewViewViewNameTextBox = findElementBy("contactsCreateNewViewViewNameTextBox");
		enterText(contactsCreateNewViewViewNameTextBox,TC_31_contactsCreateNewViewViewNameTextBoxValue);
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String actualViewName =js.executeScript("return document.getElementById('fname').value;").toString();
		
		softassertObject.assertEquals(actualViewName, TC_31_contactsCreateNewViewViewNameTextBoxValue);
		
		WebElement contactsCreateNewViewViewUniqueNameTextBox = findElementBy("contactsCreateNewViewViewUniqueNameTextBox");
		clickOnElement(contactsCreateNewViewViewUniqueNameTextBox,"contactsCreateNewViewViewUniqueNameTextBox");
		enterText(contactsCreateNewViewViewUniqueNameTextBox,TC_31_contactsCreateNewViewViewUniqueNameTextBoxValue);
		
		String actualViewUniqueName =js.executeScript("return document.getElementById('devname').value;").toString();
		
		softassertObject.assertEquals(actualViewUniqueName, TC_31_contactsCreateNewViewViewUniqueNameTextBoxValue);
		
		captureScreenshot("Contacts/TC31_contactsCreateNewView_NamesEntered");
		WebElement contactsCreateNewViewCancelButton = findElementBy("contactsCreateNewViewCancelButton");
		clickOnElement(contactsCreateNewViewCancelButton,"contactsCreateNewViewCancelButton");
		
		checkIfCorrectPageDisplayed("contactsPage");
		WebElement contactsPageViewDropDownButton = findElementBy("contactsPageViewDropDownButton");
		clickOnElement(contactsPageViewDropDownButton, "contactsPageViewDropDownButton");
		captureScreenshot("Contacts/TC31_ViewNotAddedAfterCancel");
		
		List<String> viewOptions = getDropDownOptionsTextUsingSelect(contactsPageViewDropDownButton);
		softassertObject.assertFalse(viewOptions.contains(TC_31_contactsCreateNewViewViewNameTextBoxValue));
		softassertObject.assertAll();
	}		
	@Test
	@Parameters({"TC_32_newContactPageLastNameTextBoxValue","TC_32_newContactPageAccountNameTextBox"})
	public void TC32(String TC_32_newContactPageLastNameTextBoxValue,String TC_32_newContactPageAccountNameTextBox) throws IOException {
		WebElement contactPageNewButton = findElementBy("contactPageNewButton");
		clickOnElement(contactPageNewButton,"contactPageNewButton");
		
		WebElement newContactPageLastNameTextBox = findElementBy("newContactPageLastNameTextBox");
		enterText(newContactPageLastNameTextBox,TC_32_newContactPageLastNameTextBoxValue);
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String actualText =js.executeScript("return document.getElementById('name_lastcon2').value;").toString(); 
		System.out.println(actualText);
		AssertJUnit.assertEquals(actualText,TC_32_newContactPageLastNameTextBoxValue);
		
		
		WebElement newContactPageAccountNameTextBox = findElementBy("newContactPageAccountNameTextBox");
		enterText(newContactPageAccountNameTextBox,TC_32_newContactPageAccountNameTextBox);
		
		actualText =js.executeScript("return document.getElementById('con4').value;").toString();
		AssertJUnit.assertEquals(actualText, TC_32_newContactPageAccountNameTextBox);
		
		captureScreenshot("Contacts/TC32_LastNameAndAccountNameEnteredBeforeSave");
		
		WebElement newContactPageSaveAdNewButton = findElementBy("newContactPageSaveAdNewButton");
		clickOnElement(newContactPageSaveAdNewButton,"newContactPageSaveAdNewButton");
		
		captureScreenshot("Contacts/TC32_newContactsEditPage");
		checkIfCorrectPageDisplayed("newContactsEditPage");		
	}	
}
