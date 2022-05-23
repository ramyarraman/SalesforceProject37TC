package com.salesforce.testScripts;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;


import com.salesforce.base.BaseMethods;

public class CreateAccount extends BaseMethods {
	static String viewNameValue;
	@BeforeMethod
	public void setUpForCreateAccountTests() throws InterruptedException {
		loginSalesforce(false);
		checkIfCorrectPageDisplayed("homePage");
		
		Thread.sleep(2000);
		WebElement accounts = findElementBy("AccountsLink");
		clickOnElement(accounts,"AccountsLink");
		checkIfCorrectPageDisplayed("accountsPage");
		WebElement closePopUp = findElementBy("id","tryLexDialogX");
		clickOnElement(closePopUp,"closePopUp");
	}
	
	@Test(enabled=true)
	public void tC10CreateAccount() throws IOException {
		
		WebElement newAccount = findElementBy("newAccountButton");
		clickOnElement(newAccount,"newAccount");
		checkIfCorrectPageDisplayed("newAccountEditPage");
		WebElement accountNameTextBox = findElementBy("accountNameTextBox");
		clickOnElement(accountNameTextBox,"accountNameTextBox");
			
		String expectedAccountName = "New Account1";  
		enterText(accountNameTextBox,expectedAccountName);
		
		WebElement accountSaveButton = findElementBy("accountSaveButton");
		clickOnElement(accountSaveButton,"accountSaveButton");
		
		WebElement accountName = findElementBy("accountName");
		Assert.assertEquals(getText(accountName), expectedAccountName);
		captureScreenshot("CreateAccounts/tC10CreateAccount_AccountNameAndPageUpdate");
		checkIfCorrectPageWithNameisDisplayed(accountName,"newAccountPage");	
		softassertObject.assertAll();
		
	}
	
	@Test(enabled=true)
	public void tC11CreateNewView() throws IOException {
		
		WebElement createNewViewLink = findElementBy("createNewView");
		clickOnElement(createNewViewLink,"createNewViewLink");
		
		WebElement viewName = findElementBy("viewNameTextBox");
		String addToMakeNameUnique = getUniquedateTimeString();		
		viewNameValue="New_View"+addToMakeNameUnique;
		enterText(viewName,viewNameValue);
		
		WebElement viewUniqueName = findElementBy("viewUniqueNameTextBox");
		clickOnElement(viewUniqueName,"viewUniqueName");
		addToMakeNameUnique = getUniquedateTimeString();		
		String viewtUniqueNameValue="New_Unique_view"+addToMakeNameUnique;
		enterText(viewUniqueName,viewtUniqueNameValue);
		
		WebElement createNewViewSaveButton = findElementBy("createNewViewSaveButton");
		clickOnElement(createNewViewSaveButton,"createNewViewSaveButton");
		
		WebElement viewDropDown = findElementBy("viewDropDown");
		List <String> viewDropDownOptionsText =getDropDownOptionsTextUsingSelect(viewDropDown);
		captureScreenshot("CreateAccounts/tC11CreateNewView_viewSelectioninDropDown");
		Assert.assertTrue(viewDropDownOptionsText.contains(viewNameValue));	
		
	}
	
	@Test(enabled=true,dependsOnMethods= {"tC11CreateNewView"})
	public void tC12CreateNewView() throws IOException, InterruptedException {
		
		System.out.println("In TC12");
		WebElement accountsPageViewDropDown = findElementBy("accountsPageViewDropDown");
		selectOptionByVisibleText(accountsPageViewDropDown,viewNameValue);
		
		driver.navigate().refresh();
		Thread.sleep(5000);
		captureScreenshot("CreateAccounts/tC12CreateNewView_afterselecting view");
		WebElement accountsPageViewEditLink = findElementBy("xpath","//*[@id='filter_element']/div/span/span[2]/a[1]");
		clickOnElement(accountsPageViewEditLink,"accountsPageViewEditLink");
		
		checkIfCorrectPageDisplayed("accountsEditViewPage");
		
		WebElement editViewViewNameTextBox=findElementBy("editViewViewNameTextBox");
		String nameEntered= "New View1";
		enterText(editViewViewNameTextBox,nameEntered);
		
		WebElement editViewFilterField = findElementBy("editViewFilterField");
		selectOptionByVisibleText(editViewFilterField, "Account Name");
		
		WebElement editViewOperator = findElementBy("editViewOperator");
		selectOptionByVisibleText(editViewOperator, "contains");
		
		WebElement editViewAvailableFields = findElementBy("editViewAvailableFields");
		selectOptionByVisibleText(editViewAvailableFields,"Last Activity");
		
		WebElement editViewAddArrow = findElementBy("editViewAddArrow");
		clickOnElement(editViewAddArrow,"editViewAddArrow");
		
		WebElement editViewSaveButton = findElementBy("editViewSaveButton");
		clickOnElement(editViewSaveButton,"editViewSaveButton");
		
		WebElement viewDropDown = findElementBy("accountsPageViewDropDown");
		Select select = new Select(viewDropDown);		
		String actualNameInView = getText(select.getFirstSelectedOption());
		System.out.println("actualNameInView "+actualNameInView);
		
		Assert.assertEquals(actualNameInView,nameEntered);
		
		List<WebElement> headerData = findElementsBy("accountsPageviewTableHeader");
		List<String> headerText = new ArrayList<String>();
 		for(WebElement data:headerData) {
// 			System.out.println(getText(data));
 			headerText.add(getText(data));
 		}
 		
 		captureScreenshot("CreateAccounts/tC12CreateNewView_LastActivityUpdate");
		Assert.assertTrue(headerText.contains("Last Activity"));
		softassertObject.assertAll();	
		
		//code to remove last activity, preparation for using test case next time
		 accountsPageViewEditLink = findElementBy("accountsPageViewEditLink");
		clickOnElement(accountsPageViewEditLink,"accountsPageViewEditLink");
		WebElement editViewSelectedFields = findElementBy("editViewSelectedFields");
		selectOptionByVisibleText(editViewSelectedFields,"Last Activity");		
		WebElement editViewRemoveArrow = findElementBy("editViewRemoveArrow");
		clickOnElement(editViewRemoveArrow,"editViewRemoveArrow");		
		editViewSaveButton = findElementBy("editViewSaveButton");
		clickOnElement(editViewSaveButton,"editViewSaveButton");
		
	}
	
