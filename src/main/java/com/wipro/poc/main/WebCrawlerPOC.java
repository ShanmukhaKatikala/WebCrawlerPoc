package com.wipro.poc.main;

import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wipro.poc.exception.ServiceException;
import com.wipro.poc.service.WebCrawlerService;

public class WebCrawlerPOC {

	public static void main(String[] args) {
		WebCrawlerService webCrawlerService = null;
		try (ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml")) {
			webCrawlerService = appContext.getBean("webCrawlerSerive", WebCrawlerService.class);
		}

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter URL to crawl: ");
		String url = scanner.nextLine();

		try {
			webCrawlerService.crawlWebSite(url, true, true);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}
