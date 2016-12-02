package com.wipro.poc.service;

import org.springframework.stereotype.Service;

import com.wipro.poc.beans.Sitemap;
import com.wipro.poc.exception.ServiceException;

@Service
public interface IWebCrawler {
	public Sitemap crawler(String url, boolean crawlForSubDomains, boolean crawlForImages) throws ServiceException;
}
