package com.weatherwebsite.services;

import java.time.LocalDate;
import java.util.StringJoiner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.weatherwebsite.models.Weather;
import com.weatherwebsite.services.exceptions.OpenWeatherRestException;

import static com.weatherwebsite.utils.ConversionHelper.fahrenheitToCelsius;
import static com.weatherwebsite.utils.ConversionHelper.unixToRealTime;

@Service
public class OpenWeatherService {

	private static final String API_KEY = "0e2059cc2b1eae3156c209f330f3685d";

	private static final String OPEN_WEATHER_GET_REQUEST =
			"http://api.openweathermap.org/data/2.5/weather?q={cityName}&units=imperial&APPID=" + API_KEY;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * This method makes call to OpenWeatherMap.org api for the specified city name. Data is received
	 * in JSON format. Weather object is constructed from received json.
	 *
	 * @param cityName name of the city.
	 * @return weather object created from json response.
	 * @throws OpenWeatherRestException if call to rest api fails.
	 */
	public Weather getWeather(String cityName) throws OpenWeatherRestException {
		try {
			String weather = restTemplate.getForObject(OPEN_WEATHER_GET_REQUEST, String.class, cityName);
			try {
				if (weather != null) {
					JSONObject jsonWeather = new JSONObject(weather);
					return createWeatherFromJson(jsonWeather, cityName);
				} else {
					throw new OpenWeatherRestException("JSON data received from OpenWeatherMap.org is null");
				}
			} catch (JSONException e) {
				throw new OpenWeatherRestException("JSON data received from OpenWeatherMap.org is incorrect: " + e.getMessage());
			}
		} catch (RestClientException e) {
			throw new OpenWeatherRestException("Call to OpenWeatherMap.org failed with the following response: " + e.getMessage());
		}
	}

	/**
	 * This method returns Weather object created from input json and city name.
	 *
	 * @param jsonWeather
	 * @param cityName
	 * @return weather object
	 */
	private Weather createWeatherFromJson(JSONObject jsonWeather, String cityName) throws JSONException {
		String description = extractDescription(jsonWeather.getJSONArray("weather"));

		int fahrenheit = jsonWeather.getJSONObject("main").getInt("temp");
		int celsius = fahrenheitToCelsius(fahrenheit);

		String sunriseTime = unixToRealTime(jsonWeather.getJSONObject("sys").getLong("sunrise"));
		String sunsetTime = unixToRealTime(jsonWeather.getJSONObject("sys").getLong("sunset"));

		return new Weather(LocalDate.now(), cityName, description, fahrenheit, celsius, sunriseTime, sunsetTime);
	}

	/**
	 * This method extracts weather descriptions from json array and returns single string object.
	 * Each description is separated with comma.
	 *
	 * @param descriptionArray
	 * @return string object created from json array.
	 */
	private String extractDescription(JSONArray descriptionArray) {
		StringJoiner joiner = new StringJoiner(", ");
		for (int i = 0; i < descriptionArray.length(); i++) {
			joiner.add(descriptionArray.getJSONObject(i).getString("description"));
		}
		return joiner.toString();
	}
}
