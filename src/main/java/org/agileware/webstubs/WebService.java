package org.agileware.webstubs;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/service")
class WebService {

	@Autowired
	ServiceRepository serviceRepository;
	
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"})
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody Service create(@RequestBody Service service) {
		serviceRepository.save(service);
		return service;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE, consumes = {"application/json"})
	public ResponseEntity<String> delete(@PathVariable String id) {
		if (serviceRepository.exists(id)) {
			serviceRepository.delete(id);
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = {"application/json"})
	public @ResponseBody Service update(@PathVariable String id, @RequestBody Service service) {
		Service exisisting = serviceRepository.findOne(id);
		BeanUtils.copyProperties(service, exisisting, new String[] {"id"});
		serviceRepository.save(exisisting);
		return exisisting;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public @ResponseBody Service find(@PathVariable String id) {
		Service service = serviceRepository.findOne(id);
		return service;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Service> list() {
		List<Service> services = serviceRepository.findAll();
		return services;
	}

	@RequestMapping(value = "/test2")
	public @ResponseBody String verify2() {
		return " RECEIVED! ";
	}
}
