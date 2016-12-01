package com.wipro.poc.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "sitemap")
@XmlAccessorType(XmlAccessType.FIELD)
public class SitemapDTO {

	private List<UrlDTO> url = new ArrayList<UrlDTO>();
	private List<ImageDTO> image = new ArrayList<ImageDTO>();

	public List<UrlDTO> getUrl() {
		return url;
	}

	public void setUrl(List<UrlDTO> url) {
		this.url = url;
	}

	public List<ImageDTO> getImage() {
		return image;
	}

	public void setImage(List<ImageDTO> image) {
		this.image = image;
	}
}
