package org.agileware.webstubs;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;

public class FileReader {

	public static String read(Resource resource) throws Exception {
		InputStream is = null;
		try {
			is = resource.getInputStream();
			return IOUtils.toString(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
}
