package com.wipro.poc.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

import com.wipro.poc.dto.SitemapDTO;
import com.wipro.poc.exception.ServiceException;

public class ConvertDto2File {
	private static final Logger LOGGER = Logger.getLogger(ConvertDto2File.class);

	public static void marshalDTO(Object marshalObject, String filePath) throws ServiceException {
		LOGGER.debug("marshalDTO() | IN");
		BufferedOutputStream bos = null;
		try {
			JAXBContext contextObj = JAXBContext.newInstance(SitemapDTO.class);
			Marshaller marshallerObj = contextObj.createMarshaller();
			marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			bos = new BufferedOutputStream(new FileOutputStream(filePath));

			marshallerObj.marshal(marshalObject, bos);
		} catch (IOException | JAXBException e) {
			LOGGER.error("marshalDTO() | marshalling object failed;", e);
			throw new ServiceException(e.getMessage());
		} catch(NullPointerException npe) {
			throw new ServiceException(npe.getMessage());
		} finally {
			if (null != bos) {
				try {
					bos.close();
				} catch (IOException e) {
				}
			}
		}
		LOGGER.debug("marshalDTO() | EXIT");
	}
}
