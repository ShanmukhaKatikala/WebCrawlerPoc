package com.wipro.poc.dto;

import javax.xml.bind.annotation.XmlElement;

public class ImageDTO {
	private String imageLoc;

	@XmlElement(name = "loc")
	public String getImageLoc() {
		return imageLoc;
	}

	public void setImageLoc(String imageLoc) {
		this.imageLoc = imageLoc;
	}

}
