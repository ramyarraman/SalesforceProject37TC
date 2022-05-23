package com.salesforce.utilities;

public class SalesForceConstants {
	public static final String USER_DIR=System.getProperty("user.dir");
	public static final String SALESFORCE_PROPERTIES_PATH = USER_DIR+"/src/test/resources/data.properties";
	public static final String SALESFORCE_OBJECTREPOSITORY_PATH = USER_DIR+"/src/test/resources/ObjectRepository.properties";
	public static final String[] USER_MENU_DROPDOWN_OPTIONS = {"My Profile","My Settings","Developer Console","Switch to Lightning Experience","Logout"};
	public static final String SALESFORCE_SCREENSHOTFOLDER_PATH = USER_DIR+"/resources/";
	public static final String[] OPPORTUNITIES_DROPDOWN_OPTIONS = {"All Opportunities","Closing Next Month","Closing This Month","My Opportunities","New Last Week","New This Week","Opportunity Pipeline","Private","Recently Viewed Opportunities","Won"};
	public static final String[] LEADS_DROPDOWN_OPTIONS = {"All Open Leads","My Unread Leads","Recently Viewed Leads","Today's Leads","View - Custom 1","View - Custom 2"};
	public static final String GENERATE_REPORT_PATH = USER_DIR+"/extentReports/";
}
