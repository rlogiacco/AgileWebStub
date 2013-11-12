package org.agileware.webstubs;

import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:", tags = {}, glue = "org.agileware.webstubs.stepdefs", format = "html:target/cucumber", monochrome = true)
public class JUnitWrapper {

}
