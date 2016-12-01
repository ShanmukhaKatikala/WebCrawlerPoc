package com.wipro.poc.constants;

public final class AppConstants {
	public static final String COMMA_SEPARATOR = ",";
	public static final String URL_SELECTOR_REGEX = "a[href]";
	public static final String URL_SELECTOR_ATTRIBUTE = "abs:href";

	public static final String IMG_SELECTOR_REGEX = "img[src~=(?i)\\.(png|jpe?g|gif)]";
	public static final String IMG_SELECTOR_ATTRIBUTE = "abs:src";
	
	public static final int CONNECT_SUCCESS = 200;
	public static final int CONNECT_FAILURE = 500;

	private AppConstants() {
	}
}
