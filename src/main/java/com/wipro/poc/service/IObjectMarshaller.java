package com.wipro.poc.service;

import com.wipro.poc.dto.SitemapDTO;
import com.wipro.poc.exception.ServiceException;

public interface IObjectMarshaller {
	public void marshallObject(SitemapDTO sitemapDto) throws ServiceException;
}
