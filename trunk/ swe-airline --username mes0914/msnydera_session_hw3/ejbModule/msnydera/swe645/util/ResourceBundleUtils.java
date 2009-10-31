/*
 * Created by: Matt Snyder
 */
package msnydera.swe645.util;

import java.util.ResourceBundle;

/**
 * Utility class used to read in a property file and provide methods to get
 * property values.
 * 
 */
public class ResourceBundleUtils {
	private static ResourceBundle resourceBundle;

	/**
	 * Loads the properties
	 */
	private static void loadProperties() {
		loadProperties("");
	}

	/**
	 * Loads the property file in the given propertyFilePath. The property file
	 * name should be airlineDb.properties.
	 * 
	 * @param propertyFilePath
	 *            Path to the property file.
	 */
	private static void loadProperties(String propertyFilePath) {
		if (resourceBundle == null) {
			resourceBundle = ResourceBundle.getBundle(propertyFilePath + "airlineDb");
		}
	}

	/**
	 * Returns the property value for the provided propertyName. This method
	 * assumes the provided propertyName is not null.
	 * 
	 * @param propertyName
	 *            Name of the property to lookup.
	 * @return Value for the provided property name.
	 */
	public static String getProperty(String propertyName) {
		if (resourceBundle == null) {
			loadProperties();
		}
		return resourceBundle.getString(propertyName);
	}

	/**
	 * Returns the property value for the provided propertyName. This method
	 * assumes the provided propertyName is not null.
	 * 
	 * If the property file is not already loaded, the propertyFilePath
	 * parameter will be used to lookup the property file in that location.
	 * 
	 * @param propertyName
	 *            Name of the property to lookup.
	 * @param propertyFilePath
	 *            Path to the property file (only used if the property file is
	 *            not yet loaded)
	 * @return
	 */
	public static String getProperty(String propertyName, String propertyFilePath) {
		if (resourceBundle == null) {
			loadProperties(propertyFilePath);
		}
		return resourceBundle.getString(propertyName);
	}
}
