package com.weatherwebsite.utils;

import org.junit.Test;

import com.weatherwebsite.services.exceptions.OpenWeatherRestException;
import com.weatherwebsite.utils.ConversionHelper;

import static org.junit.Assert.assertEquals;

public class ConversionHelperTest {

	@Test
	public void unixToRealTimeTest() throws OpenWeatherRestException {
		assertEquals(ConversionHelper.unixToRealTime(1487925434), "8:37 AM");
		assertEquals(ConversionHelper.unixToRealTime(0), "12:00 AM");
		assertEquals(ConversionHelper.unixToRealTime(-10000000), "6:13 AM");
	}

	@Test
	public void fahrenheitToCelsiusTest() throws OpenWeatherRestException {
		assertEquals(ConversionHelper.fahrenheitToCelsius(-460), -273);
		assertEquals(ConversionHelper.fahrenheitToCelsius(Integer.MAX_VALUE), 238609275);
	}
}
