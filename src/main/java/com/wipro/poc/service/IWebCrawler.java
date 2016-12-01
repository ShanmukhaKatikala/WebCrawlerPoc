package com.wipro.poc.service;

import java.io.IOException;

import com.wipro.poc.beans.Sitemap;

public interface IWebCrawler {
	public Sitemap crawler(String url, boolean crawlForSubDomains, boolean crawlForImages) throws IOException;
}
