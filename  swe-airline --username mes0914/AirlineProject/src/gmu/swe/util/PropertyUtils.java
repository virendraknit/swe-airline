package gmu.swe.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertyUtils {
	private static Properties properties;
	private static ResourceBundle resourceBundle;
	
	private static void loadProperties(){
		loadProperties("");
	}
	
	private static void loadProperties(String propertyFilePath){
		if(properties == null){
			properties = new Properties();
			try {
				ResourceBundle rb = ResourceBundle.getBundle("airlineDb.properties");
				
				properties.load(new FileInputStream(propertyFilePath + "/airlineDb.properties"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getProperty(String propertyName){
		if(properties == null){
			loadProperties();
		}
		return properties.getProperty(propertyName);
	}
	
	public static String getProperty(String propertyName, String propertyFilePath){
		if(properties == null){
			loadProperties(propertyFilePath);
		}
		return properties.getProperty(propertyName);
	}
}
