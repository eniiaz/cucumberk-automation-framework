package com.automation.utils;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.List;
import java.util.Set;

/**
 * Browser Utility Class
 * Provides common browser operations and WebDriver helper methods
 */
public class BrowserUtils {
    private static final Logger logger = LogManager.getLogger(BrowserUtils.class);

    private BrowserUtils() {
        // Private constructor
    }

    /**
     * Get WebDriverWait instance
     * @return WebDriverWait with configured timeout
     */
    public static WebDriverWait getWait() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(ConfigReader.getExplicitWait()));
    }

    /**
     * Get WebDriverWait with custom timeout
     * @param timeoutSeconds custom timeout in seconds
     * @return WebDriverWait instance
     */
    public static WebDriverWait getWait(int timeoutSeconds) {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutSeconds));
    }

    /**
     * Wait for element to be visible
     * @param locator element locator
     * @return visible WebElement
     */
    public static WebElement waitForVisibility(By locator) {
        logger.debug("Waiting for visibility of element: {}", locator);
        return getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable
     * @param locator element locator
     * @return clickable WebElement
     */
    public static WebElement waitForClickability(By locator) {
        logger.debug("Waiting for clickability of element: {}", locator);
        return getWait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for element to be clickable
     * @param element WebElement
     * @return clickable WebElement
     */
    public static WebElement waitForClickability(WebElement element) {
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Click element with wait
     * @param locator element locator
     */
    public static void click(By locator) {
        logger.debug("Clicking element: {}", locator);
        waitForClickability(locator).click();
    }

    /**
     * Click element using JavaScript
     * @param element WebElement to click
     */
    public static void clickWithJS(WebElement element) {
        logger.debug("Clicking element with JavaScript");
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * Send keys with wait
     * @param locator element locator
     * @param text text to send
     */
    public static void sendKeys(By locator, String text) {
        logger.debug("Sending keys '{}' to element: {}", text, locator);
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text from element
     * @param locator element locator
     * @return element text
     */
    public static String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    /**
     * Check if element is displayed
     * @param locator element locator
     * @return true if displayed
     */
    public static boolean isDisplayed(By locator) {
        try {
            return DriverManager.getDriver().findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Select dropdown option by visible text
     * @param locator dropdown locator
     * @param text visible text
     */
    public static void selectByVisibleText(By locator, String text) {
        logger.debug("Selecting '{}' from dropdown: {}", text, locator);
        Select select = new Select(waitForVisibility(locator));
        select.selectByVisibleText(text);
    }

    /**
     * Select dropdown option by value
     * @param locator dropdown locator
     * @param value option value
     */
    public static void selectByValue(By locator, String value) {
        Select select = new Select(waitForVisibility(locator));
        select.selectByValue(value);
    }

    /**
     * Select dropdown option by index
     * @param locator dropdown locator
     * @param index option index
     */
    public static void selectByIndex(By locator, int index) {
        Select select = new Select(waitForVisibility(locator));
        select.selectByIndex(index);
    }

    /**
     * Get all dropdown options
     * @param locator dropdown locator
     * @return list of WebElements
     */
    public static List<WebElement> getDropdownOptions(By locator) {
        Select select = new Select(waitForVisibility(locator));
        return select.getOptions();
    }

    /**
     * Hover over element
     * @param locator element locator
     */
    public static void hover(By locator) {
        logger.debug("Hovering over element: {}", locator);
        Actions actions = new Actions(DriverManager.getDriver());
        actions.moveToElement(waitForVisibility(locator)).perform();
    }

    /**
     * Double click element
     * @param locator element locator
     */
    public static void doubleClick(By locator) {
        logger.debug("Double clicking element: {}", locator);
        Actions actions = new Actions(DriverManager.getDriver());
        actions.doubleClick(waitForClickability(locator)).perform();
    }

    /**
     * Right click element
     * @param locator element locator
     */
    public static void rightClick(By locator) {
        logger.debug("Right clicking element: {}", locator);
        Actions actions = new Actions(DriverManager.getDriver());
        actions.contextClick(waitForClickability(locator)).perform();
    }

    /**
     * Drag and drop
     * @param source source element locator
     * @param target target element locator
     */
    public static void dragAndDrop(By source, By target) {
        logger.debug("Drag and drop from {} to {}", source, target);
        Actions actions = new Actions(DriverManager.getDriver());
        WebElement sourceElement = waitForVisibility(source);
        WebElement targetElement = waitForVisibility(target);
        actions.dragAndDrop(sourceElement, targetElement).perform();
    }

    /**
     * Scroll to element
     * @param locator element locator
     */
    public static void scrollToElement(By locator) {
        logger.debug("Scrolling to element: {}", locator);
        WebElement element = waitForVisibility(locator);
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    /**
     * Scroll to element
     * @param element WebElement
     */
    public static void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    /**
     * Execute JavaScript
     * @param script JavaScript code
     * @param args arguments
     * @return script result
     */
    public static Object executeJS(String script, Object... args) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        return js.executeScript(script, args);
    }

    /**
     * Wait for page to load completely
     */
    public static void waitForPageLoad() {
        logger.debug("Waiting for page to load");
        getWait().until(driver -> 
            ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete")
        );
    }

    /**
     * Switch to window by title
     * @param title window title
     */
    public static void switchToWindowByTitle(String title) {
        logger.debug("Switching to window with title: {}", title);
        WebDriver driver = DriverManager.getDriver();
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle : windowHandles) {
            driver.switchTo().window(handle);
            if (driver.getTitle().contains(title)) {
                return;
            }
        }
        throw new RuntimeException("Window with title '" + title + "' not found");
    }

    /**
     * Switch to frame by locator
     * @param locator frame locator
     */
    public static void switchToFrame(By locator) {
        logger.debug("Switching to frame: {}", locator);
        WebElement frame = waitForVisibility(locator);
        DriverManager.getDriver().switchTo().frame(frame);
    }

    /**
     * Switch to default content
     */
    public static void switchToDefaultContent() {
        DriverManager.getDriver().switchTo().defaultContent();
    }

    /**
     * Accept alert
     */
    public static void acceptAlert() {
        logger.debug("Accepting alert");
        getWait().until(ExpectedConditions.alertIsPresent());
        DriverManager.getDriver().switchTo().alert().accept();
    }

    /**
     * Dismiss alert
     */
    public static void dismissAlert() {
        logger.debug("Dismissing alert");
        getWait().until(ExpectedConditions.alertIsPresent());
        DriverManager.getDriver().switchTo().alert().dismiss();
    }

    /**
     * Get alert text
     * @return alert text
     */
    public static String getAlertText() {
        getWait().until(ExpectedConditions.alertIsPresent());
        return DriverManager.getDriver().switchTo().alert().getText();
    }

    /**
     * Take screenshot and return as byte array
     * @return screenshot bytes
     */
    public static byte[] takeScreenshot() {
        logger.debug("Taking screenshot");
        TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
        return ts.getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Attach screenshot to Allure report
     * @param name screenshot name
     */
    public static void attachScreenshotToAllure(String name) {
        byte[] screenshot = takeScreenshot();
        Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), "png");
    }

    /**
     * Wait for specified duration
     * @param seconds seconds to wait
     */
    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Get current URL
     * @return current URL
     */
    public static String getCurrentUrl() {
        return DriverManager.getDriver().getCurrentUrl();
    }

    /**
     * Get page title
     * @return page title
     */
    public static String getPageTitle() {
        return DriverManager.getDriver().getTitle();
    }

    /**
     * Navigate to URL
     * @param url URL to navigate to
     */
    public static void navigateTo(String url) {
        logger.info("Navigating to: {}", url);
        DriverManager.getDriver().get(url);
    }

    /**
     * Refresh page
     */
    public static void refresh() {
        DriverManager.getDriver().navigate().refresh();
    }

    /**
     * Navigate back
     */
    public static void back() {
        DriverManager.getDriver().navigate().back();
    }

    /**
     * Navigate forward
     */
    public static void forward() {
        DriverManager.getDriver().navigate().forward();
    }
}

