package com.salesforce.testScripts;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.salesforce.base.BaseMethods;
import com.salesforce.utilities.SalesForceConstants;

public class CreateOpty extends BaseMethods{
	
	@BeforeMethod
	public void setupForCreateOptyTests() {
//		launchAndOpenBrowser("firefox");
//		goToSFLoginPage();	
		loginSalesforce(false);	
		checkIfCorrectPageDisplayed("homePage");		
		WebElement opportunitiesPageLink = findElementBy("opportunitiesPageLink");
		clickOnElement(opportunitiesPageLink,"opportunitiesPageLink");
		checkIfCorrectPageDisplayed("opportunitiesPage");
		
		WebElement closePopUp = findElementBy("id","tryLexDialogX");
		clickOnElement(closePopUp,"closePopUp");
	}
	
	@Test(enabled=false)
	public void TC15() {		
		
		WebElement opportunitiesPageDropDown = findElementBy("opportunitiesPageDropDown");
		List<String> opportunitiesOptions = getDropDownOptionsTextUsingSelect(opportunitiesPageDropDown);
		System.out.println("Actual Options: "+opportunitiesOptions);
		List<String> expectedOptions= new ArrayList<String>(Arrays.asList(SalesForceConstants.OPPORTUNITIES_DROPDOWN_OPTIONS));
		System.out.println("Expected Options: "+expectedOptions);
		
		AssertJUnit.assertEquals(opportunitiesOptions, expectedOptions);
		softassertObject.assertAll();	
		
	}
	
	@Test(enabled=false)
	public void TC16() throws IOException {
		WebElement opportunitiesPageNewButton = findElementBy("opportunitiesPageNewButton");
		clickOnElement(opportunitiesPageNewButton,"opportunitiesPageNewButton");
		checkIfCorrectPageDisplayed("newOpportunityEditPage");
		
		WebElement opportunitiesPageNameTextBox = findElementBy("opportunitiesPageNameTextBox");
		enterText(opportunitiesPageNameTextBox,"OppName");
		
		WebElement opportunitiesPageAccountNameTextBox = findElementBy("opportunitiesPageAccountNameTextBox");
		enterText(opportunitiesPageAccountNameTextBox,"United Oil & Gas Corp.");
		
		WebElement opportunitiesPageCloseDate = findElementBy("opportunitiesPageCloseDate");
		clickOnElement(opportunitiesPageCloseDate,"opportunitiesPageCloseDate");
		
		WebElement opportunitiesPageSelectADate = findElementBy("opportunitiesPageSelectADate");
		clickOnElement(opportunitiesPageSelectADate,"opportunitiesPageSelectADate");
		
		WebElement opportunitiesPageStage = findElementBy("opportunitiesPageStage");
		selectOptionByVisibleText(opportunitiesPageStage, "Prospecting");
		
		WebElement opportunitiesPageProbability = findElementBy("opportunitiesPageProbability");
		enterText(opportunitiesPageProbability,"12");
		
		WebElement opportunitiesPageLeadSource = findElementBy("opportunitiesPageLeadSource");
		selectOptionByVisibleText(opportunitiesPageLeadSource, "Phone Inquiry");
		
		WebElement opportunitiesPageSaveButton = findElementBy("opportunitiesPageSaveButton");
		clickOnElement(opportunitiesPageSaveButton,"opportunitiesPageSaveButton");
		
		WebElement opportunitiesPageName = findElementBy("opportunitiesPageName");
		captureScreenshot("CreateOpty/TC16_NewOppurtunitiesPage");
		checkIfCorrectPageWithNameisDisplayed(opportunitiesPageName, "newOpportunitiesPage");
		
	}
	@Test(enabled=false)
	public void TC17() throws IOException {
		WebElement opportunitiesPagePipelineLink = findElementBy("opportunitiesPagePipelineLink");
		clickOnElement(opportunitiesPagePipelineLink,"opportunitiesPagePipelineLink");
		captureScreenshot("CreateOpty/TC17_oppurtunitiesPipelineReportsPage");
		checkIfCorrectPageDisplayed("oppurtunitiesPipelineReportsPage");
	}
	
	@Test(enabled=false)
	public void TC18() throws IOException {
		WebElement opportunitiesPageStuckOpportunitiesLink = findElementBy("opportunitiesPageStuckOpportunitiesLink");
		clickOnElement(opportunitiesPageStuckOpportunitiesLink,"opportunitiesPageStuckOpportunitiesLink");
		captureScreenshot("CreateOpty/TC18_oppurtunitiesStuckOpportunitiesPage");
		checkIfCorrectPageDisplayed("oppurtunitiesStuckOpportunitiesPage");
	}
	
	@Test(enabled=true, dataProvider="TC19data")
	public void TC19(String intervalValue,String includeValue) throws IOException {
//		String[] intervalValues= {"Current FQ","Next FQ"};
//		String[] includeValues= {"all","open","closed"};		
		
		WebElement opportunitiesPageQuaterlySummaryIncludeDropDown = findElementBy("opportunitiesPageQuaterlySummaryIncludeDropDown");
		System.out.println(getDropDownOptionsTextUsingSelect(opportunitiesPageQuaterlySummaryIncludeDropDown));
		
//		for(String intervalValue:intervalValues) {
//			for(String includeValue:includeValues) {
				System.out.println(intervalValue+" , "+includeValue);
				WebElement opportunitiesPageQuaterlySummaryIntervalDropDown = findElementBy("opportunitiesPageQuaterlySummaryIntervalDropDown");
				selectOptionByVisibleText(opportunitiesPageQuaterlySummaryIntervalDropDown, intervalValue);
				
				opportunitiesPageQuaterlySummaryIncludeDropDown = findElementBy("opportunitiesPageQuaterlySummaryIncludeDropDown");
				selectOptionByValue(opportunitiesPageQuaterlySummaryIncludeDropDown, includeValue);
				
				WebElement opportunitiesPageQuaterlySummaryRunReportButton = findElementBy("opportunitiesPageQuaterlySummaryRunReportButton");
				clickOnElement(opportunitiesPageQuaterlySummaryRunReportButton,"opportunitiesPageQuaterlySummaryRunReportButton");
				
				captureScreenshot("CreateOpty/TC19_opportunityReportPage");
				checkIfCorrectPageDisplayed("opportunityReportPage");
				
				WebElement opportunitiesPageLink = findElementBy("opportunitiesPageLink");
				clickOnElement(opportunitiesPageLink,"opportunitiesPageLink");
				
				
//			}
//		}		
	}	
}
