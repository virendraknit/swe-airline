package gmu.swe.util;

import java.util.ResourceBundle;

public class ResourceBundleUtils {
	private static ResourceBundle resourceBundle;

	private static void loadProperties() {
		loadProperties("");
	}

	private static void loadProperties(String propertyFilePath) {
		if (resourceBundle == null) {
			resourceBundle = ResourceBundle.getBundle(propertyFilePath + "airlineDb");
		}
	}

	public static String getProperty(String propertyName) {
		if (resourceBundle == null) {
			loadProperties();
		}
		return resourceBundle.getString(propertyName);
	}

	public static String getProperty(String propertyName, String propertyFilePath) {
		if (resourceBundle == null) {
			loadProperties(propertyFilePath);
		}
		return resourceBundle.getString(propertyName);
	}

	public static void main(String args[]) {
		System.out.println("ya " + ResourceBundleUtils.getProperty("DB_FILE_PATH"));
	}
}
