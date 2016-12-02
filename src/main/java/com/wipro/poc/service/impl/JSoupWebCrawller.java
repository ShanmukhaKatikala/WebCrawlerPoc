package com.wipro.poc.service.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import com.wipro.poc.beans.Sitemap;
import com.wipro.poc.constants.AppConstants;
import com.wipro.poc.exception.ServiceException;
import com.wipro.poc.service.IWebCrawler;

public class JSoupWebCrawller implements IWebCrawler {
	private static final Logger LOGGER = Logger.getLogger(JSoupWebCrawller.class);

	@Value("${proxy.required}")
	private String proxyRequired;

	@Value("${proxy.host.name}")
	private String proxyHostName;

	@Value("${proxy.host.port}")
	private String proxyHostPort;

	@Value("${jsoup.connect.timeout}")
	private String jSoupTimeout;

	@Value("${domains.ignore}")
	private String ignoreDomains;

	@Value("${domains.mandatory}")
	private String mandatoryDomains;

	@Override
	public Sitemap crawler(String url, boolean crawlForSubDomains, boolean crawlForImages) throws ServiceException {
		LOGGER.debug("crawler() | IN");
		// Complete list of crawled URLs and Images
		Sitemap crawledSitemap = new Sitemap();

		// Crawl user entered url
		Sitemap parentResultSitemap = crawlSubDomain(url, crawlForImages);
		crawledSitemap.getUrlSet().add(url);

		// Crawl for child urls recursively
		if (crawlForSubDomains && null != parentResultSitemap) {
			while (true) {
				Sitemap resultSitemap = recursiveCrawl(parentResultSitemap, crawledSitemap, crawlForImages);
				if (resultSitemap.isEmptySitemap()) {
					break;
				}
				parentResultSitemap = resultSitemap;
			}
		}
		LOGGER.debug("crawler() | OUT: crawledSitemap: " + crawledSitemap.getUrlSet());
		return crawledSitemap;
	}

	private Sitemap recursiveCrawl(Sitemap subdomain, Sitemap crawledSitemap, boolean crawlForImages)
			throws ServiceException {
		Sitemap tempSitemap = new Sitemap();

		// Iterate through each subdomain URL and have the crawled set of URLs
		for (String subdomainUrl : subdomain.getUrlSet()) {
			String tempSubDomainUrl = trimLeadingChar(subdomainUrl);

			// Skip configured domains and already crawled urls
			if (skipSubDomainUrl(subdomainUrl) || crawledSitemap.getUrlSet().contains(subdomainUrl)
					|| crawledSitemap.getUrlSet().contains(tempSubDomainUrl)) {
				continue;
			}

			// Crawl subdomain url and maintain temporary sitemap for each url
			// crawled with child urls & images
			Sitemap subdomainSiteMap = crawlSubDomain(subdomainUrl, crawlForImages);
			if (null != subdomainSiteMap) {
				tempSitemap.getUrlSet().addAll(subdomainSiteMap.getUrlSet());
				tempSitemap.getImageSet().addAll(subdomainSiteMap.getImageSet());
			}

			crawledSitemap.getUrlSet().add(subdomainUrl);
		}

		// Crawled url and images history
		crawledSitemap.getImageSet().addAll(subdomain.getImageSet());
		return tempSitemap;
	}

	private Sitemap crawlSubDomain(String url, boolean crawlForImages) throws ServiceException {
		LOGGER.debug("crawlSubDomain() | IN; URL=" + url);
		int statusCode = AppConstants.CONNECT_SUCCESS;

		// URL connection to website using Jsoup library with proxy and without
		// proxy
		Document document = null;
		try {
			if (getProxyRequired()) {
				document = Jsoup.connect(url).proxy(proxyHostName, getProxyHostPort()).timeout(getConnectTimeout())
						.get();
			} else {
				document = Jsoup.connect(url).timeout(getConnectTimeout()).get();
			}
		} catch (HttpStatusException e) {
			statusCode = e.getStatusCode();
			LOGGER.error("crawlSubDomain(): Crawl URL failed with HttpStatusException and skipping...;", e);
		} catch (IOException e) {
			statusCode = AppConstants.CONNECT_FAILURE;
			LOGGER.error("crawlSubDomain(): Crawl URL failed;", e);
			throw new ServiceException("crawlSubDomain(): Crawl URL failed;" + e.getMessage());
		}

		Sitemap sitemap = null;

		// Prepare sitemap only for SUCCESS status
		if (AppConstants.CONNECT_SUCCESS == statusCode) {
			Elements urls = document.select(AppConstants.URL_SELECTOR_REGEX);
			Set<String> domainUrls = getDomainElements(urls, AppConstants.URL_SELECTOR_ATTRIBUTE);

			Set<String> domainImages = null;
			if (crawlForImages) {
				Elements images = document.select(AppConstants.IMG_SELECTOR_REGEX);
				domainImages = getDomainElements(images, AppConstants.IMG_SELECTOR_ATTRIBUTE);
			}

			if (!CollectionUtils.isEmpty(domainImages) || !CollectionUtils.isEmpty(domainUrls)) {
				sitemap = new Sitemap();
				sitemap.setUrlSet(domainUrls);
				sitemap.setImageSet(domainImages);
			}
		}

		return sitemap;
	}

	private Set<String> getDomainElements(Elements elements, String searchElement) {
		Set<String> domainElements = new HashSet<String>();
		for (Element element : elements) {
			domainElements.add(element.attr(searchElement));
		}

		return domainElements;
	}

	private boolean skipSubDomainUrl(String subdomainUrl) {
		for (String ignoreDomain : ignoreDomainList()) {
			if (subdomainUrl.indexOf(ignoreDomain) >= 0) {
				return true;
			}
		}

		if (subdomainUrl.indexOf(mandatoryDomains) < 0) {
			return true;
		}

		return false;
	}

	private List<String> ignoreDomainList() {
		return Arrays.asList(ignoreDomains.split(AppConstants.COMMA_SEPARATOR));
	}

	private boolean getProxyRequired() {
		return Boolean.valueOf(this.proxyRequired);
	}

	private int getProxyHostPort() {
		try {
			return Integer.parseInt(this.proxyHostPort);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	private int getConnectTimeout() {
		try {
			return Integer.parseInt(this.jSoupTimeout);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	private String trimLeadingChar(String url) {
		if (url.endsWith("/")) {
			return url.substring(0, url.lastIndexOf("/"));
		}

		return url;
	}
}
