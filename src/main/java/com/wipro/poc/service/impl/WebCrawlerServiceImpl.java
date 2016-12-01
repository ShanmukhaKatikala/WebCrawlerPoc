package com.wipro.poc.service.impl;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wipro.poc.beans.Sitemap;
import com.wipro.poc.dto.SitemapDTO;
import com.wipro.poc.exception.ServiceException;
import com.wipro.poc.service.DTOMapper;
import com.wipro.poc.service.IWebCrawler;
import com.wipro.poc.service.WebCrawlerService;

public class WebCrawlerServiceImpl implements WebCrawlerService {

	@Autowired
	private IWebCrawler webCrawler;

	@Autowired
	private DTOMapper dtoMapper;

	@Override
	public Sitemap crawlWebSite(String url, boolean crawlForSubDomains, boolean crawlForImages)
			throws ServiceException {
		if (StringUtils.isBlank(url)) {
			return new Sitemap();
		}

		Sitemap sitemap = null;
		try {
			sitemap = webCrawler.crawler(url, crawlForSubDomains, crawlForImages);
		} catch (IOException ioe) {
			throw new ServiceException(ioe.getMessage());
		}

		SitemapDTO sitemapDTO = dtoMapper.mapper(sitemap);
		marshalDTO(sitemapDTO);

		return sitemap;
	}

	private void marshalDTO(SitemapDTO sitemapDto) throws ServiceException {
		BufferedOutputStream bos = null;
		try {
			JAXBContext contextObj = JAXBContext.newInstance(SitemapDTO.class);
			Marshaller marshallerObj = contextObj.createMarshaller();
			marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			bos = new BufferedOutputStream(new FileOutputStream("WebCrawlerResult.xml"));

			marshallerObj.marshal(sitemapDto, bos);
		} catch (IOException | JAXBException e) {
			e.printStackTrace();
			throw new ServiceException(e.getLocalizedMessage());
		} finally {
			if (null != bos) {
				try {
					bos.close();
				} catch (IOException e) {
				}
			}
		}

	}
}