	@Test(enabled=true,dependsOnMethods="tC10CreateAccount")
	public void TC13MergeAccounts() throws IOException, InterruptedException {
		
		WebElement accountsPageToolsMergeAccounts = findElementBy("accountsPageToolsMergeAccounts");
		clickOnElement(accountsPageToolsMergeAccounts,"accountsPageToolsMergeAccounts");
		
		WebElement mergeMyAccountsTextBox = findElementBy("mergeMyAccountsTextBox");
		enterText(mergeMyAccountsTextBox,"New Account1");
		
		WebElement mergeMyAccountsFindAccountsButton = findElementBy("mergeMyAccountsFindAccountsButton");
		clickOnElement(mergeMyAccountsFindAccountsButton,"mergeMyAccountsFindAccountsButton");
		
		WebElement mergeMyAccountsFirstCheckBox = findElementBy("mergeMyAccountsFirstCheckBox");
		if(!mergeMyAccountsFirstCheckBox.isSelected())
		clickOnElement(mergeMyAccountsFirstCheckBox,"mergeMyAccountsFirstCheckBox");
		
		WebElement mergeMyAccountsSecondCheckBox = findElementBy("mergeMyAccountsSecondCheckBox");
		if(!mergeMyAccountsSecondCheckBox.isSelected())
		clickOnElement(mergeMyAccountsSecondCheckBox,"mergeMyAccountsSecondCheckBox");
		
		WebElement mergeMyAccountsNextButton = findElementBy("mergeMyAccountsNextButton");
		clickOnElement(mergeMyAccountsNextButton,"mergeMyAccountsNextButton");
		
		checkIfCorrectPageDisplayed("mergeAccountsPage");
		String expectedTitleInPage="Step 2. Select the values to retain";
		
		WebElement mergeMyAccountsStep2Tiltle = findElementBy("mergeMyAccountsStep2Tiltle");
		String actualTitleInPage=getText(mergeMyAccountsStep2Tiltle);
		
		Assert.assertEquals(actualTitleInPage, expectedTitleInPage);
		captureScreenshot("CreateAccounts/TC13MergeAccounts_Step2Page");
		
		WebElement mergeMyAccountsStep2MergeButton = findElementBy("mergeMyAccountsStep2MergeButton");
		clickOnElement(mergeMyAccountsStep2MergeButton,"mergeMyAccountsStep2MergeButton");
	
		acceptAlert();
		
		Thread.sleep(5000);
		checkIfCorrectPageDisplayed("accountsPage");
		
		WebElement accountsPageViewDropDown=findElementBy("accountsPageViewDropDown");
		selectOptionByVisibleText(accountsPageViewDropDown, "Recently Viewed Accounts");

		Thread.sleep(5000);
//		WebElement accountsPageViewDropDownGoButton = findElementBy("accountsPageViewDropDownGoButton");
//		clickOnElement(accountsPageViewDropDownGoButton,"accountsPageViewDropDownGoButton");
		captureScreenshot("CreateAccounts/TC13MergeAccounts_AccountsPageAfterMerge");
		
	
		List<WebElement> accountsList =findElementsBy("xpath","//*[@id='ext-gen12']/div/table/tbody//tr/td[4]");
		List<String> AccountsItems =new ArrayList<String>();
		for(WebElement element:accountsList) {
			AccountsItems.add(getText(element));
		}		
//		System.out.println("AccoutnItems: "+AccountsItems);
		
		Assert.assertTrue(AccountsItems.contains("New Account1"));
		
	}
	
