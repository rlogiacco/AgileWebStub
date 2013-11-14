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

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.message.GenericMessage;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@org.springframework.data.mongodb.core.mapping.Document(collection="services")
public class SoapService extends Service {

	private transient XPathFactory xPathFactory = XPathFactory.newInstance();
	private transient DocumentBuilderFactory documentFactory = DocumentBuilderFactory
			.newInstance();
	
	private Document document;
	
	public static void main(String... args) throws Exception {
        LoggerFactory.getLogger(SoapService.class).info("test");
        ApplicationContext ctx =
            new ClassPathXmlApplicationContext("spring/services.xml");
        
        // Channel
        DirectChannel channel = ctx.getBean("viaWebService", DirectChannel.class);
        
        MessagingTemplate template = new MessagingTemplate();

        Message<?> reply = template.sendAndReceive(channel, new GenericMessage<String>("72"));
        System.out.println(reply.getPayload());
        
        DirectChannel channel12 = ctx.getBean("viaWebService12", DirectChannel.class);
        
        MessagingTemplate template12 = new MessagingTemplate();

        Message<?> reply12 = template12.sendAndReceive(channel12, new GenericMessage<String>("72"));
        System.out.println(reply12.getPayload());
    }
	
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
