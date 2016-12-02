package com.wipro.poc.beans;

import java.util.HashSet;
import java.util.Set;

import org.springframework.util.CollectionUtils;

public class Sitemap {
	private Set<String> urlSet = new HashSet<String>();
	private Set<String> imageSet = new HashSet<String>();

	public Set<String> getUrlSet() {
		return urlSet;
	}

	public void setUrlSet(Set<String> urlSet) {
		this.urlSet = urlSet;
	}

	public Set<String> getImageSet() {
		return imageSet;
	}

	public void setImageSet(Set<String> imageSet) {
		this.imageSet = imageSet;
	}

	public boolean isEmptySitemap() {
		if (CollectionUtils.isEmpty(this.imageSet) && CollectionUtils.isEmpty(urlSet)) {
			return true;
		}
		return false;
	}
}
