package com.wipro.poc.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.wipro.poc.dto.SitemapDTO;
import com.wipro.poc.exception.ServiceException;

public class Object2XmlMarshallerTest {

	private Object2XmlMarshaller marshaller;
	private String testOutfilePath = "test_outputfile.xml";

	@Mock
	private SitemapDTO sitemapDto;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		marshaller = new Object2XmlMarshaller();
		marshaller.setOutFilePath(testOutfilePath);
	}

	@Test
	public void testObjectToXmlMarshalling() throws ServiceException {
		marshaller.marshallObject(sitemapDto);

		File testFile = new File(testOutfilePath);
		assertThat(testFile, is(notNullValue()));
		assertThat(testFile.exists(), is(equalTo(true)));
		assertThat(testFile.isFile(), is(equalTo(true)));
	}

	@Test(expected = ServiceException.class)
	public void throwExceptionIfOutfileNotSupplied() throws ServiceException {
		marshaller.setOutFilePath(null);
		marshaller.marshallObject(sitemapDto);
	}
}
