package com.automation.stepdefinitions;

import com.automation.pages.DashboardPage;
import com.automation.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.*;

/**
 * Login Step Definitions for MKF CRM
 */
public class LoginSteps {
    private static final Logger logger = LogManager.getLogger(LoginSteps.class);

    private final LoginPage loginPage = new LoginPage();
    private final DashboardPage dashboardPage = new DashboardPage();

    // ==================== Given Steps ====================

    @Given("the user is on the login page")
    public void theUserIsOnTheLoginPage() {
        loginPage.navigateToLoginPage();
        assertTrue("Login page should be loaded", loginPage.isPageLoaded());
    }

    // ==================== When Steps ====================

    @When("the user enters email {string}")
    public void theUserEntersEmail(String email) {
        loginPage.enterEmail(email);
    }

    @When("the user enters password {string}")
    public void theUserEntersPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("the user clicks the sign in button")
    public void theUserClicksTheSignInButton() {
        loginPage.clickSignInButton();
    }

    @When("the user logs in with email {string} and password {string}")
    public void theUserLogsInWithEmailAndPassword(String email, String password) {
        loginPage.login(email, password);
    }

    @When("the user clicks the sign up link")
    public void theUserClicksTheSignUpLink() {
        loginPage.clickSignUpLink();
    }

    // ==================== Then Steps ====================

    @Then("the user should be redirected to the dashboard page")
    public void theUserShouldBeRedirectedToTheDashboardPage() {
        // Wait a bit for navigation
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertTrue("Dashboard page should be loaded", dashboardPage.isPageLoaded());
        logger.info("User successfully redirected to dashboard page");
    }

    @Then("the user should see an error message")
    public void theUserShouldSeeAnErrorMessage() {
        assertTrue("Error message should be displayed", loginPage.isErrorMessageDisplayed());
        logger.info("Error message displayed: {}", loginPage.getErrorMessageText());
    }

    @Then("the user should see an error message containing {string}")
    public void theUserShouldSeeAnErrorMessageContaining(String expectedText) {
        assertTrue("Error message should be displayed", loginPage.isErrorMessageDisplayed());
        String actualError = loginPage.getErrorMessageText();
        assertTrue("Error message should contain: " + expectedText + ". Actual: " + actualError,
                actualError.contains(expectedText));
    }

    @Then("the user should remain on the login page")
    public void theUserShouldRemainOnTheLoginPage() {
        assertTrue("User should remain on login page", loginPage.isPageLoaded());
        String currentUrl = loginPage.getCurrentUrl();
        assertTrue("URL should contain /login. Current URL: " + currentUrl, currentUrl.contains("/login"));
    }
}
