package com.wipro.poc.service;

import org.springframework.stereotype.Service;

import com.wipro.poc.dto.SitemapDTO;
import com.wipro.poc.exception.ServiceException;

@Service
public interface IWebCrawlerService {
	public SitemapDTO crawlWebSite(String url, boolean crawlForSubDomains, boolean crawlForImages) throws ServiceException;
}
