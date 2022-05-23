package com.salesforce.testScripts;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;


import com.salesforce.base.BaseMethods;

public class RandomScenarios extends BaseMethods{

	@BeforeMethod
	public void setUpForRandomScenarios() throws InterruptedException {
		loginSalesforce(false);	
		//		checkIfCorrectPageDisplayed("homePage");
	}

	

	@Test(enabled=true)
	public void TC33() throws IOException, InterruptedException {
		WebElement homePageLink = findElementBy("homePageLink");
		clickOnElement(homePageLink,"homePageLink");

		checkIfCorrectPageDisplayed("homeLinkPage");
		WebElement closePopUp = findElementBy("id","tryLexDialogX");
		clickOnElement(closePopUp,"closePopUp");

		WebElement homePageNameLink = findElementBy("homePageNameLink");
		String NameInHomePage=getText(homePageNameLink);
		String tagName = getAttributeValueOfElement(homePageNameLink,"tagName");
		Assert.assertTrue(tagName.equalsIgnoreCase("a"));

		captureScreenshot("RandomScenarios/TC33_homePageHasUserNameLink");

		WebElement userFirstAndLastName = findElementBy("userFirstAndLastName");
		String expectedName=getText(userFirstAndLastName);
		Assert.assertEquals(NameInHomePage, expectedName);

		clickOnElement(homePageNameLink, "homePageNameLink");
		WebElement profilePageFistLastName = findElementBy("profilePageFistLast");
		checkIfCorrectPageWithNameisDisplayed(profilePageFistLastName, "profilePage");
		captureScreenshot("RandomScenarios/TC33_userProfilePage");		
	}
	@Test(enabled=true)	
	@Parameters("TC34_LastNameValue")
	public void TC34(String TC34_LastNameValue) throws IOException, InterruptedException {

		WebElement homePageLink = findElementBy("homePageLink");
		clickOnElement(homePageLink,"homePageLink");

		checkIfCorrectPageDisplayed("homeLinkPage");
		WebElement closePopUp = findElementBy("id","tryLexDialogX");
		clickOnElement(closePopUp,"closePopUp");

		WebElement homePageNameLink = findElementBy("homePageNameLink");

		clickOnElement(homePageNameLink, "homePageNameLink");
		WebElement profilePageFistLastName = findElementBy("profilePageFistLast");
		checkIfCorrectPageWithNameisDisplayed(profilePageFistLastName, "profilePage");
		captureScreenshot("RandomScenarios/TC34_userProfilePage");		

		WebElement contactEditLink = findElementBy("contactEditLink");
		clickOnElement(contactEditLink,"contactEditLink");

		switchToNewFrame("contactInfoContentId");

		WebElement aboutTab = findElementBy("aboutTab");
		clickOnElement(aboutTab,"aboutTab");	

		Actions action = new Actions(driver);
		action.sendKeys(Keys.TAB);

		WebElement aboutTabFirstName = findElementBy("aboutTabFirstName");
		if(aboutTabFirstName.equals(driver.switchTo().activeElement()))
			System.out.println("On focus");
		else
			System.out.println("Not on focus");
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String firstNameText =js.executeScript("return document.getElementById('firstName').value;").toString(); 


		captureScreenshot("RandomScenarios/TC34_editContactsPage_FocusOnFirstName");
		WebElement aboutTabLastName = findElementBy("aboutTabLastName");
		enterText(aboutTabLastName,TC34_LastNameValue);

		WebElement aboutTabSaveAllButton = findElementBy("aboutTabSaveAllButton");
		clickOnElement(aboutTabSaveAllButton,"aboutTabSaveAllButton");

		Thread.sleep(3000);
		System.out.println(driver.getTitle());
		profilePageFistLastName = findElementBy("profilePageFistLast");
		softassertObject.assertEquals(getText(profilePageFistLastName).trim(),firstNameText+" "+TC34_LastNameValue);

		captureScreenshot("RandomScenarios/TC34_profilePage_LastNameUpdate");
		checkIfCorrectPageWithNameisDisplayed(profilePageFistLastName, "profilePage");

		WebElement userFirstAndLastName = findElementBy("userFirstAndLastName");
		String expectedName=firstNameText+" "+TC34_LastNameValue;
		Assert.assertEquals(getText(userFirstAndLastName), expectedName);		
		softassertObject.assertAll();
	}
	@Test(enabled=true)
	@Parameters("TC35_selectedTabsListOptionToSelect_VisibleText")
	public void TC35(String TC35_selectedTabsListOptionToSelect_VisibleText) throws IOException, InterruptedException {
		WebElement addTabPlusSymbol = findElementBy("addTabPlusSymbol");
		clickOnElement(addTabPlusSymbol,"addTabPlusSymbol");

		checkIfCorrectPageDisplayed("allTabsPage");

		WebElement allTabCustomizeMyTabsButton = findElementBy("allTabCustomizeMyTabsButton");
		clickOnElement(allTabCustomizeMyTabsButton,"allTabCustomizeMyTabsButton");

		checkIfCorrectPageDisplayed("customizeMyTabsPage");
		
		Thread.sleep(3000);
		WebElement selectedTabsList = findElementBy("selectedTabsList");
		selectOptionByVisibleText(selectedTabsList,TC35_selectedTabsListOptionToSelect_VisibleText);

		WebElement removeArrow = findElementBy("removeArrow");
		clickOnElement(removeArrow,"removeArrow");

		captureScreenshot("RandomScenarios/TC35_OptionRemovedFromSelectedTabs_AddedToAvailable");
		WebElement availableTabsList = findElementBy("availableTabsList");
		List <String> availableTabsListTextOptions = getDropDownOptionsTextUsingSelect(availableTabsList);
		softassertObject.assertTrue(availableTabsListTextOptions.contains(TC35_selectedTabsListOptionToSelect_VisibleText));

		WebElement customizeMyTabsSaveButton = findElementBy("customizeMyTabsSaveButton");
		clickOnElement(customizeMyTabsSaveButton,"customizeMyTabsSaveButton");

		checkIfCorrectPageDisplayed("allTabsPage");
		captureScreenshot("RandomScenarios/TC35_OptionRemovedFromSelectedTabs_OptionNotAvailableInTabBarAllTabsPage");
		softassertObject.assertFalse(getTabBarOptions().contains(TC35_selectedTabsListOptionToSelect_VisibleText));

		WebElement userMenu = findElementBy("userMenuDropDown");
		checkIfElementDisplayedByLocator("userMenuDropDown");
		clickOnElement(userMenu,"userMenu");
		WebElement logout = findElementBy("logout");
		mouseOverAndClickUsingActions(logout, "logout");

		
		Thread.sleep(3000);
		captureScreenshot("RandomScenarios/TC35_afterLogout");
		loginSalesforce(false);
		captureScreenshot("RandomScenarios/TC35_OptionRemovedFromSelectedTabs_OptionNotAvailableInTabBarAfterReLogin");
		softassertObject.assertFalse(getTabBarOptions().contains(TC35_selectedTabsListOptionToSelect_VisibleText));	

		//Adding the removed option so the case can be repeated without manually adding the option - not included with test case

		addTabPlusSymbol = findElementBy("addTabPlusSymbol");
		clickOnElement(addTabPlusSymbol,"addTabPlusSymbol");

		allTabCustomizeMyTabsButton = findElementBy("allTabCustomizeMyTabsButton");
		clickOnElement(allTabCustomizeMyTabsButton,"allTabCustomizeMyTabsButton");

		availableTabsList = findElementBy("availableTabsList");
		selectOptionByVisibleText(availableTabsList,TC35_selectedTabsListOptionToSelect_VisibleText);

		WebElement addArrow = findElementBy("addArrow");
		clickOnElement(addArrow,"addArrow");
		customizeMyTabsSaveButton = findElementBy("customizeMyTabsSaveButton");
		clickOnElement(customizeMyTabsSaveButton,"customizeMyTabsSaveButton");	
		
		softassertObject.assertAll();
	}
	@Test(enabled = true)
	@Parameters("TC36_calenderTimeLink")
	public void TC36(String TC36_calenderTimeLink) throws IOException, InterruptedException {
		WebElement homePageLink = findElementBy("homePageLink");
		clickOnElement(homePageLink,"homePageLink");

		checkIfCorrectPageDisplayed("homeLinkPage");
		WebElement closePopUp = findElementBy("id","tryLexDialogX");
		clickOnElement(closePopUp,"closePopUp");

		Date Todaysdate = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("EEEEE MMM dd, YYYY");
		String formattedTodaysDate = sf.format(Todaysdate);

		WebElement homePageDateLink = findElementBy("homePageDateLink");
		softassertObject.assertTrue(getAttributeValueOfElement(homePageDateLink, "tagName").equalsIgnoreCase("a"));

		String actualDateDisplayed = getText(homePageDateLink);
		softassertObject.assertEquals(actualDateDisplayed, formattedTodaysDate);
		clickOnElement(homePageDateLink, "homePageDateLink");

		WebElement userFirstAndLastName = findElementBy("userFirstAndLastName");		
		checkIfCorrectPageWithNameisDisplayed(userFirstAndLastName,"calenderForUserNamePage");
		captureScreenshot("RandomScenarios/TC36_calenderForUserNamePage");

		WebElement calenderTimeLink = findElementBy(TC36_calenderTimeLink);
		clickOnElement(calenderTimeLink,"calenderTimeLink");

		checkIfCorrectPageDisplayed("calenderNewEventPage");
		WebElement userCalenderNewEventSubjectTextBox = findElementBy("userCalenderNewEventSubjectTextBox");
		softassertObject.assertEquals(userCalenderNewEventSubjectTextBox, driver.switchTo().activeElement());

		WebElement userCalenderNewEventSubjectComboIcon = findElementBy("userCalenderNewEventSubjectComboIcon");
		clickOnElement(userCalenderNewEventSubjectComboIcon,"userCalenderNewEventSubjectComboIcon");

		
//		//new code adding to see if this resolves the error 
//		int numberOfWindows = driver.getWindowHandles().size();
//		String currentWindowHandle=driver.getWindowHandle();
//		if(numberOfWindows>2) {
//			System.out.println("More Than 2 windows");					
//			for(int i=0;i<numberOfWindows;i++) {
//				switchToNewWindow();
//				String title = driver.getTitle();
//				if(!(title.equals("ComboBox")&&(!(driver.getWindowHandle().equals(currentWindowHandle))))){
//					driver.close();
//					driver.switchTo().window(currentWindowHandle);
//					currentWindowHandle = getCurrentWindowHandleAndSwitchToNewWindow();
//				}else if(title.equals("ComboBox"))
//						break;
//			}
//		}else
//			currentWindowHandle = getCurrentWindowHandleAndSwitchToNewWindow();
		
		String currentWindowHandle = getCurrentWindowHandleAndSwitchToNewWindow();
		checkIfCorrectPageDisplayed("comboBoxPage");
		System.out.println("Page Title: "+getPageTitle());
		
		WebElement userCalenderComboBoxOtherLink = findElementBy("userCalenderComboBoxOtherLink");
		clickOnElement(userCalenderComboBoxOtherLink,"userCalenderComboBoxOtherLink");

		switchToWindow(currentWindowHandle);

		int countOfWindows = getCountOfWindows();
		softassertObject.assertTrue(countOfWindows==1);
		captureScreenshot("RandomScenarios/TC36_calenderForUserNamePage_SubjectTextBoxEnteredAndComBoBoxClosed");

		userCalenderNewEventSubjectTextBox = findElementBy("userCalenderNewEventSubjectTextBox");
		JavascriptExecutor js = (JavascriptExecutor)driver; 
		String actualTextInSubjectTextBox = js.executeScript("return document.getElementById('evt5').value;").toString();
		softassertObject.assertEquals(actualTextInSubjectTextBox,"Other");

		WebElement userCalenderNewEventEndTimeTextBox = findElementBy("userCalenderNewEventEndTimeTextBox");
		clickOnElement(userCalenderNewEventEndTimeTextBox,"userCalenderNewEventEndTimeTextBox");

		captureScreenshot("RandomScenarios/TC36_userCalenderNewEventEndTimeTextBoxSelection");

		String defaultTimeValue = getAttributeValueOfElement(userCalenderNewEventEndTimeTextBox, "value");
		softassertObject.assertEquals(defaultTimeValue,"9:00 PM");

		String selectedTime= js.executeScript("return document.getElementById('EndDateTime_time').value;").toString();
		softassertObject.assertEquals(selectedTime,"9:00 PM");		

		WebElement userCalenderNewEvenSaveButton = findElementBy("userCalenderNewEvenSaveButton");
		clickOnElement(userCalenderNewEvenSaveButton,"userCalenderNewEvenSaveButton");


		js.executeScript("window.scrollBy(0,500);");
		captureScreenshot("RandomScenarios/TC36_calenderForUserNamePageWithAddedEvent");

		userFirstAndLastName = findElementBy("userFirstAndLastName");
		checkIfCorrectPageWithNameisDisplayed(userFirstAndLastName,"calenderForUserNamePage");

		WebElement userCalenderPageEventIn8PMTimeSlot = findElementBy("userCalenderPageEventIn8PMTimeSlot");
		String tagName = getAttributeValueOfElement(userCalenderPageEventIn8PMTimeSlot, "tagName");
		softassertObject.assertTrue(tagName.equalsIgnoreCase("a"));

		String EventDetail =  getText(userCalenderPageEventIn8PMTimeSlot);
		softassertObject.assertTrue(EventDetail.equals("Other"));		
	}

