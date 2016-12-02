package com.wipro.poc.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.wipro.poc.beans.Sitemap;
import com.wipro.poc.exception.ServiceException;

public class JSoupWebCrawllerTest {

	private JSoupWebCrawller webCrawler;

	@Before
	public void setUp() {
		webCrawler = new JSoupWebCrawller();
	}

	@Test
	public void testWebCrawlSuccessfully() throws ServiceException {
		String url = "http://wiprodigital.com";
		Sitemap sitemap = webCrawler.crawler(url, true, true);

		assertThat(sitemap, is(notNullValue()));
		assertThat(sitemap.getImageSet(), is(notNullValue()));
		assertThat(sitemap.getUrlSet(), is(notNullValue()));

		assertThat(sitemap.getUrlSet().contains(url), is(equalTo(true)));
	}

	@Test(expected = ServiceException.class)
	public void throwExceptionWhenUrlNotReachable() throws ServiceException {
		String url = "http://unknowndomainxxxx.com";
		webCrawler.crawler(url, true, true);
	}
}
