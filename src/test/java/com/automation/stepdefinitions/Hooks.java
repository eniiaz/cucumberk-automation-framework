package com.automation.stepdefinitions;

import com.automation.utils.AllureReport;
import com.automation.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * Cucumber Hooks for setup and teardown operations
 * Takes screenshot on pass/fail and ALWAYS closes browser after each scenario
 */
public class Hooks {
    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @BeforeAll
    public static void setupExecution() {
        AllureReport.startupBanner();
        logger.info("========== Test Execution Started ==========");
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        String featureName = getFeatureName(scenario);
        Allure.epic(getEpicFromTags(scenario));
        Allure.feature(featureName);
        Allure.story(scenario.getName());

        logger.info("🚀 Starting scenario: {}", scenario.getName());
        logger.info("   Feature: {}", featureName);
        logger.info("   Tags: {}", scenario.getSourceTagNames());
    }

    @Before("@ui")
    public void setUpBrowser() {
        logger.info("🌐 Initializing browser for UI test");
        DriverManager.getDriver();
    }

    /**
     * Takes screenshot (pass or fail) and cleans up
     * order=1 runs BEFORE order=0
     */
    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        logger.info("📋 After hook - scenario: {} [{}]", scenario.getName(), scenario.getStatus());

        // Take screenshot for ALL scenarios (pass or fail) if driver exists
        if (DriverManager.hasDriver()) {
            String screenshotName = scenario.isFailed() ? "❌ Failed Screenshot" : "✅ Final State";
            takeScreenshot(scenario, screenshotName);
        }

        String emoji = scenario.isFailed() ? "❌" : "✅";
        logger.info("{} Scenario completed: {} [{}]", emoji, scenario.getName(), scenario.getStatus());
    }

    /**
     * ALWAYS closes browser - runs last (order=0)
     * This guarantees browser closes whether test passes or fails
     */
    @After(order = 0)
    public void closeDriverAlways(Scenario scenario) {
        logger.info("🔒 Closing browser...");
        try {
            DriverManager.closeDriver();
            logger.info("✅ Browser closed successfully");
        } catch (Exception e) {
            logger.error("⚠️ Error closing browser: {}", e.getMessage());
            // Force kill if normal close fails
            forceKillDriver();
        }
    }

    @AfterAll
    public static void teardownExecution() {
        logger.info("========== Test Execution Completed ==========");

        // Final safety net
        if (DriverManager.hasDriver()) {
            logger.warn("⚠️ Driver still open - forcing cleanup");
            DriverManager.closeDriver();
        }

        AllureReport.generate();
    }

    private void forceKillDriver() {
        try {
            WebDriver driver = DriverManager.getExistingDriver();
            if (driver != null) {
                driver.quit();
                logger.info("🔒 Force killed browser");
            }
        } catch (Exception e) {
            logger.error("Force kill failed: {}", e.getMessage());
        }
    }

    private void takeScreenshot(Scenario scenario, String screenshotName) {
        try {
            WebDriver driver = DriverManager.getExistingDriver();
            if (driver instanceof TakesScreenshot) {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", screenshotName);
                Allure.addAttachment(screenshotName, "image/png",
                        new ByteArrayInputStream(screenshot), "png");
                logger.info("📸 Screenshot attached: {}", screenshotName);
            }
        } catch (Exception e) {
            logger.error("❌ Screenshot failed: {}", e.getMessage());
        }
    }

    private String getFeatureName(Scenario scenario) {
        String uri = scenario.getUri().toString();
        String fileName = new File(uri).getName();
        return fileName.replace(".feature", "").replace("_", " ").replace("-", " ");
    }

    private String getEpicFromTags(Scenario scenario) {
        return scenario.getSourceTagNames().stream()
                .filter(tag -> tag.startsWith("@epic-"))
                .map(tag -> tag.replace("@epic-", "").replace("-", " "))
                .findFirst()
                .orElse("General");
    }
}

