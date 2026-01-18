package com.automation.stepdefinitions;

import com.automation.pages.nomadtms.NomadTmsAgentPage;
import com.automation.pages.nomadtms.NomadTmsDashboardPage;
import com.automation.pages.nomadtms.NomadTmsLoginPage;
import com.automation.utils.ConfigReader;
import com.automation.utils.LLMResponseValidator;
import com.automation.utils.LLMResponseValidator.ValidationResult;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.*;

/**
 * Step Definitions for Nomad TMS AI Agent Chat
 */
public class NomadTmsAgentSteps {
    private static final Logger logger = LogManager.getLogger(NomadTmsAgentSteps.class);

    private final NomadTmsLoginPage loginPage = new NomadTmsLoginPage();
    private final NomadTmsDashboardPage dashboardPage = new NomadTmsDashboardPage();
    private final NomadTmsAgentPage agentPage = new NomadTmsAgentPage();

    private String lastAgentResponse = "";

    @Given("the user is logged in to Nomad TMS")
    public void theUserIsLoggedInToNomadTMS() {
        loginPage.navigateToLoginPage();
        assertTrue("Login page should be loaded", loginPage.isPageLoaded());
        
        String email = ConfigReader.getNomadTmsTestUserEmail();
        String password = ConfigReader.getNomadTmsTestUserPassword();
        
        loginPage.login(email, password);
        logger.info("Logged in with email: {}", email);
        
        waitFor(3000);
        assertTrue("User should be on dashboard after login", dashboardPage.isPageLoaded());
    }

    @Given("the user navigates to the Agent chat page")
    public void theUserNavigatesToTheAgentChatPage() {
        agentPage.clickAgentMenu();
        waitFor(1000);
        assertTrue("Agent page should be displayed", agentPage.isPageLoaded());
        logger.info("User is on Agent chat page");
    }

    @When("the user sends message {string}")
    public void theUserSendsMessage(String message) {
        agentPage.sendMessage(message);
        lastAgentResponse = agentPage.getFullLastAgentResponse();
        logger.info("Sent: '{}' | Received: '{}'", message, lastAgentResponse);
    }

    @Then("the Agent should respond within TMS domain scope")
    public void theAgentShouldRespondWithinTMSDomainScope() {
        assertNotNull("Agent response should not be null", lastAgentResponse);
        assertFalse("Agent response should not be empty", lastAgentResponse.isEmpty());
        
        boolean isWithinScope = LLMResponseValidator.isWithinTMSScope(lastAgentResponse);
        assertTrue("Agent response should be within TMS domain scope. Response: " + lastAgentResponse, 
            isWithinScope);
        
        logger.info("Response validated within TMS scope");
    }

    @Then("the response should be validated as {string}")
    public void theResponseShouldBeValidatedAs(String expectedBehavior) {
        ValidationResult result = LLMResponseValidator.validate(lastAgentResponse, expectedBehavior);
        
        logger.info("Validation for '{}': {}", expectedBehavior, result);
        assertTrue("Response should match expected behavior: " + expectedBehavior + 
                   ". Result: " + result.getMessage(), result.isPassed());
    }

    private void waitFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
