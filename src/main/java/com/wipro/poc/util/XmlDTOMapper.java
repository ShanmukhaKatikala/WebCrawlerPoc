package com.wipro.poc.util;

import java.util.ArrayList;
import java.util.List;

import com.wipro.poc.beans.Sitemap;
import com.wipro.poc.dto.ImageDTO;
import com.wipro.poc.dto.SitemapDTO;
import com.wipro.poc.dto.UrlDTO;
import com.wipro.poc.service.DTOMapper;

public class XmlDTOMapper implements DTOMapper {
	
	public SitemapDTO mapper(Sitemap sitemap) {
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

		return sitemapDto;
	}
}
