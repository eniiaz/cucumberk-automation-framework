package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration Reader Utility
 * Reads properties from config.properties file at project root level
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
                logger.info("Configuration loaded successfully from project root: {}", configFile.getAbsolutePath());
                return;
            } catch (IOException e) {
                logger.warn("Failed to load config from project root: {}", e.getMessage());
            }
        }
        
        // Fallback to classpath resource
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
            if (is != null) {
                properties.load(is);
                logger.info("Configuration loaded successfully from classpath: {}", CONFIG_FILE_NAME);
                return;
            }
        } catch (IOException e) {
            logger.warn("Could not load from classpath: {}", e.getMessage());
        }
        
        // Last resort: try relative path
        File relativeConfig = new File(CONFIG_FILE_NAME);
        if (relativeConfig.exists() && relativeConfig.isFile()) {
            try (FileInputStream fis = new FileInputStream(relativeConfig)) {
                properties.load(fis);
                logger.info("Configuration loaded successfully from relative path: {}", relativeConfig.getAbsolutePath());
                return;
            } catch (IOException e) {
                logger.warn("Failed to load config from relative path: {}", e.getMessage());
            }
        }
        
        throw new RuntimeException("Config file not found. Searched in: " + 
            projectRoot + ", classpath, and current directory");
    }

    /**
     * Get property value by key
     * @param key property key
     * @return property value or null if not found
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
     * @param key property key
     * @param defaultValue default value if property not found
     * @return property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Get integer property
     * @param key property key
     * @return integer value
     */
    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }

    /**
     * Get integer property with default
     * @param key property key
     * @param defaultValue default value
     * @return integer value
     */
    public static int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }

    /**
     * Get boolean property
     * @param key property key
     * @return boolean value
     */
    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    /**
     * Get boolean property with default
     * @param key property key
     * @param defaultValue default value
     * @return boolean value
     */
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    // Convenience methods for common properties
    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getApiBaseUrl() {
        return getProperty("api.base.url");
    }

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

    public static String getDbDriver() {
        return getProperty("db.driver");
    }

    public static String getDbUrl() {
        return getProperty("db.url");
    }

    public static String getDbUsername() {
        return getProperty("db.username");
    }

    public static String getDbPassword() {
        return getProperty("db.password");
    }

    public static String getEnvironment() {
        return getProperty("environment", "qa");
    }

    // ==================== URL Methods ====================

    public static String getProductionUrl() {
        return getProperty("production.url");
    }

    public static String getStagingUrl() {
        return getProperty("staging.url");
    }

    public static String getQaUrl() {
        return getProperty("qa.url");
    }

    public static String getDevUrl() {
        return getProperty("dev.url");
    }

    // ==================== API Methods ====================

    public static String getApiProductionUrl() {
        return getProperty("api.production.url");
    }

    public static String getApiStagingUrl() {
        return getProperty("api.staging.url");
    }

    public static String getApiQaUrl() {
        return getProperty("api.qa.url");
    }

    public static String getApiDevUrl() {
        return getProperty("api.dev.url");
    }

    public static String getApiEndpoint(String endpointKey) {
        return getProperty("api.endpoint." + endpointKey);
    }

    public static int getApiTimeout() {
        return getIntProperty("api.timeout", 30000);
    }

    public static int getApiRetryCount() {
        return getIntProperty("api.retry.count", 3);
    }

    // ==================== Credentials Methods ====================

    public static String getTestUserEmail() {
        return getProperty("test.user.email");
    }

    public static String getTestUserPassword() {
        return getProperty("test.user.password");
    }

    public static String getTestUserFullName() {
        return getProperty("test.user.fullname");
    }

    public static String getApiKey() {
        return getProperty("api.key");
    }

    public static String getApiSecret() {
        return getProperty("api.secret");
    }

    public static String getApiToken() {
        return getProperty("api.token");
    }

    // ==================== Wait Methods ====================

    public static int getShortWait() {
        return getIntProperty("wait.short", 5);
    }

    public static int getMediumWait() {
        return getIntProperty("wait.medium", 10);
    }

    public static int getLongWait() {
        return getIntProperty("wait.long", 20);
    }

    public static int getVeryLongWait() {
        return getIntProperty("wait.very.long", 30);
    }

    // ==================== Feature Flags ====================

    public static boolean isApiTestingEnabled() {
        return getBooleanProperty("feature.api.testing", true);
    }

    public static boolean isUiTestingEnabled() {
        return getBooleanProperty("feature.ui.testing", true);
    }

    public static boolean isDbTestingEnabled() {
        return getBooleanProperty("feature.db.testing", false);
    }

    // ==================== Other Methods ====================

    public static int getRetryCount() {
        return getIntProperty("retry.count", 2);
    }

    public static int getRetryDelay() {
        return getIntProperty("retry.delay", 1000);
    }

    public static String getScreenshotPath() {
        return getProperty("screenshot.path", "target/screenshots");
    }

    public static boolean isScreenshotOnFailure() {
        return getBooleanProperty("screenshot.on.failure", true);
    }

    /**
     * Reload properties (useful for dynamic configuration changes)
     */
    public static void reload() {
        loadProperties();
    }
}

