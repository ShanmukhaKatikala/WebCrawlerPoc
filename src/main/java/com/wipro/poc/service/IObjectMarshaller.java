package com.wipro.poc.service;

import org.springframework.stereotype.Service;

import com.wipro.poc.dto.SitemapDTO;
import com.wipro.poc.exception.ServiceException;

@Service
public interface IObjectMarshaller {
	public void marshallObject(SitemapDTO sitemapDto) throws ServiceException;
}