	@Test(enabled=true)
	@Parameters("TC37_calenderTimeLink")
	public void TC37(String TC37_calenderTimeLink) throws IOException, InterruptedException {
		WebElement homePageLink = findElementBy("homePageLink");
		clickOnElement(homePageLink,"homePageLink");

		checkIfCorrectPageDisplayed("homeLinkPage");
		WebElement closePopUp = findElementBy("id","tryLexDialogX");
		clickOnElement(closePopUp,"closePopUp");

		Date todaysDateTime = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("EEEEE MMM dd, YYYY");
		String formattedTodaysDate = sf.format(todaysDateTime);

		WebElement homePageDateLink = findElementBy("homePageDateLink");
		softassertObject.assertTrue(getAttributeValueOfElement(homePageDateLink, "tagName").equalsIgnoreCase("a"));

		String actualDateDisplayed = getText(homePageDateLink);
		softassertObject.assertEquals(actualDateDisplayed, formattedTodaysDate);
		clickOnElement(homePageDateLink, "homePageDateLink");

		WebElement userFirstAndLastName = findElementBy("userFirstAndLastName");		
		checkIfCorrectPageWithNameisDisplayed(userFirstAndLastName,"calenderForUserNamePage");
		captureScreenshot("RandomScenarios/TC37_calenderForUserNamePage");

		WebElement calenderTimeLink = findElementBy(TC37_calenderTimeLink);
		clickOnElement(calenderTimeLink,"calenderTimeLink");

		checkIfCorrectPageDisplayed("calenderNewEventPage");
		WebElement userCalenderNewEventSubjectTextBox = findElementBy("userCalenderNewEventSubjectTextBox");
		softassertObject.assertEquals(userCalenderNewEventSubjectTextBox, driver.switchTo().activeElement());

		WebElement userCalenderNewEventSubjectComboIcon = findElementBy("userCalenderNewEventSubjectComboIcon");
		clickOnElement(userCalenderNewEventSubjectComboIcon,"userCalenderNewEventSubjectComboIcon");

//		//new code adding to see if this resolves the error 
//		int numberOfWindows = driver.getWindowHandles().size();
//		String currentWindowHandle=driver.getWindowHandle();
//		if(numberOfWindows>2) {
//			System.out.println("More Than 2 windows");					
//			for(int i=0;i<numberOfWindows;i++) {
//				switchToNewWindow();
//				String title = driver.getTitle();
//				if(!(title.equals("ComboBox")&&(!(driver.getWindowHandle().equals(currentWindowHandle))))){
//					driver.close();
//					driver.switchTo().window(currentWindowHandle);
//					currentWindowHandle = getCurrentWindowHandleAndSwitchToNewWindow();
//				}else if(title.equals("ComboBox"))
//						break;
//			}
//		}else
//			currentWindowHandle = getCurrentWindowHandleAndSwitchToNewWindow();

		String currentWindowHandle = getCurrentWindowHandleAndSwitchToNewWindow();
		checkIfCorrectPageDisplayed("comboBoxPage");



		WebElement userCalenderComboBoxOtherLink = findElementBy("userCalenderComboBoxOtherLink");
		clickOnElement(userCalenderComboBoxOtherLink,"userCalenderComboBoxOtherLink");

		switchToWindow(currentWindowHandle);

		int countOfWindows = getCountOfWindows();
		softassertObject.assertTrue(countOfWindows==1);
		captureScreenshot("RandomScenarios/TC37_calenderForUserNamePage_SubjectTextBoxEnteredAndComBoBoxClosed");

		userCalenderNewEventSubjectTextBox = findElementBy("userCalenderNewEventSubjectTextBox");
		JavascriptExecutor js = (JavascriptExecutor)driver; 
		String actualTextInSubjectTextBox = js.executeScript("return document.getElementById('evt5').value;").toString();
		softassertObject.assertEquals(actualTextInSubjectTextBox,"Other");

		WebElement userCalenderNewEventEndTimeTextBox = findElementBy("userCalenderNewEventEndTimeTextBox");
		clickOnElement(userCalenderNewEventEndTimeTextBox,"userCalenderNewEventEndTimeTextBox");

		captureScreenshot("RandomScenarios/TC37_userCalenderNewEventEndTimeTextBoxSelection");

		String defaultTimeValue = getAttributeValueOfElement(userCalenderNewEventEndTimeTextBox, "value");
		softassertObject.assertEquals(defaultTimeValue,"5:00 PM");

		Actions action = new Actions(driver);
		action.sendKeys(Keys.ARROW_DOWN)
		.sendKeys(Keys.ARROW_DOWN)
		.sendKeys(Keys.ARROW_DOWN)
		.sendKeys(Keys.ARROW_DOWN)
		.sendKeys(Keys.ENTER)
		.perform();

		String selectedTime= js.executeScript("return document.getElementById('EndDateTime_time').value;").toString();
		System.out.println(selectedTime);
		softassertObject.assertEquals(selectedTime,"7:00 PM");

		WebElement userCalenderNewEventRecurrenceCheckBox = findElementBy("userCalenderNewEventRecurrenceCheckBox");
		clickOnElement(userCalenderNewEventRecurrenceCheckBox,"userCalenderNewEventRecurrenceCheckBox");

		softassertObject.assertTrue(checkIfElementSelected("userCalenderNewEventRecurrenceCheckBox"));

		WebElement userCalenderNewEventRecurrenceFrequencyWeeklyRadioButton = findElementBy("userCalenderNewEventRecurrenceFrequencyWeeklyRadioButton");
		clickOnElement(userCalenderNewEventRecurrenceFrequencyWeeklyRadioButton,"userCalenderNewEventRecurrenceFrequencyWeeklyRadioButton");

		String actualTextInRecursEveryTextBox = js.executeScript("return document.getElementById('wi').value;").toString();
		softassertObject.assertEquals(actualTextInRecursEveryTextBox,"1");

		List<WebElement> checkBoxOptions = findElementsBy("xpath","//*[@id=\"w\"]/div[2]/input");
		String selectedDayLabel="";
		for(WebElement option: checkBoxOptions) {
			boolean flag = checkIfElementSelected(option);
			if(flag) {
				String idValue = getAttributeValueOfElement(option,"id");
				List<WebElement> checkBoxLabelOptions = findElementsBy("xpath","//*[@id=\"w\"]/div[2]/label");
				for(WebElement checkBoxLabelOption:checkBoxLabelOptions) {
					if(getAttributeValueOfElement(checkBoxLabelOption, "for").equals(idValue)) {
						selectedDayLabel = getText(checkBoxLabelOption);
					}
				}
			}
		}


		sf = new SimpleDateFormat("EEEEE");
		String todaysDay = sf.format(todaysDateTime);
		softassertObject.assertEquals(selectedDayLabel, todaysDay);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(todaysDateTime);
		calendar.add(Calendar.DAY_OF_MONTH,14);
		Date twoWeeksLater = calendar.getTime();
		SimpleDateFormat datef = new SimpleDateFormat("MM/dd/yyyy");
		String date2WeeksLater =datef.format(twoWeeksLater);
		System.out.println("day2weekslater"+date2WeeksLater);
		
//		SimpleDateFormat Monthf=new SimpleDateFormat("MMMM");
//		String month2WeeksLater = Monthf.format(twoWeeksLater);
//		if(Integer.parseInt(date2WeeksLater)<10) {
//			date2WeeksLater=date2WeeksLater.substring(1);
//			System.out.println("new date2WeeksLater"+date2WeeksLater);
//		}
//		System.out.println("date2WeeksLater"+date2WeeksLater);
//		System.out.println("Month"+month2WeeksLater);
		

		WebElement userCalenderNewEventRecurrenceFrequencyEndDateTextBox = findElementBy("userCalenderNewEventRecurrenceFrequencyEndDateTextBox");
//		clickOnElement(userCalenderNewEventRecurrenceFrequencyEndDateTextBox,"userCalenderNewEventRecurrenceFrequencyEndDateTextBox");
		
		enterText(userCalenderNewEventRecurrenceFrequencyEndDateTextBox, date2WeeksLater);
		

//		WebElement monthDropDown=findElementBy("userCalenderNewEventRecurrenceFrequencyEndMonthPickerCalenderDates");
//		selectOptionByVisibleText(monthDropDown, month2WeeksLater);
//		List<WebElement> EndDatePickerCalenderDates = findElementsBy("userCalenderNewEventRecurrenceFrequencyEndDatePickerCalenderDates");
//		for(WebElement dateOption: EndDatePickerCalenderDates) {
//			if(getText(dateOption).equals(date2WeeksLater)) {
//				clickOnElement(dateOption, "date2WeeksLater");
//			}
//		}	
		
		webDriverWait(5);

		js.executeScript("window.scrollBy(0,300);");
		captureScreenshot("RandomScenarios/TC37_userCalenderNewEventEndTimeUpdateAndRecurrence");

		WebElement userCalenderNewEvenSaveButton = findElementBy("userCalenderNewEvenSaveButton");
		clickOnElement(userCalenderNewEvenSaveButton,"userCalenderNewEvenSaveButton");

		js.executeScript("window.scrollBy(0,500);");
		captureScreenshot("RandomScenarios/TC37_calenderForUserNamePageWithAddedEvent");

		userFirstAndLastName = findElementBy("userFirstAndLastName");
		checkIfCorrectPageWithNameisDisplayed(userFirstAndLastName,"calenderForUserNamePage");

		WebElement userCalenderPageMultiEventIn4TO7PMTimeSlot = findElementBy("userCalenderPageMultiEventIn4TO7PMTimeSlot");
		softassertObject.assertTrue(userCalenderPageMultiEventIn4TO7PMTimeSlot.isDisplayed());

		WebElement userCalenderPageEventIn4To7PMTimeSlotEventDetail = findElementBy("userCalenderPageEventIn4To7PMTimeSlotEventDetail");

		String tagName = getAttributeValueOfElement(userCalenderPageEventIn4To7PMTimeSlotEventDetail,"tagName");
		softassertObject.assertTrue(tagName.equalsIgnoreCase("a"));

		String eventDetail = getText(userCalenderPageEventIn4To7PMTimeSlotEventDetail);
		//String eventDetail =  js.executeScript("return document.getElementById('p:f:j_id25:j_id69:20:j_id71:0:j_id72:calendarEvent:j_id84').value;").toString();
		System.out.println("eventDetail "+eventDetail);
		softassertObject.assertEquals(eventDetail,"Other");	

		WebElement userCalenderPageMonthViewIcon = findElementBy("userCalenderPageMonthViewIcon");
		clickOnElement(userCalenderPageMonthViewIcon,"userCalenderPageMonthViewIcon");

		js.executeScript("window.scrollBy(0,100);");
		captureScreenshot("RandomScenarios/TC37_userCalenderPageMonthViewIconWithAddedDetails");

		userFirstAndLastName = findElementBy("userFirstAndLastName");
		checkIfCorrectPageWithNameisDisplayed(userFirstAndLastName,"calenderForUserNamePageMonthView");


		WebElement userCalenderPageMonthViewTodaysDateEventDetail = findElementBy("userCalenderPageMonthViewTodaysDateEventDetail");
		softassertObject.assertEquals(getText(userCalenderPageMonthViewTodaysDateEventDetail), "Other");
		softassertObject.assertTrue(getAttributeValueOfElement(userCalenderPageMonthViewTodaysDateEventDetail, "tagName").equalsIgnoreCase("a"));

		List<WebElement> allDates = findElementsBy("userCalenderPageMonthViewAllDatesOtherThanTodayList");
		for(WebElement date:allDates) {
			WebElement dateValue = date.findElement(By.xpath("div/a[2]"));
			if(getText(dateValue).equals(date2WeeksLater)){
				WebElement eventDetailElement = date.findElement(By.xpath("div[2]/a"));
				String eventDetailText = getText(eventDetailElement);						
				softassertObject.assertEquals(eventDetailText, "Other");
				softassertObject.assertTrue(getAttributeValueOfElement(eventDetailElement,"tagName").equalsIgnoreCase("a"));
			}
		}	
		softassertObject.assertAll();
	}
}
