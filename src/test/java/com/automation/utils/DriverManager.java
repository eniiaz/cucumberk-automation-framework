package com.automation.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * WebDriver Manager Utility
 * Manages WebDriver instance for test execution
 */
public class DriverManager {
    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    
    private static WebDriver driver;

    private DriverManager() {
        // Private constructor to prevent instantiation
    }

    /**
     * Get WebDriver instance - creates new one if doesn't exist
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            String browserType = ConfigReader.getBrowser().toLowerCase();
            boolean isHeadless = ConfigReader.isHeadless();

            System.out.println("🔧 Browser Configuration:");
            System.out.println("   Browser: " + browserType);
            System.out.println("   Headless: " + isHeadless);

            switch (browserType) {
                case "chrome":
                    driver = createChromeDriver(isHeadless);
                    break;
                case "firefox":
                    driver = createFirefoxDriver(isHeadless);
                    break;
                case "edge":
                    driver = createEdgeDriver(isHeadless);
                    break;
                case "safari":
                    driver = createSafariDriver();
                    break;
                default:
                    logger.warn("Unknown browser '{}', defaulting to Chrome", browserType);
                    driver = createChromeDriver(isHeadless);
            }

            logger.info("WebDriver initialized successfully");
        }

        // Configure timeouts
        configureTimeouts();

        return driver;
    }

    private static WebDriver createChromeDriver(boolean isHeadless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        if (isHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            System.out.println("✅ Chrome running in headless mode");
        } else {
            options.addArguments("--start-maximized");
            System.out.println("🖥️ Chrome running in headed mode");
        }

        // Stability options - disable automation detection
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--remote-allow-origins=*");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        // Disable password save prompts and other popups
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(boolean isHeadless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        if (isHeadless) {
            options.addArguments("--headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");
            System.out.println("✅ Firefox running in headless mode");
        } else {
            System.out.println("🖥️ Firefox running in headed mode");
        }

        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver(boolean isHeadless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();

        if (isHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            System.out.println("✅ Edge running in headless mode");
        } else {
            options.addArguments("--start-maximized");
            System.out.println("🖥️ Edge running in headed mode");
        }

        return new EdgeDriver(options);
    }

    private static WebDriver createSafariDriver() {
        // Safari doesn't support headless mode
        System.out.println("🖥️ Safari running (headless not supported)");
        return new SafariDriver();
    }

    private static void configureTimeouts() {
        if (driver != null) {
            int implicitWait = ConfigReader.getImplicitWait();
            int pageLoadTimeout = ConfigReader.getPageLoadTimeout();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        }
    }

    /**
     * Close and quit WebDriver instance
     */
    public static void closeDriver() {
        if (driver != null) {
            try {
                driver.quit();
                logger.info("🔒 WebDriver closed successfully");
            } catch (Exception e) {
                logger.error("Error closing WebDriver: {}", e.getMessage());
            } finally {
                driver = null;
            }
        }
    }

    /**
     * Quit WebDriver (alias for closeDriver)
     */
    public static void quitDriver() {
        closeDriver();
    }

    /**
     * Check if WebDriver instance exists without creating a new one
     * @return true if driver exists, false otherwise
     */
    public static boolean hasDriver() {
        return driver != null;
    }

    /**
     * Get existing WebDriver instance without creating a new one
     * @return existing driver instance or null if none exists
     */
    public static WebDriver getExistingDriver() {
        return driver;
    }
}

