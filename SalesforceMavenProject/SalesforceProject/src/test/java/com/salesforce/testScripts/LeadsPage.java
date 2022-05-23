package com.salesforce.testScripts;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import com.salesforce.base.BaseMethods;
import com.salesforce.utilities.SalesForceConstants;


public class LeadsPage extends BaseMethods {
	
	@BeforeMethod
	public void setUpLeadsPage() throws InterruptedException {

		loginSalesforce(false);	
//		checkIfCorrectPageDisplayed("homePage");
		
		WebElement leadsPageLink = findElementBy("leadsPageLink");
		clickOnElement(leadsPageLink,"leadsPageLink");
		checkIfCorrectPageDisplayed("leadsPage");
		
		WebElement closePopUp = findElementBy("id","tryLexDialogX");
		clickOnElement(closePopUp,"closePopUp");
	}
	@Test
	public void TC20LeadsTab() throws IOException {
		captureScreenshot("Leads/TC20LeadsTab_LeadsPage");
		
	}
	
	@Test
	public void TC21LeadsSelectView() {
		
		WebElement leadsPageViewDropDown = findElementBy("leadsPageViewDropDown");
		List<String> actualLeadsViewOptionsList=getDropDownOptionsTextUsingSelect(leadsPageViewDropDown);
		List<String> expectedLeadsViewOptionsList=new ArrayList<String>(Arrays.asList(SalesForceConstants.LEADS_DROPDOWN_OPTIONS));
		softassertObject.assertEquals(actualLeadsViewOptionsList, expectedLeadsViewOptionsList);
		softassertObject.assertAll();
		
	}
	@Test
	public void TC22DefaultViewAndTC23() throws IOException, InterruptedException{
		
		WebElement leadsPageViewDropDown = findElementBy("leadsPageViewDropDown");
		selectOptionByVisibleText(leadsPageViewDropDown, "Today's Leads");
		
//		JavascriptExecutor js = (JavascriptExecutor)driver;
//		js.executeScript("window.scrollBy(0,-100);");
		
		WebElement userMenu = findElementBy("userMenuDropDown");
//		checkIfElementDisplayedByLocator("userMenuDropDown");
		clickOnElement(userMenu,"userMenu");
		WebElement logout = findElementBy("logout");
		clickOnElement(logout, "logout");
		
		
		
		captureScreenshot("Leads/TC22DefaultViewAndTC23_BeforeLogin");
		loginSalesforce(false);
		checkIfCorrectPageDisplayed("homePage");
		
		WebElement leadsPageLink = findElementBy("leadsPageLink");
		clickOnElement(leadsPageLink,"leadsPageLink");
		checkIfCorrectPageDisplayed("leadsPage");
		
//		WebElement closePopUp = findElementBy("id","tryLexDialogX");
//		clickOnElement(closePopUp,"closePopUp");
		
		WebElement leadsPageViewDropDownGoButton = findElementBy("leadsPageViewDropDownGoButton");
		clickOnElement(leadsPageViewDropDownGoButton,"leadsPageViewDropDownGoButton");
		
		String expectedViewOption = "Today's Leads";
		leadsPageViewDropDown = findElementBy("leadsPageViewDropDown");
		Select select = new Select(leadsPageViewDropDown);
		String actualViewOption = getText(select.getFirstSelectedOption());
		captureScreenshot("Leads/TC22DefaultViewAndTC23_SelectedViewPage");
		softassertObject.assertEquals(actualViewOption, expectedViewOption);
	}
	
	@Test
	public void TC24() throws IOException {
		WebElement leadsPageNewButton = findElementBy("leadsPageNewButton");
		clickOnElement(leadsPageNewButton,"leadsPageNewButton");
		
		WebElement newLeadsPageLastNameTextBox = findElementBy("newLeadsPageLastNameTextBox");
		enterText(newLeadsPageLastNameTextBox,"ABCD");
		
		WebElement newLeadsPageCompanyNameTextBox = findElementBy("newLeadsPageCompanyNameTextBox");
		enterText(newLeadsPageCompanyNameTextBox,"ABCD");
		
		WebElement newLeadsPageSaveButton = findElementBy("newLeadsPageSaveButton");
		clickOnElement(newLeadsPageSaveButton,"newLeadsPageSaveButton");
		
		captureScreenshot("Leads/TC24_newLeadsPage");
		WebElement nameInNewLeadsPage =findElementBy("newLeadsPageName");
		
		checkIfCorrectPageWithNameisDisplayed(nameInNewLeadsPage,"newLeadsPage");
		
	}
}
