package com.salesforce.utilities;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.salesforce.base.BaseMethods;

public class ListenersAndExtentReports extends BaseMethods implements ITestListener{
	
	public static GenerateReports listnerObjforReports;
	
	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Listener On TestStart");
//		listnerObjforReports.startSingleTestReport(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		listnerObjforReports.logTestpassed();
//		String className = result.getMethod().getRealClass().toString();
////		System.out.println(className);
//		String testMethodName = result.getName();
//		try {
//			captureScreenshot("/listenerScreenShot/PassedCasesScreenshot/"+className+"/"+testMethodName);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeAllDriver();
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		listnerObjforReports.logTestFailed();
		listnerObjforReports.logTestFailedWithError(result.getThrowable());
		String className = result.getMethod().getRealClass().toString();
//		System.out.println(className);
		String testMethodName = result.getName();
		try {
			captureScreenshot("/listenerScreenShot/FailedCasesScreenshot/"+className+"/"+testMethodName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeAllDriver();
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		listnerObjforReports.logTestSkipped();
		closeAllDriver();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {	
		System.out.println("Listener On Start");
		listnerObjforReports = GenerateReports.getInstance();
		listnerObjforReports.startExtentReport();
	}

	@Override
	public void onFinish(ITestContext context) {
		listnerObjforReports.endReport();
	}
	
	
}
