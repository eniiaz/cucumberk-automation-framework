package com.automation.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Failed Test Runner
 * Re-runs failed scenarios from the previous test run
 * Usage: mvn test -Dtest=FailedTestRunner
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "html:target/cucumber-reports/rerun.html",
                "json:target/cucumber-reports/rerun.json"
        },
        features = "@target/rerun.txt",
        glue = "com.automation.stepdefinitions"
)
public class FailedTestRunner {
}
