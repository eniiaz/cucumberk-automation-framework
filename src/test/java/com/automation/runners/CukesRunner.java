package com.automation.runners;

import com.automation.utils.AllureReport;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "html:target/cucumber-reports.html",
                "json:target/cucumber-reports/cucumber.json",
                "rerun:target/rerun.txt"
        },
        features = "src/test/resources/features",
        glue = "com.automation.stepdefinitions",
        tags = "@epic-Security",
        dryRun = false
)
public class CukesRunner {

    @BeforeClass
    public static void beforeAll() {
        AllureReport.startupBanner();
    }

    @AfterClass
    public static void afterAll() {
        try {
            AllureReport.generate(); // creates target/allure-report if CLI is available
        } catch (Exception e) {
            System.out.println("Allure report generation failed");
        }
    }
}
