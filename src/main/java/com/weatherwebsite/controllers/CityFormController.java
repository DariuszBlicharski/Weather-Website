package com.weatherwebsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weatherwebsite.models.Weather;
import com.weatherwebsite.services.OpenWeatherService;
import com.weatherwebsite.services.exceptions.OpenWeatherRestException;

@Controller
public class CityFormController {

	@Autowired
	OpenWeatherService openWeatherService;

	@GetMapping("/")
	public String getCityForm() {
		return "cityNameForm";
	}

	@PostMapping("/results")
	public String cityFormSubmit(@RequestParam String cityName, Model model) {
		try {
			Weather weather = openWeatherService.getWeather(cityName);
			model.addAttribute("weather", weather);
			return "results";
		} catch (OpenWeatherRestException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "openWeatherError";
		}
	}

}