package com.wipro.poc.service.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wipro.poc.beans.Sitemap;
import com.wipro.poc.dto.SitemapDTO;

public class XmlDTOMapperTest {

	private XmlDTOMapper xmlDTOMapper;

	@Mock
	private Sitemap mockSitemap;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		xmlDTOMapper = new XmlDTOMapper();
	}

	@Test
	public void testXmlDtoMapper() {
		SitemapDTO sitemapDTO = xmlDTOMapper.mapper(mockSitemap);

		assertThat(sitemapDTO, is(notNullValue()));
		assertThat(sitemapDTO, isA(SitemapDTO.class));
	}
}
