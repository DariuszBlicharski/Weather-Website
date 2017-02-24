package com.weatherwebsite.models;

import java.time.LocalDate;
import java.util.Objects;

public class Weather {

	public Weather(LocalDate currentDate, String cityName, String description,
				   int fahrenheit, int celsius, String sunriseTime, String sunsetTime) {
		this.currentDate = currentDate;
		this.cityName = cityName;
		this.description = description;
		this.fahrenheit = fahrenheit;
		this.celsius = celsius;
		this.sunriseTime = sunriseTime;
		this.sunsetTime = sunsetTime;
	}

	private LocalDate currentDate;

	private String cityName;

	private String description;

	private int fahrenheit;

	private int celsius;

	private String sunriseTime;

	private String sunsetTime;

	public LocalDate getCurrentDate() {
		return currentDate;
	}

	public String getCityName() {
		return cityName;
	}

	public String getDescription() {
		return description;
	}

	public int getFahrenheit() {
		return fahrenheit;
	}

	public int getCelsius() {
		return celsius;
	}

	public String getSunriseTime() {
		return sunriseTime;
	}

	public String getSunsetTime() {
		return sunsetTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(currentDate, cityName, description, fahrenheit, celsius, sunriseTime, sunsetTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Weather) {
			final Weather other = (Weather) obj;
			return Objects.equals(currentDate, other.currentDate) &&
					Objects.equals(cityName, other.cityName) &&
					Objects.equals(description, other.description) &&
					fahrenheit == other.fahrenheit &&
					celsius == other.celsius &&
					Objects.equals(sunriseTime, other.sunriseTime) &&
					Objects.equals(sunsetTime, other.sunsetTime);
		} else {
			return false;
		}
	}
}
