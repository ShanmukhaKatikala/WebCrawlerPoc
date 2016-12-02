package com.wipro.poc.service;

import com.wipro.poc.beans.Sitemap;
import com.wipro.poc.exception.ServiceException;

public interface IWebCrawler {
	public Sitemap crawler(String url, boolean crawlForSubDomains, boolean crawlForImages) throws ServiceException;
}
