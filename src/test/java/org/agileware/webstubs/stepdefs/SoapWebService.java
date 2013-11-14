package org.agileware.webstubs.stepdefs;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.en.Given;

@ContextConfiguration(locations = "classpath:cucumber.xml")
public class SoapWebService {
	
	@Autowired
	private WebDriver browser;
	
	@Given("test")
	public void test() {
		System.out.println(browser);
	}

}
