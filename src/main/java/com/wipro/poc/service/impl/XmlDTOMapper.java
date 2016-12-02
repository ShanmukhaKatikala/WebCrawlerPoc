package com.wipro.poc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wipro.poc.beans.Sitemap;
import com.wipro.poc.dto.ImageDTO;
import com.wipro.poc.dto.SitemapDTO;
import com.wipro.poc.dto.UrlDTO;
import com.wipro.poc.service.IDTOMapper;

public class XmlDTOMapper implements IDTOMapper {
	private static final Logger LOGGER = Logger.getLogger(XmlDTOMapper.class);
	
	public SitemapDTO mapper(Sitemap sitemap) {
		LOGGER.debug("mapper() | IN");
		SitemapDTO sitemapDto = new SitemapDTO();

		if (null == sitemap) {
			return sitemapDto;
		}

		List<UrlDTO> urlDtos = new ArrayList<UrlDTO>();
		for (String url : sitemap.getUrlSet()) {
			UrlDTO urlDto = new UrlDTO();
			urlDto.setUrlLoc(url);
			urlDtos.add(urlDto);
		}

		List<ImageDTO> imageDtos = new ArrayList<ImageDTO>();
		for (String image : sitemap.getImageSet()) {
			ImageDTO imageDto = new ImageDTO();
			imageDto.setImageLoc(image);
			imageDtos.add(imageDto);
		}

		sitemapDto.setUrl(urlDtos);
		sitemapDto.setImage(imageDtos);

		LOGGER.debug("mapper() | EXIT");
		return sitemapDto;
	}
}
