package com.weatherwebsite.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class ConversionHelper {

	private ConversionHelper() {}

	/**
	 * This method converts unix time in seconds to real time.
	 *
	 * @param unixTime
	 * @return real time
	 */
	public static String unixToRealTime(long unixTime) {
		LocalTime realTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), ZoneOffset.UTC).toLocalTime();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
		return realTime.format(formatter);
	}

	/**
	 * This method converts fahrenheit temperature to celsius.
	 *
	 * @param fahrenheit
	 * @return temperature in celsius units.
	 */
	public static int fahrenheitToCelsius(int fahrenheit) {
		return (fahrenheit - 32) * 5 / 9;
	}
}
