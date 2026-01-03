package com.automation.stepdefinitions;

import com.automation.utils.ConfigReader;
import com.automation.utils.BrowserUtils;
import com.automation.utils.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.*;

/**
 * Common Step Definitions
 * Contains reusable step implementations across different scenarios
 */
public class CommonSteps {
    private static final Logger logger = LogManager.getLogger(CommonSteps.class);

    // ==================== Given Steps ====================

    @Given("the browser is open")
    public void theBrowserIsOpen() {
        DriverManager.getDriver();
        logger.info("Browser opened");
    }

    @Given("the user navigates to {string}")
    public void theUserNavigatesTo(String url) {
        BrowserUtils.navigateTo(url);
    }

    @Given("the user navigates to the application")
    public void theUserNavigatesToTheApplication() {
        String baseUrl = ConfigReader.getBaseUrl();
        BrowserUtils.navigateTo(baseUrl);
        logger.info("Navigated to application: {}", baseUrl);
    }

    @Given("the environment is {string}")
    public void theEnvironmentIs(String environment) {
        String actualEnv = ConfigReader.getEnvironment();
        assertEquals("Environment should be " + environment,
                environment.toLowerCase(), actualEnv.toLowerCase());
    }

    // ==================== When Steps ====================

    @When("the user waits for {int} second(s)")
    public void theUserWaitsForSeconds(int seconds) {
        BrowserUtils.sleep(seconds);
    }

    @When("the user refreshes the page")
    public void theUserRefreshesThePage() {
        BrowserUtils.refresh();
    }

    @When("the user navigates back")
    public void theUserNavigatesBack() {
        BrowserUtils.back();
    }

    @When("the user navigates forward")
    public void theUserNavigatesForward() {
        BrowserUtils.forward();
    }

    @When("the user accepts the alert")
    public void theUserAcceptsTheAlert() {
        BrowserUtils.acceptAlert();
    }

    @When("the user dismisses the alert")
    public void theUserDismissesTheAlert() {
        BrowserUtils.dismissAlert();
    }

    @When("the user switches to window with title {string}")
    public void theUserSwitchesToWindowWithTitle(String title) {
        BrowserUtils.switchToWindowByTitle(title);
    }

    @When("I store value {string} as {string}")
    public void iStoreValueAs(String value, String key) {
        ScenarioContext.set(key, value);
        logger.debug("Stored {} = {}", key, value);
    }

    // ==================== Then Steps ====================

    @Then("the page title should be {string}")
    public void thePageTitleShouldBe(String expectedTitle) {
        String actualTitle = BrowserUtils.getPageTitle();
        assertEquals("Page title mismatch", expectedTitle, actualTitle);
    }

    @Then("the page title should contain {string}")
    public void thePageTitleShouldContain(String expectedText) {
        String actualTitle = BrowserUtils.getPageTitle();
        assertTrue("Page title should contain: " + expectedText, actualTitle.contains(expectedText));
    }

    @Then("the current URL should be {string}")
    public void theCurrentUrlShouldBe(String expectedUrl) {
        String actualUrl = BrowserUtils.getCurrentUrl();
        assertEquals("URL mismatch", expectedUrl, actualUrl);
    }

    @Then("the current URL should contain {string}")
    public void theCurrentUrlShouldContain(String expectedText) {
        String actualUrl = BrowserUtils.getCurrentUrl();
        assertTrue("URL should contain: " + expectedText, actualUrl.contains(expectedText));
    }

    @Then("the alert text should be {string}")
    public void theAlertTextShouldBe(String expectedText) {
        String alertText = BrowserUtils.getAlertText();
        assertEquals("Alert text mismatch", expectedText, alertText);
    }

    @Then("the alert text should contain {string}")
    public void theAlertTextShouldContain(String expectedText) {
        String alertText = BrowserUtils.getAlertText();
        assertTrue("Alert text should contain: " + expectedText, alertText.contains(expectedText));
    }

    @Then("the stored value {string} should be {string}")
    public void theStoredValueShouldBe(String key, String expectedValue) {
        String actualValue = ScenarioContext.get(key);
        assertEquals("Stored value mismatch for key: " + key, expectedValue, actualValue);
    }

    @Then("take a screenshot")
    public void takeAScreenshot() {
        BrowserUtils.attachScreenshotToAllure("Manual Screenshot");
        logger.info("Screenshot captured");
    }
}
