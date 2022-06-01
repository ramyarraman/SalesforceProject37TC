package com.salesforce.testScripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import org.openqa.selenium.WebElement;



import com.salesforce.base.BaseMethods;
import com.salesforce.base.TestFailUserException;
import com.salesforce.utilities.CommonUtilities;
import com.salesforce.utilities.SalesForceConstants;

//@Listeners(com.salesforce.utilities.ListenersAndExtentReports.class)
public class SFDCLoginAutomationScripts extends BaseMethods{

	//SFDC Login test cases begin..................................................................................................
	@Test(priority=0,enabled=false)	
	public static void LoginErrorMessage1() throws TestFailUserException, IOException{

		//Test case Condition 1..........................................................
		checkIfCorrectPageDisplayed("loginPage");

		//Test case Condition 2..........................................................
		String userNameValue = CommonUtilities.getProperty("userName",SalesForceConstants.SALESFORCE_PROPERTIES_PATH);
		WebElement userName = findElementBy("userName");

		enterText(userName,userNameValue);
		System.out.println("userName entered: "+userNameValue);
		String actualText = getAttributeValueOfElement(userName,"value");			
		Assert.assertEquals(actualText,userNameValue);

		//Test case Condition 3..........................................................
		locatorName = CommonUtilities.getProperty("passwordLocator", SalesForceConstants.SALESFORCE_OBJECTREPOSITORY_PATH);
		locatorValue = CommonUtilities.getProperty("passwordLocatorValue", SalesForceConstants.SALESFORCE_OBJECTREPOSITORY_PATH);
		WebElement password = findElementBy(locatorName,locatorValue);

		clearText(password);
		String actualpassword = getAttributeValueOfElement(password,"value");			
		Assert.assertEquals(actualpassword,"");
		
		captureScreenshot("SFDCLogin/LoginErrorMessage1_UserNameAndPasswordEntered");

		
		//Test case Condition 4..........................................................
	
		WebElement loginButton= findElementBy("loginButton");	
		clickOnElement(loginButton,"loginButton");
	
		WebElement noPasswordError= findElementBy("loginError");	
		String actualErrorText = getText(noPasswordError);
		String expectedError = CommonUtilities.getProperty("noPasswordErrorMessage",SalesForceConstants.SALESFORCE_PROPERTIES_PATH);

		Assert.assertEquals(actualErrorText,expectedError);

		captureScreenshot("SFDCLogin/LoginErrorMessage1_NoPasswordError");
		
	}

	@Test(priority=3,enabled=true)
	public static void LoginToSalesForce2() throws TestFailUserException, IOException {

		//Test case Condition 1..........................................................
		checkIfCorrectPageDisplayed("loginPage");		

		loginSalesforce(false);	

		waitForPageToLoad(10);
		String pageTitle = getPageTitle();
		//Test case Condition 2..........................................................
		captureScreenshot("SFDCLogin/LoginToSalesForce2_WelcomeToFreeTrial");
//		try {
		Assert.assertEquals(pageTitle,"Welcome to your free trial should be displayed","Welcome to your free trial not displayed,Test Failed");
//		}catch(Error e) {
//			report.logTestFailedWithError(e);
//		}
			
	}
	@Test(priority=1,enabled=false)
	public static void CheckRemeberMe3() throws TestFailUserException, IOException {

		//Test case Condition 1..........................................................
		checkIfCorrectPageDisplayed("loginPage");

		//Test case Condition 2..........................................................
		loginSalesforce(true);	
		waitForPageToLoad(40);
		checkIfCorrectPageDisplayed("homePage");

		//Test case Condition 3..........................................................
				
		WebElement userMenu = findElementBy("userMenuDropDown");

		clickOnElement(userMenu,"userMenu");

		WebElement logout = findElementBy("logout");

		clickOnElement(logout,"logout");

		checkIfCorrectPageDisplayed("loginPage");

		locatorName = CommonUtilities.getProperty("rememberMeCheckBoxLocator",SalesForceConstants.SALESFORCE_OBJECTREPOSITORY_PATH);
		locatorValue = CommonUtilities.getProperty("rememberMeCheckBoxLocatorValue",SalesForceConstants.SALESFORCE_OBJECTREPOSITORY_PATH);
		boolean rememberMeSelection= checkIfElementSelectedByLocator(locatorName,locatorValue);
		captureScreenshot("SFDCLogin/CheckRemeberMe3_RememberMeSelectedAndUserNameDisplayed");

		Assert.assertTrue(rememberMeSelection,"Remember Me checkbox not selected");
		
		//Test case Condition 4..........................................................

		String validUserName = CommonUtilities.getProperty("userName",SalesForceConstants.SALESFORCE_PROPERTIES_PATH);

		WebElement userName = findElementBy("userName");
		String actualText = getAttributeValueOfElement(userName,"value");			
		Assert.assertEquals(actualText.toLowerCase(),validUserName);
		//			locator = By.xpath("//*[@id=\"idcard-identity\"]");
		//			waitSecondsForElementLocatedBy(30, locator);
		//			WebElement userName = findElementBy(locator);
		//			String actualText = userName.getText();
		//			checkIfCorrectTextDisplayed(actualText,validUserName);
		
	}

