package com.salesforce.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class CommonUtilities {
	
	public static String getProperty(String Key,String filePath) {
		File file = new File(filePath);
		FileReader fr; String value="";
		try {
			fr = new FileReader(file);
			Properties pf = new Properties();	
			pf.load(fr);
			value = pf.getProperty(Key);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return value;
	}

}
