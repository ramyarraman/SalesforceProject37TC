package com.salesforce.utilities;

import java.io.IOException;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class GenerateReports {
	ExtentHtmlReporter htmlReporter;
	public static ExtentReports extentReportsObj;
	public static ExtentTest extentTestObj;
	private static GenerateReports obj;
	private GenerateReports() {
		// TODO Auto-generated constructor stub
	}
	public static GenerateReports getInstance() {
		if(obj==null) {
			obj=new GenerateReports();
		}
		return obj;
	}

	static String testcaseName = null;

	public void startExtentReport() {
		Date currentdatetime = new Date();

		htmlReporter = new ExtentHtmlReporter(SalesForceConstants.GENERATE_REPORT_PATH+"extentReport"+currentdatetime+".html");
		extentReportsObj = new ExtentReports();
		extentReportsObj.attachReporter(htmlReporter);
		extentReportsObj.setSystemInfo("Host Name", "Salesforce");
		extentReportsObj.setSystemInfo("Environment", "Automation Testing");
		extentReportsObj.setSystemInfo("User Name", "Ramya");
		

		htmlReporter.config().setDocumentTitle("Test Execution Report");
		htmlReporter.config().setReportName("Salesforce Automation Tests");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.STANDARD);
		
	}

	public void startSingleTestReport(String testName) {
		testcaseName = testName;
		extentTestObj = extentReportsObj.createTest(testName);
	}

	public void logTestInfo(String message) {

		extentTestObj.log(Status.INFO, message);
	}

	public void logTestSkipped() {

		extentTestObj.log(Status.SKIP, MarkupHelper.createLabel(testcaseName + " Skipped", ExtentColor.YELLOW));
	}
	
	public void logTestpassed() {
		extentTestObj.log(Status.PASS, MarkupHelper.createLabel(testcaseName + " Passed", ExtentColor.GREEN));
	}

	public void logTestFailed() {
		extentTestObj.log(Status.FAIL, MarkupHelper.createLabel(testcaseName + " Failed", ExtentColor.RED));
	}
	public void logTestFailedWithError(Throwable e) {
		extentTestObj.log(Status.FAIL,e);		
	}

	public void endReport() {
		extentReportsObj.flush();
	}
	public void attachScreeshot(String fileName) throws IOException {
		extentTestObj.addScreenCaptureFromPath(fileName);
	}
}