	//password reset

	@Test(priority=4,enabled=false)
	public static void ForgotPassword4A() throws TestFailUserException, IOException {

		//Test case Condition 1..........................................................
		checkIfCorrectPageDisplayed("loginPage");


		WebElement forgotPassword = findElementBy("forgotPassword");
		clickOnElement(forgotPassword,"forgotPassword");
		//Test case Condition 2..........................................................
		checkIfCorrectPageDisplayed("forgotPassword");
		captureScreenshot("SFDCLogin/ForgotPassword4A_pageForgotPassword");

		WebElement userName = findElementBy("xpath","//*[@id='un']");
		String userNameValue = CommonUtilities.getProperty("userName",SalesForceConstants.SALESFORCE_PROPERTIES_PATH);
		enterText(userName,userNameValue);

		WebElement continueButton = findElementBy("id","continue");
		clickOnElement(continueButton,"continueButton");
		
		captureScreenshot("SFDCLogin/ForgotPassword4A_ResetPasswordMessage");

		WebElement forgotPasswordMessage = findElementBy("xpath","//div[@id='forgotPassForm']/div[1]/p[1]");
		String expectedMessage = CommonUtilities.getProperty("passwordResetMessage",SalesForceConstants.SALESFORCE_PROPERTIES_PATH);
		String actualText = forgotPasswordMessage.getText();
		Assert.assertEquals(actualText,expectedMessage);		
		
	}

	@Test(priority=2,enabled=false)
	public void forgotPassword4B() throws TestFailUserException, IOException {

//		//Test case Condition 1..........................................................
		
		checkIfCorrectPageDisplayed("loginPage");

		//Test case Condition 2..........................................................

		WebElement userName = findElementBy("userName");
		waitSecondsForElementVisible(5,userName);
		String userNameEntered = "123";
		enterText(userName,userNameEntered);	
		String usernameValue = userName.getAttribute("value");
		System.out.println("value of username: "+usernameValue);
		Assert.assertEquals(usernameValue,userNameEntered);

		//Test case Condition 3..........................................................

		WebElement password = findElementBy("password");
		String passwordEntered = "22131";
		enterText(password,passwordEntered);
		String passwordValue = password.getAttribute("value");
		System.out.println("value of password: "+passwordValue);
		Assert.assertEquals(passwordValue,passwordEntered);
		captureScreenshot("SFDCLogin/ForgotPassword4B_WrongUserNamePasswordEntered");

		//Test case Condition 3..........................................................

		WebElement loginButton = findElementBy("loginButton");
		clickOnElement(loginButton,"loginButton");	
		WebElement ActualloginError = findElementBy("loginError");
		String expectedError = CommonUtilities.getProperty("loginErrorMessage",SalesForceConstants.SALESFORCE_PROPERTIES_PATH);
		String actualText = ActualloginError.getText();
		captureScreenshot("SFDCLogin/ForgotPassword4B_LoginErrorMessage");
	
		Assert.assertEquals(actualText,expectedError);	
		
	}
}
