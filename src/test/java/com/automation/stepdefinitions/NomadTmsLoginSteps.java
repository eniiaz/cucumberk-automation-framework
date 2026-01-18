package com.automation.stepdefinitions;

import com.automation.pages.nomadtms.NomadTmsDashboardPage;
import com.automation.pages.nomadtms.NomadTmsLoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.*;

/**
 * Login Step Definitions for Nomad TMS Fleet Management System
 */
public class NomadTmsLoginSteps {
    private static final Logger logger = LogManager.getLogger(NomadTmsLoginSteps.class);

    private final NomadTmsLoginPage loginPage = new NomadTmsLoginPage();
    private final NomadTmsDashboardPage dashboardPage = new NomadTmsDashboardPage();

    // ==================== Given Steps ====================

    @Given("the Nomad TMS user is on the login page")
    public void theNomadTmsUserIsOnTheLoginPage() {
        loginPage.navigateToLoginPage();
        assertTrue("Nomad TMS login page should be loaded", loginPage.isPageLoaded());
        logger.info("User is on Nomad TMS login page");
    }

    // ==================== When Steps ====================

    @When("the Nomad TMS user enters email {string}")
    public void theNomadTmsUserEntersEmail(String email) {
        loginPage.enterEmail(email);
        logger.info("Entered email: {}", email);
    }

    @When("the Nomad TMS user enters password {string}")
    public void theNomadTmsUserEntersPassword(String password) {
        loginPage.enterPassword(password);
        logger.info("Entered password");
    }

    @When("the Nomad TMS user clicks the sign in button")
    public void theNomadTmsUserClicksTheSignInButton() {
        loginPage.clickSignInButton();
        logger.info("Clicked sign in button");
    }

    @When("the Nomad TMS user logs in with email {string} and password {string}")
    public void theNomadTmsUserLogsInWithEmailAndPassword(String email, String password) {
        loginPage.login(email, password);
        logger.info("Login attempted with email: {}", email);
    }

    @When("the Nomad TMS user clicks the sign up tab")
    public void theNomadTmsUserClicksTheSignUpTab() {
        loginPage.clickSignUpTab();
        logger.info("Clicked sign up tab");
    }

    @When("the Nomad TMS user clicks the sign in tab")
    public void theNomadTmsUserClicksTheSignInTab() {
        loginPage.clickSignInTab();
        logger.info("Clicked sign in tab");
    }

    // ==================== Then Steps ====================

    @Then("the Nomad TMS user should be redirected to the dashboard")
    public void theNomadTmsUserShouldBeRedirectedToTheDashboard() {
        // Wait for navigation after login
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertTrue("User should be redirected to dashboard", dashboardPage.isPageLoaded());
        logger.info("User successfully redirected to Nomad TMS dashboard");
    }

    @Then("the Nomad TMS user should see a login error message")
    public void theNomadTmsUserShouldSeeALoginErrorMessage() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertTrue("Login error message should be displayed", loginPage.isErrorMessageDisplayed());
        logger.info("Login error message displayed: {}", loginPage.getErrorMessageText());
    }

    @Then("the Nomad TMS user should see a login error message containing {string}")
    public void theNomadTmsUserShouldSeeALoginErrorMessageContaining(String expectedText) {
        assertTrue("Error message should be displayed", loginPage.isErrorMessageDisplayed());
        String actualError = loginPage.getErrorMessageText();
        assertTrue("Error message should contain: " + expectedText + ". Actual: " + actualError,
                actualError.contains(expectedText));
        logger.info("Error message displayed: {}", actualError);
    }

    @Then("the Nomad TMS user should remain on the login page")
    public void theNomadTmsUserShouldRemainOnTheLoginPage() {
        assertTrue("User should remain on login page", loginPage.isOnLoginPage());
        logger.info("User remained on login page");
    }

    @Then("the Nomad TMS user should see the sign in tab")
    public void theNomadTmsUserShouldSeeTheSignInTab() {
        assertTrue("Sign In tab should be visible", loginPage.isSignInTabVisible());
        logger.info("Sign In tab is visible");
    }

    @Then("the Nomad TMS user should see the sign up tab")
    public void theNomadTmsUserShouldSeeTheSignUpTab() {
        assertTrue("Sign Up tab should be visible", loginPage.isSignUpTabVisible());
        logger.info("Sign Up tab is visible");
    }

    @Then("the Nomad TMS user should see the sign in form")
    public void theNomadTmsUserShouldSeeTheSignInForm() {
        assertTrue("Sign In form should be visible", loginPage.isSignInFormVisible());
        logger.info("Sign In form is visible");
    }
}
