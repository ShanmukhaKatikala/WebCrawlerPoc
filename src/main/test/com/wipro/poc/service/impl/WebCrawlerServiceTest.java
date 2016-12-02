package com.wipro.poc.service.impl;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wipro.poc.beans.Sitemap;
import com.wipro.poc.exception.ServiceException;
import com.wipro.poc.service.IDTOMapper;
import com.wipro.poc.service.IWebCrawler;

public class WebCrawlerServiceTest {

	@Mock
	private IWebCrawler mockWebCrawler;

	@Mock
	private IDTOMapper mockDtoMapper;

	@InjectMocks
	private WebCrawlerServiceImpl webCrawlerServiceImpl = new WebCrawlerServiceImpl();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCrawlWebSite() throws ServiceException {
		String url = "http://wiprodigital.com";
		Sitemap sitemap = initSitemap(url);
		when(mockWebCrawler.crawler(isA(String.class), isA(Boolean.class), isA(Boolean.class))).thenReturn(sitemap);

		webCrawlerServiceImpl.crawlWebSite(url, true, true);

		verify(mockWebCrawler, times(1)).crawler(isA(String.class), isA(Boolean.class), isA(Boolean.class));
		verify(mockDtoMapper, times(1)).mapper(isA(Sitemap.class));
	}


	private Sitemap initSitemap(String url) {
		Sitemap sitemap = new Sitemap();
		Set<String> urlSet = new HashSet<String>();
		urlSet.add(url);
		Set<String> imageSet = new HashSet<String>();
		imageSet.add(url + "/wiprologo.gif");

		sitemap.setUrlSet(urlSet);
		sitemap.setImageSet(imageSet);
		return sitemap;
	}
}
