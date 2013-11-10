package org.agileware.webstubs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@org.springframework.data.mongodb.core.mapping.Document(collection="services")
public class SoapService extends Service {

	private transient XPathFactory xPathFactory = XPathFactory.newInstance();
	private transient DocumentBuilderFactory documentFactory = DocumentBuilderFactory
			.newInstance();
	
	private Document document;
	
	public void setBody(InputStream body) throws Exception {
		DocumentBuilder builder = documentFactory.newDocumentBuilder();
		document = builder.parse(body);
	}

	public String dispatch() throws Exception {
		XPath xpath = xPathFactory.newXPath();
		XPathExpression expr = xpath
				.compile("/howto/topic[@name='PowerBuilder']/url/text()");
		Object obj = expr.evaluate(document);
		return (String) obj;
	}
	
	public String operation() throws Exception {
		XPath xpath = xPathFactory.newXPath();
		XPathExpression expr = xpath
				.compile("local-name(/Envelope/Body/*)");
		Object obj = expr.evaluate(document);
		return (String) obj;
	}

	public Set<String> listOperations(String url)
			throws FileNotFoundException, SAXException, IOException,
			ParserConfigurationException {
		DocumentBuilder builder = documentFactory.newDocumentBuilder();
		Document document = builder.parse(url);
		NodeList elements = document.getElementsByTagName("operation");
		Set<String> operations = new HashSet<String>();
		for (int i = 0; i < elements.getLength(); i++) {
			operations.add(elements.item(i).getAttributes()
					.getNamedItem("name").getNodeValue());
		}
		return operations;
	}

}
