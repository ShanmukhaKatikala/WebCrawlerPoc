package com.wipro.poc.service;

import com.wipro.poc.dto.SitemapDTO;
import com.wipro.poc.exception.ServiceException;

public interface IWebCrawlerService {
	public SitemapDTO crawlWebSite(String url, boolean crawlForSubDomains, boolean crawlForImages) throws ServiceException;
}
