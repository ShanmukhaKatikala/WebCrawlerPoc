package com.wipro.poc.main;

import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wipro.poc.dto.SitemapDTO;
import com.wipro.poc.exception.ServiceException;
import com.wipro.poc.service.IObjectMarshaller;
import com.wipro.poc.service.IWebCrawlerService;

public class WebCrawlerPOC {
	private static final Logger LOGGER = Logger.getLogger(WebCrawlerPOC.class);

	public static void main(String[] args) {
		LOGGER.info("*** Web Crawler :: START ***");
		IWebCrawlerService webCrawlerService = null;
		IObjectMarshaller objectMarshaller = null;
		try (ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml")) {
			webCrawlerService = appContext.getBean("webCrawlerSerive", IWebCrawlerService.class);
			objectMarshaller = appContext.getBean("objectMarshaller", IObjectMarshaller.class);
		}

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter URL to crawl: ");
		String url = scanner.nextLine();

		LOGGER.info("Starting web crawl process for URL:" + url);
		SitemapDTO sitemapDto = null;
		try {
			sitemapDto = webCrawlerService.crawlWebSite(url, true, true);
		} catch (ServiceException e) {
			LOGGER.error("Web Crawler failed;", e);
			System.out.println("\nWebCrawler FAILED; Check logs for error");
		}

		try {
			if (null != sitemapDto) {
				objectMarshaller.marshallObject(sitemapDto);
				System.out.println("\nGenerated output file : SUCCESS");
			}
		} catch (ServiceException e) {
			LOGGER.error("Marshalling Object failed;", e);
			System.out.println("\nGenerated output file : FAILED; Check logs for error");
		}
		LOGGER.info("*** Web Crawler :: END ***");
	}
}
