package com.example.covid19.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DailyTimeSeries{

	@JsonProperty("pattern")
	private String pattern;

	@JsonProperty("example")
	private String example;

	public String getPattern(){
		return pattern;
	}

	public String getExample(){
		return example;
	}
}