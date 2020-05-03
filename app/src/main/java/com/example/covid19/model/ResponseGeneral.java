package com.example.covid19.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseGeneral{

	@JsonProperty("dailyTimeSeries")
	private DailyTimeSeries dailyTimeSeries;

	@JsonProperty("image")
	private String image;

	@JsonProperty("recovered")
	private Recovered recovered;

	@JsonProperty("dailySummary")
	private String dailySummary;

	@JsonProperty("lastUpdate")
	private String lastUpdate;

	@JsonProperty("countryDetail")
	private CountryDetail countryDetail;

	@JsonProperty("source")
	private String source;

	@JsonProperty("countries")
	private String countries;

	@JsonProperty("confirmed")
	private Confirmed confirmed;

	@JsonProperty("deaths")
	private Deaths deaths;

	public DailyTimeSeries getDailyTimeSeries(){
		return dailyTimeSeries;
	}

	public String getImage(){
		return image;
	}

	public Recovered getRecovered(){
		return recovered;
	}

	public String getDailySummary(){
		return dailySummary;
	}

	public String getLastUpdate(){
		return lastUpdate;
	}

	public CountryDetail getCountryDetail(){
		return countryDetail;
	}

	public String getSource(){
		return source;
	}

	public String getCountries(){
		return countries;
	}

	public Confirmed getConfirmed(){
		return confirmed;
	}

	public Deaths getDeaths(){
		return deaths;
	}
}