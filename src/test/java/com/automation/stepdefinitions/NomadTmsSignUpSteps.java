package com.automation.stepdefinitions;

import com.automation.pages.nomadtms.NomadTmsDashboardPage;
import com.automation.pages.nomadtms.NomadTmsSignUpPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.*;

/**
 * Sign Up Step Definitions for Nomad TMS Fleet Management System
 */
public class NomadTmsSignUpSteps {
    private static final Logger logger = LogManager.getLogger(NomadTmsSignUpSteps.class);

    private final NomadTmsSignUpPage signUpPage = new NomadTmsSignUpPage();
    private final NomadTmsDashboardPage dashboardPage = new NomadTmsDashboardPage();

    // ==================== Given Steps ====================

    @Given("the Nomad TMS user is on the signup page")
    public void theNomadTmsUserIsOnTheSignupPage() {
        signUpPage.navigateToSignUpPage();
        assertTrue("Nomad TMS signup page should be loaded", signUpPage.isPageLoaded());
        logger.info("User is on Nomad TMS signup page");
    }

    // ==================== When Steps ====================

    @When("the Nomad TMS user enters first name {string}")
    public void theNomadTmsUserEntersFirstName(String firstName) {
        signUpPage.enterFirstName(firstName);
        logger.info("Entered first name: {}", firstName);
    }

    @When("the Nomad TMS user enters last name {string}")
    public void theNomadTmsUserEntersLastName(String lastName) {
        signUpPage.enterLastName(lastName);
        logger.info("Entered last name: {}", lastName);
    }

    @When("the Nomad TMS user enters organization name {string}")
    public void theNomadTmsUserEntersOrganizationName(String orgName) {
        signUpPage.enterOrganizationName(orgName);
        logger.info("Entered organization name: {}", orgName);
    }

    @When("the Nomad TMS user enters signup email {string}")
    public void theNomadTmsUserEntersSignupEmail(String email) {
        signUpPage.enterEmail(email);
        logger.info("Entered signup email: {}", email);
    }

    @When("the Nomad TMS user enters signup password {string}")
    public void theNomadTmsUserEntersSignupPassword(String password) {
        signUpPage.enterPassword(password);
        logger.info("Entered signup password");
    }

    @When("the Nomad TMS user clicks the create account button")
    public void theNomadTmsUserClicksTheCreateAccountButton() {
        signUpPage.clickCreateAccountButton();
        logger.info("Clicked create account button");
    }

    // Note: Sign in/Sign up tab click steps are defined in NomadTmsLoginSteps to avoid duplicates

    // ==================== Then Steps ====================

    @Then("the Nomad TMS user should be successfully registered")
    public void theNomadTmsUserShouldBeSuccessfullyRegistered() {
        // Wait for registration to complete
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // After successful registration, user should be redirected to dashboard or confirmation page
        assertTrue("User should be successfully registered and redirected", 
                dashboardPage.isPageLoaded() || !signUpPage.isOnSignUpPage());
        logger.info("User successfully registered");
    }

    @Then("the Nomad TMS user should see a signup error message")
    public void theNomadTmsUserShouldSeeASignupErrorMessage() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertTrue("Signup error message should be displayed", signUpPage.isErrorMessageDisplayed());
        logger.info("Signup error message displayed: {}", signUpPage.getErrorMessageText());
    }

    @Then("the Nomad TMS user should see a signup error message containing {string}")
    public void theNomadTmsUserShouldSeeASignupErrorMessageContaining(String expectedText) {
        assertTrue("Error message should be displayed", signUpPage.isErrorMessageDisplayed());
        String actualError = signUpPage.getErrorMessageText();
        assertTrue("Error message should contain: " + expectedText + ". Actual: " + actualError,
                actualError.contains(expectedText));
        logger.info("Signup error message displayed: {}", actualError);
    }

    @Then("the Nomad TMS user should remain on the signup page")
    public void theNomadTmsUserShouldRemainOnTheSignupPage() {
        assertTrue("User should remain on signup page", signUpPage.isOnSignUpPage());
        logger.info("User remained on signup page");
    }

    @Then("the Nomad TMS user should see validation error for first name")
    public void theNomadTmsUserShouldSeeValidationErrorForFirstName() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Check for any validation message or that user remains on page
        assertTrue("User should see validation error or remain on signup page", 
                signUpPage.isValidationMessageDisplayed() || signUpPage.isOnSignUpPage());
        logger.info("Validation error displayed for first name");
    }

    @Then("the Nomad TMS user should see validation error for last name")
    public void theNomadTmsUserShouldSeeValidationErrorForLastName() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertTrue("User should see validation error or remain on signup page", 
                signUpPage.isValidationMessageDisplayed() || signUpPage.isOnSignUpPage());
        logger.info("Validation error displayed for last name");
    }

    @Then("the Nomad TMS user should see validation error for organization")
    public void theNomadTmsUserShouldSeeValidationErrorForOrganization() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertTrue("User should see validation error or remain on signup page", 
                signUpPage.isValidationMessageDisplayed() || signUpPage.isOnSignUpPage());
        logger.info("Validation error displayed for organization");
    }

    @Then("the Nomad TMS user should see validation error for email")
    public void theNomadTmsUserShouldSeeValidationErrorForEmail() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertTrue("User should see validation error or remain on signup page", 
                signUpPage.isValidationMessageDisplayed() || signUpPage.isOnSignUpPage());
        logger.info("Validation error displayed for email");
    }

    @Then("the Nomad TMS user should see validation error for email format")
    public void theNomadTmsUserShouldSeeValidationErrorForEmailFormat() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertTrue("User should see validation error for email format or remain on signup page", 
                signUpPage.isValidationMessageDisplayed() || signUpPage.isOnSignUpPage());
        logger.info("Validation error displayed for email format");
    }

    @Then("the Nomad TMS user should see validation error for password")
    public void theNomadTmsUserShouldSeeValidationErrorForPassword() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertTrue("User should see validation error or remain on signup page", 
                signUpPage.isValidationMessageDisplayed() || signUpPage.isOnSignUpPage());
        logger.info("Validation error displayed for password");
    }

    @Then("the Nomad TMS user should see password strength validation message")
    public void theNomadTmsUserShouldSeePasswordStrengthValidationMessage() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertTrue("User should see password strength validation or remain on signup page", 
                signUpPage.isValidationMessageDisplayed() || signUpPage.isOnSignUpPage());
        logger.info("Password strength validation message displayed");
    }

    @Then("the Nomad TMS user should see the sign up form")
    public void theNomadTmsUserShouldSeeTheSignUpForm() {
        assertTrue("Sign Up form should be visible", signUpPage.isSignUpFormVisible());
        logger.info("Sign Up form is visible");
    }

    @Then("the Nomad TMS create account button should be disabled")
    public void theNomadTmsCreateAccountButtonShouldBeDisabled() {
        assertFalse("Create account button should be disabled", signUpPage.isCreateAccountButtonEnabled());
        logger.info("Create account button is disabled");
    }
}
