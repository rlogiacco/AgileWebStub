package org.agileware.webstubs;

import java.util.List;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableMongoRepositories
public class Dispatcher {

	@Autowired
	private ServiceRepository serviceRepository;

	@RequestMapping
	public @ResponseBody String dispatch(HttpServletRequest request) throws Exception {
		String path = request.getRequestURI();
		if (path.indexOf(request.getContextPath()) > -1) {
			path = path.substring(request.getContextPath().length());
		}
		List<Service> services = serviceRepository.findAll();
		for (Service service : services) {
			if (service.getUrl().matches(path)) {
				System.out.println("MATCH: " + service.getUrl());
				SoapService soap = new SoapService();
				soap.setBody(request.getInputStream());
				System.out.println(soap.dispatch());
			}
		}
		System.out.println("PATH " + request.getServletPath());
		System.out.println("QUERY " + request.getQueryString());
		System.out.println("URL " + request.getRequestURL());

		System.out.println("CONTEXT " + request.getContextPath());
		System.out.println("URI " + request.getRequestURI());

		System.out.println(serviceRepository.count());

		return "RESPONSE: " + request;
	}
}
