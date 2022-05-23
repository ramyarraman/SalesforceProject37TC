package com.salesforce.base;

public class TestFailUserException extends Exception{

	private static final long serialVersionUID = 1L;
	public TestFailUserException(String message){
		super(message);
	}	
}