package org.agileware.webstubs;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:", tags = {}, glue = "org.agileware.webstubs.stepdefs", format = "html:target/cucumber", monochrome = true)
public class JUnitWrapper {

}
