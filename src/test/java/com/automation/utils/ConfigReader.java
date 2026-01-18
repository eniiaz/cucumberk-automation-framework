package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration Reader Utility for Nomad TMS
 */
public class ConfigReader {
    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;
    private static final String CONFIG_FILE_NAME = "config.properties";

    static {
        loadProperties();
    }

    private ConfigReader() {
        // Private constructor to prevent instantiation
    }

    private static void loadProperties() {
        properties = new Properties();
        
        // Try project root first
        String projectRoot = System.getProperty("user.dir");
        File configFile = new File(projectRoot, CONFIG_FILE_NAME);
        
        if (configFile.exists() && configFile.isFile()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                properties.load(fis);
                logger.info("Configuration loaded from: {}", configFile.getAbsolutePath());
                return;
            } catch (IOException e) {
                logger.warn("Failed to load config from project root: {}", e.getMessage());
            }
        }
        
        // Fallback to classpath resource
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
            if (is != null) {
                properties.load(is);
                logger.info("Configuration loaded from classpath: {}", CONFIG_FILE_NAME);
                return;
            }
        } catch (IOException e) {
            logger.warn("Could not load from classpath: {}", e.getMessage());
        }
        
        throw new RuntimeException("Config file not found: " + CONFIG_FILE_NAME);
    }

    /**
     * Get property value by key
     */
    public static String getProperty(String key) {
        String value = System.getProperty(key);
        if (value == null) {
            value = properties.getProperty(key);
        }
        return value;
    }

    /**
     * Get property value with default fallback
     */
    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Get integer property
     */
    public static int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    /**
     * Get boolean property
     */
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    // ==================== Nomad TMS Configuration ====================

    public static String getNomadTmsBaseUrl() {
        return getProperty("nomadtms.base.url");
    }

    public static String getNomadTmsLoginUrl() {
        return getProperty("nomadtms.login.url");
    }

    public static String getNomadTmsSignupUrl() {
        return getProperty("nomadtms.signup.url");
    }

    public static String getNomadTmsApiBaseUrl() {
        return getProperty("nomadtms.api.base.url");
    }

    public static String getNomadTmsTestUserEmail() {
        return getProperty("nomadtms.test.user.email");
    }

    public static String getNomadTmsTestUserPassword() {
        return getProperty("nomadtms.test.user.password");
    }

    public static String getNomadTmsTestUserFirstName() {
        return getProperty("nomadtms.test.user.firstname");
    }

    public static String getNomadTmsTestUserLastName() {
        return getProperty("nomadtms.test.user.lastname");
    }

    public static String getNomadTmsTestUserOrganization() {
        return getProperty("nomadtms.test.user.organization");
    }

    // ==================== Browser Configuration ====================

    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        return getBooleanProperty("headless", false);
    }

    public static int getImplicitWait() {
        return getIntProperty("implicit.wait", 10);
    }

    public static int getExplicitWait() {
        return getIntProperty("explicit.wait", 15);
    }

    public static int getPageLoadTimeout() {
        return getIntProperty("page.load.timeout", 30);
    }

    // ==================== API Configuration ====================

    public static int getApiTimeout() {
        return getIntProperty("api.timeout", 30000);
    }

    public static String getEnvironment() {
        return getProperty("environment", "qa");
    }

    // ==================== Screenshot & Reporting ====================

    public static String getScreenshotPath() {
        return getProperty("screenshot.path", "target/screenshots");
    }

    public static boolean isScreenshotOnFailure() {
        return getBooleanProperty("screenshot.on.failure", true);
    }

    public static int getRetryCount() {
        return getIntProperty("retry.count", 2);
    }

    /**
     * Reload properties
     */
    public static void reload() {
        loadProperties();
    }
}
