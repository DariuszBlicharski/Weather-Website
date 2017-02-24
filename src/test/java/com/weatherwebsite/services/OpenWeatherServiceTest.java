package com.weatherwebsite.services;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import com.weatherwebsite.models.Weather;
import com.weatherwebsite.services.exceptions.OpenWeatherRestException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class OpenWeatherServiceTest {

	private final String CITY_NAME = "London";

	private final String LONDON_MOCK_RESPONSE =
			"{\"coord\":{\"lon\":-0.13,\"lat\":51.51},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"" +
					"broken clouds\",\"icon\":\"04n\"}],\"base\":\"stations\",\"main\":{\"temp\":42.98,\"pressure\":1004,\"humidity\"" +
					":70,\"temp_min\":41,\"temp_max\":44.6},\"visibility\":10000,\"wind\":{\"speed\":20.8,\"deg\":290},\"clouds\":{\"" +
					"all\":68},\"dt\":1487883000,\"sys\":{\"type\":1,\"id\":5091,\"message\":0.0535,\"country\":\"GB\",\"sunrise\"" +
					":1487832976,\"sunset\":1487871119},\"id\":2643743,\"name\":\"London\",\"cod\":200}";

	private final Weather expectedLondonWeather =
			new Weather(LocalDate.now(), CITY_NAME, "broken clouds", 42, 5, "6:56 AM", "5:31 PM");

	@InjectMocks
	private OpenWeatherService tested;

	@Mock
	private RestTemplate restTemplate;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void getWeatherTest_London() throws OpenWeatherRestException {
		when(restTemplate.getForObject(any(String.class), eq(String.class), eq(CITY_NAME)))
				.thenReturn(LONDON_MOCK_RESPONSE);
		assertEquals(tested.getWeather(CITY_NAME), expectedLondonWeather);
	}

	@Test
	public void getWeatherTest_NullJsonRespone() throws OpenWeatherRestException {
		when(restTemplate.getForObject(any(String.class), eq(String.class), any(String.class)))
				.thenReturn(null);
		try {
			tested.getWeather(CITY_NAME);
		} catch (OpenWeatherRestException e) {
			assertEquals("JSON data received from OpenWeatherMap.org is null", e.getMessage());
		}
	}

	@Test
	public void getWeatherTest_IncorrectJsonRespone() throws OpenWeatherRestException {
		when(restTemplate.getForObject(any(String.class), eq(String.class), any(String.class)))
				.thenReturn("[]");
		try {
			tested.getWeather(CITY_NAME);
		} catch (OpenWeatherRestException e) {
			assertEquals("JSON data received from OpenWeatherMap.org is incorrect: A JSONObject text must begin with" +
					" '{' at 1 [character 2 line 1]", e.getMessage());
		}
	}
}
