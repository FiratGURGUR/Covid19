package com.example.covid19.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseAllData {

	String name;
	String code;
	double lan;
	double lon;

	public ResponseAllData(String name, String code, double lan, double lon) {
		this.name = name;
		this.code = code;
		this.lan = lan;
		this.lon = lon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getLan() {
		return lan;
	}

	public void setLan(double lan) {
		this.lan = lan;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
}