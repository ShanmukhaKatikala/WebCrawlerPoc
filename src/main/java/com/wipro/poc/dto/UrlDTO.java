package com.wipro.poc.dto;

import javax.xml.bind.annotation.XmlElement;

public class UrlDTO {
	private String urlLoc;

	@XmlElement(name = "loc")
	public String getUrlLoc() {
		return urlLoc;
	}

	public void setUrlLoc(String urlLoc) {
		this.urlLoc = urlLoc;
	}

}
