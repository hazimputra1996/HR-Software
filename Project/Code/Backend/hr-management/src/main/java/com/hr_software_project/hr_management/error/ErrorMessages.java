package com.hr_software_project.hr_management.error;

import java.util.MissingResourceException;
import java.util.ResourceBundle;


public final class ErrorMessages {


	private static final String BUNDLE_NAME = "errormessages";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private ErrorMessages() {
	}

	public static String getCode(String key) {
		try {
			
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return String.format("!%s!", key);
		}
	}
}
