package com.automation.utils;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

/**
 * Allure Report Utility
 * Handles Allure report generation and attachments
 */
public class AllureReport {
    private static final Logger logger = LogManager.getLogger(AllureReport.class);

    /**
     * Display startup banner
     */
    public static void startupBanner() {
        System.out.println("\n" +
                "╔══════════════════════════════════════════════════════════════╗\n" +
                "║                                                              ║\n" +
                "║           Nomad TMS Automation Test Framework                ║\n" +
                "║                                                              ║\n" +
                "║              Starting Test Execution...                      ║\n" +
                "║                                                              ║\n" +
                "╚══════════════════════════════════════════════════════════════╝\n");
        logger.info("Test execution started");
    }

    /**
     * Initialize execution folder for Allure results
     */
    public static void initializeExecution() {
        logger.info("Initializing Allure execution folder");
        startupBanner();
    }

    /**
     * Add feature and scenario info to Allure report
     * @param featureName the feature name
     * @param scenarioName the scenario name
     */
    public static void addFeatureInfo(String featureName, String scenarioName) {
        try {
            Allure.feature(featureName);
            Allure.story(scenarioName);
            logger.debug("Added feature info: {} - {}", featureName, scenarioName);
        } catch (Exception e) {
            logger.warn("Failed to add feature info to Allure: {}", e.getMessage());
        }
    }

    /**
     * Add screenshot to Allure report
     * @param name screenshot name
     * @param screenshot screenshot bytes
     */
    public static void addScreenshot(String name, byte[] screenshot) {
        try {
            Allure.addAttachment(name, "image/png", 
                    new ByteArrayInputStream(screenshot), "png");
            logger.debug("Screenshot attached to Allure: {}", name);
        } catch (Exception e) {
            logger.warn("Failed to add screenshot to Allure: {}", e.getMessage());
        }
    }

    /**
     * Add text attachment to Allure report
     * @param name attachment name
     * @param content text content
     */
    public static void addTextAttachment(String name, String content) {
        try {
            Allure.addAttachment(name, "text/plain", content);
            logger.debug("Text attachment added to Allure: {}", name);
        } catch (Exception e) {
            logger.warn("Failed to add text attachment to Allure: {}", e.getMessage());
        }
    }

    /**
     * Add API response to Allure report (for streaming/successful responses)
     * @param response the API response
     */
    public static void addStreamingApiResponse(String response) {
        addTextAttachment("API Response", response);
    }

    /**
     * Add API failure response to Allure report
     * @param response the failed API response
     */
    public static void addApiFailureResponse(String response) {
        addTextAttachment("API Failure Response", response);
    }

    /**
     * Add step to Allure report
     * @param stepName the step name
     */
    public static void step(String stepName) {
        Allure.step(stepName);
        logger.debug("Allure step: {}", stepName);
    }

    /**
     * Generate Allure report
     * Attempts to run 'allure generate' command if Allure CLI is available
     */
    public static void generate() {
        try {
            String allureResultsDir = System.getProperty("allure.results.directory", "target/allure-results");
            String allureReportDir = "target/allure-report";

            logger.info("Generating Allure report from: {}", allureResultsDir);

            // Check if Allure CLI is available
            ProcessBuilder processBuilder = new ProcessBuilder("allure", "--version");
            Process versionProcess = processBuilder.start();
            int versionExitCode = versionProcess.waitFor();

            if (versionExitCode != 0) {
                logger.warn("Allure CLI not found. Skipping report generation.");
                logger.info("To generate report manually, run: allure generate {} -o {}", allureResultsDir, allureReportDir);
                return;
            }

            // Generate Allure report
            ProcessBuilder generateBuilder = new ProcessBuilder(
                    "allure", "generate",
                    allureResultsDir,
                    "-o", allureReportDir,
                    "--clean"
            );

            Process generateProcess = generateBuilder.start();

            // Read output
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(generateProcess.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    logger.debug("Allure: {}", line);
                }
            }

            int exitCode = generateProcess.waitFor();

            if (exitCode == 0) {
                logger.info("Allure report generated successfully at: {}", allureReportDir);
                logger.info("To view report, run: allure open {}", allureReportDir);
            } else {
                logger.warn("Allure report generation completed with exit code: {}", exitCode);
            }

        } catch (Exception e) {
            logger.error("Failed to generate Allure report: {}", e.getMessage());
            logger.debug("Exception details: ", e);
        }
    }
}
