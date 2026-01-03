package com.automation.pages;

import com.automation.utils.ConfigReader;
import com.automation.utils.BrowserUtils;
import com.automation.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Base Page Object Class
 * Provides common functionality for all page objects
 * 
 * NOTE: Does NOT cache driver - always gets fresh reference from DriverManager
 * This prevents stale driver issues between scenarios
 */
public abstract class BasePage {
    protected final Logger logger = LogManager.getLogger(this.getClass());

    protected BasePage() {
        // Initialize PageFactory with current driver
        PageFactory.initElements(getDriver(), this);
    }

    /**
     * Get fresh driver reference - ALWAYS use this instead of cached driver
     * @return current WebDriver instance
     */
    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    /**
     * Get fresh WebDriverWait - creates new wait with current driver
     * @return WebDriverWait instance
     */
    protected WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(ConfigReader.getExplicitWait()));
    }

    // ==================== Navigation ====================

    /**
     * Navigate to page URL
     * @param url page URL
     */
    protected void navigateTo(String url) {
        logger.info("Navigating to: {}", url);
        getDriver().get(url);
        waitForPageLoad();
    }

    /**
     * Get current URL
     * @return current URL
     */
    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    /**
     * Get page title
     * @return page title
     */
    public String getPageTitle() {
        return getDriver().getTitle();
    }

    // ==================== Wait Methods ====================

    /**
     * Wait for element to be visible
     * @param element WebElement
     * @return visible WebElement
     */
    protected WebElement waitForVisibility(WebElement element) {
        return getWait().until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for element to be visible by locator
     * @param locator element locator
     * @return visible WebElement
     */
    protected WebElement waitForVisibility(By locator) {
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable
     * @param element WebElement
     * @return clickable WebElement
     */
    protected WebElement waitForClickability(WebElement element) {
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for element to be clickable by locator
     * @param locator element locator
     * @return clickable WebElement
     */
    protected WebElement waitForClickability(By locator) {
        return getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for page to load completely
     */
    protected void waitForPageLoad() {
        BrowserUtils.waitForPageLoad();
    }

    /**
     * Wait for element to disappear
     * @param locator element locator
     * @return true if element is invisible
     */
    protected boolean waitForInvisibility(By locator) {
        return getWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait for text to be present in element
     * @param element WebElement
     * @param text expected text
     * @return true if text is present
     */
    protected boolean waitForTextPresent(WebElement element, String text) {
        return getWait().until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    // ==================== Element Interactions ====================

    /**
     * Click element with wait
     * @param element WebElement to click
     */
    protected void click(WebElement element) {
        logger.debug("Clicking element: {}", element);
        waitForClickability(element).click();
    }

    /**
     * Click element using JavaScript
     * @param element WebElement to click
     */
    protected void clickWithJS(WebElement element) {
        BrowserUtils.clickWithJS(element);
    }

    /**
     * Send keys to element
     * @param element WebElement
     * @param text text to send
     */
    protected void sendKeys(WebElement element, String text) {
        logger.debug("Sending keys '{}' to element", text);
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text from element
     * @param element WebElement
     * @return element text
     */
    protected String getText(WebElement element) {
        return waitForVisibility(element).getText();
    }

    /**
     * Get attribute value from element
     * @param element WebElement
     * @param attribute attribute name
     * @return attribute value
     */
    protected String getAttribute(WebElement element, String attribute) {
        return waitForVisibility(element).getAttribute(attribute);
    }

    /**
     * Check if element is displayed
     * @param element WebElement
     * @return true if displayed
     */
    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if element is enabled
     * @param element WebElement
     * @return true if enabled
     */
    protected boolean isEnabled(WebElement element) {
        return waitForVisibility(element).isEnabled();
    }

    /**
     * Check if element is selected
     * @param element WebElement
     * @return true if selected
     */
    protected boolean isSelected(WebElement element) {
        return waitForVisibility(element).isSelected();
    }

    // ==================== Element Finding ====================

    /**
     * Find element by locator
     * @param locator element locator
     * @return WebElement
     */
    protected WebElement findElement(By locator) {
        return getDriver().findElement(locator);
    }

    /**
     * Find elements by locator
     * @param locator element locator
     * @return list of WebElements
     */
    protected List<WebElement> findElements(By locator) {
        return getDriver().findElements(locator);
    }

    // ==================== Scrolling ====================

    /**
     * Scroll to element
     * @param element WebElement
     */
    protected void scrollToElement(WebElement element) {
        BrowserUtils.scrollToElement(element);
    }

    // ==================== Dropdown ====================

    /**
     * Select dropdown option by visible text
     * @param element dropdown WebElement
     * @param text visible text
     */
    protected void selectByVisibleText(WebElement element, String text) {
        new org.openqa.selenium.support.ui.Select(element).selectByVisibleText(text);
    }

    /**
     * Select dropdown option by value
     * @param element dropdown WebElement
     * @param value option value
     */
    protected void selectByValue(WebElement element, String value) {
        new org.openqa.selenium.support.ui.Select(element).selectByValue(value);
    }

    /**
     * Select dropdown option by index
     * @param element dropdown WebElement
     * @param index option index
     */
    protected void selectByIndex(WebElement element, int index) {
        new org.openqa.selenium.support.ui.Select(element).selectByIndex(index);
    }

    // ==================== Verification ====================

    /**
     * Verify page is loaded (to be implemented by child classes)
     * @return true if page is loaded correctly
     */
    public abstract boolean isPageLoaded();
}
