package com.wipro.poc.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wipro.poc.beans.Sitemap;
import com.wipro.poc.dto.SitemapDTO;
import com.wipro.poc.exception.ServiceException;
import com.wipro.poc.service.IDTOMapper;
import com.wipro.poc.service.IWebCrawler;
import com.wipro.poc.service.IWebCrawlerService;

@Service
public class WebCrawlerServiceImpl implements IWebCrawlerService {
	private static final Logger LOGGER = Logger.getLogger(WebCrawlerServiceImpl.class);

	@Autowired
	private IWebCrawler webCrawler;

	@Autowired
	private IDTOMapper dtoMapper;

	@Override
	public SitemapDTO crawlWebSite(String url, boolean crawlForSubDomains, boolean crawlForImages)
			throws ServiceException {
		LOGGER.debug("crawlWebSite() | IN");

		Sitemap sitemap = null;
		try {
			sitemap = webCrawler.crawler(url, crawlForSubDomains, crawlForImages);
		} catch (ServiceException ioe) {
			throw ioe;
		}

		LOGGER.debug("crawlWebSite() | EXIT");
		return dtoMapper.mapper(sitemap);
	}
}
