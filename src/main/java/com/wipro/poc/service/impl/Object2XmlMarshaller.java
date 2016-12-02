package com.wipro.poc.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wipro.poc.dto.SitemapDTO;
import com.wipro.poc.exception.ServiceException;
import com.wipro.poc.service.IObjectMarshaller;
import com.wipro.poc.util.ConvertDto2File;

@Service
public class Object2XmlMarshaller implements IObjectMarshaller {
	private static final Logger LOGGER = Logger.getLogger(Object2XmlMarshaller.class);

	@Value("${crawldata.outfilepath}")
	private String outFilePath;

	@Override
	public void marshallObject(SitemapDTO sitemapDto) throws ServiceException {
		LOGGER.debug("marshallObject() | IN");
		ConvertDto2File.marshalDTO(sitemapDto, outFilePath);
		LOGGER.debug("marshallObject() | EXIT");
	}

	public void setOutFilePath(String outFilePath) {
		this.outFilePath = outFilePath;
	}
}
