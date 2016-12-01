package com.wipro.poc.util;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import com.wipro.poc.beans.Sitemap;
import com.wipro.poc.constants.AppConstants;
import com.wipro.poc.service.IWebCrawler;

public class JSoupUtil implements IWebCrawler {

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
	public Sitemap crawler(String url, boolean crawlForSubDomains, boolean crawlForImages) throws IOException {
		Sitemap crawledSitemap = new Sitemap();

		Sitemap parentResultSitemap = crawlSubDomain(url, crawlForImages);
		crawledSitemap.getUrlSet().add(url);

		if (crawlForSubDomains && null != parentResultSitemap) {
			while (true) {
				Sitemap resultSitemap = recursiveCrawl(parentResultSitemap, crawledSitemap, crawlForImages);
				if (resultSitemap.isEmptySitemap()) {
					break;
				}
				parentResultSitemap = resultSitemap;

				System.out.println("crawledSitemap: " + crawledSitemap.getUrlSet());
			}
		}

		return crawledSitemap;
	}

	private Sitemap recursiveCrawl(Sitemap subdomain, Sitemap crawledSitemap, boolean crawlForImages)
			throws IOException {
		Sitemap tempSitemap = new Sitemap();
		for (String subdomainUrl : subdomain.getUrlSet()) {
			String tempSubDomainUrl = trimLeadingChar(subdomainUrl);
			if (skipSubDomainUrl(subdomainUrl) || crawledSitemap.getUrlSet().contains(subdomainUrl)
					|| crawledSitemap.getUrlSet().contains(tempSubDomainUrl)) {
				continue;
			}
			Sitemap subdomainSiteMap = crawlSubDomain(subdomainUrl, crawlForImages);
			if (null != subdomainSiteMap) {
				tempSitemap.getUrlSet().addAll(subdomainSiteMap.getUrlSet());
				tempSitemap.getImageSet().addAll(subdomainSiteMap.getImageSet());
			}

			crawledSitemap.getUrlSet().add(subdomainUrl);
		}

		crawledSitemap.getImageSet().addAll(subdomain.getImageSet());
		return tempSitemap;
	}

	private Sitemap crawlSubDomain(String url, boolean crawlForImages) throws IOException {
		System.out.println("Crawling: " + url);
		int statusCode = AppConstants.CONNECT_SUCCESS;
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
		} catch (IOException e) {
			statusCode = AppConstants.CONNECT_FAILURE;
		}

		Sitemap sitemap = null;
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

	public static void main(String[] args) {
		JSoupUtil jSoupUtil = new JSoupUtil();
		/*
		 * try { jSoupUtil.crawler("http://www.wiprodigital.com", true, true); }
		 * catch (IOException e) { e.printStackTrace(); }
		 */
		System.out.println(jSoupUtil.trimLeadingChar("http://wipro.com/sfsf/sfsdf/sfd111/"));
	}

}
