package com.example.covid19.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Recovered{

	@JsonProperty("detail")
	private String detail;

	@JsonProperty("value")
	private int value;

	public String getDetail(){
		return detail;
	}

	public int getValue(){
		return value;
	}
}