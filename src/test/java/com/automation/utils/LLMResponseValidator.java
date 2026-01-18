package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * LLM Response Validator Utility
 * 
 * Simple validator that ensures AI Agent responses stay within the TMS domain scope.
 * Validates responses against TMS domain keywords and scope instructions.
 */
public class LLMResponseValidator {
    private static final Logger logger = LogManager.getLogger(LLMResponseValidator.class);
    private static Properties validatorProperties;
    private static final String VALIDATORS_FILE = "llm-validators.properties";

    static {
        loadValidatorProperties();
    }

    private static void loadValidatorProperties() {
        validatorProperties = new Properties();
        try (InputStream is = LLMResponseValidator.class.getClassLoader().getResourceAsStream(VALIDATORS_FILE)) {
            if (is != null) {
                validatorProperties.load(is);
                logger.info("LLM Validator properties loaded from: {}", VALIDATORS_FILE);
            }
        } catch (IOException e) {
            logger.error("Error loading LLM Validator properties: {}", e.getMessage());
        }
    }

    /**
     * Validates that the response is within TMS domain scope.
     * A valid response either:
     * - Contains TMS domain keywords (helpful TMS response)
     * - Contains decline keywords (politely refuses non-TMS requests)
     * 
     * @param response the agent's response text
     * @return true if response is within TMS scope
     */
    public static boolean isWithinTMSScope(String response) {
        if (response == null || response.trim().isEmpty()) {
            logger.warn("Empty response received");
            return false;
        }

        String normalizedResponse = response.toLowerCase();
        
        // Check minimum response length
        int minLength = Integer.parseInt(validatorProperties.getProperty("tms.min.response.length", "10"));
        if (response.length() < minLength) {
            logger.warn("Response too short: {} chars (min: {})", response.length(), minLength);
            return false;
        }

        // Get domain and decline keywords
        List<String> domainKeywords = parseKeywords(validatorProperties.getProperty("tms.domain.keywords", ""));
        List<String> declineKeywords = parseKeywords(validatorProperties.getProperty("tms.decline.keywords", ""));

        // Check if response contains domain keywords (helpful TMS response)
        boolean hasDomainKeywords = containsAnyKeyword(normalizedResponse, domainKeywords);
        
        // Check if response contains decline keywords (politely refusing non-TMS)
        boolean hasDeclineKeywords = containsAnyKeyword(normalizedResponse, declineKeywords);

        boolean isValid = hasDomainKeywords || hasDeclineKeywords;
        
        logger.info("TMS Scope Validation - Domain keywords: {}, Decline keywords: {}, Valid: {}", 
            hasDomainKeywords, hasDeclineKeywords, isValid);
        
        return isValid;
    }

    /**
     * Validates response against expected behavior description.
     * 
     * @param response the agent's response text
     * @param expectedBehavior description of expected behavior
     * @return validation result with details
     */
    public static ValidationResult validate(String response, String expectedBehavior) {
        boolean withinScope = isWithinTMSScope(response);
        
        String message;
        if (withinScope) {
            message = String.format("Response is within TMS scope. Expected: '%s'", expectedBehavior);
        } else {
            message = String.format("Response may be outside TMS scope. Expected: '%s'", expectedBehavior);
        }
        
        logger.info("Validation: {} - {}", withinScope ? "PASSED" : "FAILED", message);
        
        return new ValidationResult(withinScope, message, response);
    }

    /**
     * Get the TMS scope instruction from properties
     */
    public static String getTMSScopeInstruction() {
        return validatorProperties.getProperty("tms.scope.instruction", 
            "TMS Agent should only respond to transportation management queries.");
    }

    private static List<String> parseKeywords(String input) {
        if (input == null || input.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(input.toLowerCase().split("\\s*,\\s*"));
    }

    private static boolean containsAnyKeyword(String text, List<String> keywords) {
        for (String keyword : keywords) {
            if (text.contains(keyword.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Simple validation result holder
     */
    public static class ValidationResult {
        private final boolean passed;
        private final String message;
        private final String response;

        public ValidationResult(boolean passed, String message, String response) {
            this.passed = passed;
            this.message = message;
            this.response = response;
        }

        public boolean isPassed() { return passed; }
        public String getMessage() { return message; }
        public String getResponse() { return response; }

        @Override
        public String toString() {
            return String.format("ValidationResult{passed=%s, message='%s'}", passed, message);
        }
    }
}
