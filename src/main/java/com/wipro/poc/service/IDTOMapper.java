package com.wipro.poc.service;

import org.springframework.stereotype.Service;

import com.wipro.poc.beans.Sitemap;
import com.wipro.poc.dto.SitemapDTO;

@Service
public interface IDTOMapper {
	public SitemapDTO mapper(Sitemap sitemap);
}