	@Test(enabled = true)
	@Parameters({"TC14_saveReportReportName","TC14_saveReportReportUniqueName"})
	public void TC14(String TC14_saveReportReportName,String TC14_saveReportReportUniqueName)throws IOException, InterruptedException {
		
		WebElement accountsPageAccountsWithLastActivityMoreThan30 = findElementBy("accountsPageAccountsWithLastActivityMoreThan30");
		clickOnElement(accountsPageAccountsWithLastActivityMoreThan30,"accountsPageAccountsWithLastActivityMoreThan30");
		
		waitForPageToLoad(5);
		checkIfCorrectPageDisplayed("unsavedReportPage");
		
		WebElement unsavedReportsPageDateField = findElementBy("unsavedReportsPageDateField");
		clickOnElement(unsavedReportsPageDateField,"unsavedReportsPageDateField");	
		
		WebElement unsavedReportsPageDateFieldCreatedDateOption = findElementBy("unsavedReportsPageDateFieldCreatedDateOption");
		clickOnElement(unsavedReportsPageDateFieldCreatedDateOption,"unsavedReportsPageDateFieldCreatedDateOption");	
		
		WebElement unsavedunsavedReportsPageFromFieldDatePicker = findElementBy("unsavedunsavedReportsPageFromFieldDatePicker");
		clickOnElement(unsavedunsavedReportsPageFromFieldDatePicker,"unsavedunsavedReportsPageFromFieldDatePicker");	
		
		WebElement unsavedunsavedReportsPageFromFieldTodaySelectedDateOption = findElementBy("unsavedunsavedReportsPageFromFieldTodaySelectedDateOption");
		clickOnElement(unsavedunsavedReportsPageFromFieldTodaySelectedDateOption,"unsavedunsavedReportsPageFromFieldTodaySelectedDateOption");
		
		WebElement unsavedunsavedReportsPageToFieldDatePicker = findElementBy("unsavedunsavedReportsPageToFieldDatePicker");
		clickOnElement(unsavedunsavedReportsPageToFieldDatePicker,"unsavedunsavedReportsPageToFieldDatePicker");
		
		WebElement unsavedunsavedReportsPageToFieldMonthOption = findElementBy("unsavedunsavedReportsPageToFieldMonthOption");
		clickOnElement(unsavedunsavedReportsPageToFieldMonthOption,"unsavedunsavedReportsPageToFieldMonthOption");
		
		String monthSelection =getTodaysMonth();
		List<WebElement> monthOptions = findElementsBy("unsavedunsavedReportsPageToFieldMonthOptionTextElementList");
		for(WebElement month:monthOptions) {
			if(getText(month).equals(monthSelection)) {
				doubleClickUsingActions(month,"Todays month");
			}
		}
		
		
		WebElement unsavedunsavedReportsPageToFieldTodaySelectedDateOption = findElementBy("unsavedunsavedReportsPageToFieldTodaySelectedDateOption");
		waitSecondsForElementVisible(5, unsavedunsavedReportsPageToFieldTodaySelectedDateOption);
		mouseOverAndClickUsingActions(unsavedunsavedReportsPageToFieldTodaySelectedDateOption,"unsavedunsavedReportsPageToFieldTodaySelectedDateOption");
		
		captureScreenshot("CreateAccounts/TC14_TodaysReportList");
		
		WebElement unsavedunsavedReportsPageSaveButton = findElementBy("unsavedunsavedReportsPageSaveButton");
		clickOnElement(unsavedunsavedReportsPageSaveButton,"unsavedunsavedReportsPageSaveButton");
		
//		switchToNewWindow();
		
		WebElement saveReportReportName = findElementBy("saveReportReportName");
		enterText(saveReportReportName,TC14_saveReportReportName);
		
		
		WebElement saveReportReportUniqueName = findElementBy("saveReportReportUniqueName");
		clickOnElement(saveReportReportUniqueName, "saveReportReportUniqueName");
		String addToMakeNameUnique = getUniquedateTimeString();		
		String reportUniqueName=TC14_saveReportReportUniqueName+addToMakeNameUnique;
		enterText(saveReportReportUniqueName,reportUniqueName);
		
		Thread.sleep(3000);
		WebElement saveReportSaveAndRunButton = findElementBy("saveReportSaveAndRunButton");
		waitSecondsForElementToBeClickable(30,saveReportSaveAndRunButton);
		mouseOverAndClickUsingActions(saveReportSaveAndRunButton, "saveReportSaveAndRunButton");
		System.out.println("Clicked on element saveReportSaveAndRunButton");
				
		Thread.sleep(3000);
		captureScreenshot("CreateAccounts/TC14_ReportPage");
		WebElement reportsPageReportName = findElementBy("reportsPageReportName");
		Assert.assertEquals(getText(reportsPageReportName),TC14_saveReportReportName);
	
		checkIfCorrectPageWithNameisDisplayed(reportsPageReportName, "reportsPage");
		
	}
	
	
}

