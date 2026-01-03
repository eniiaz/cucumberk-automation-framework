package com.automation.stepdefinitions;

import com.automation.pages.DashboardPage;
import com.automation.pages.SignUpPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.*;

/**
 * Sign Up Step Definitions for MKF CRM
 */
public class SignUpSteps {
    private static final Logger logger = LogManager.getLogger(SignUpSteps.class);

    private final SignUpPage signUpPage = new SignUpPage();
    private final DashboardPage dashboardPage = new DashboardPage();

    // ==================== Given Steps ====================

    @Given("the user is on the sign up page")
    public void theUserIsOnTheSignUpPage() {
        signUpPage.navigateToSignUpPage();
        assertTrue("Sign up page should be loaded", signUpPage.isPageLoaded());
    }

    // ==================== When Steps ====================

    @When("the user enters full name {string}")
    public void theUserEntersFullName(String fullName) {
        signUpPage.enterFullName(fullName);
    }

    @When("the user enters signup email {string}")
    public void theUserEntersSignupEmail(String email) {
        signUpPage.enterEmail(email);
    }

    @When("the user enters signup password {string}")
    public void theUserEntersSignupPassword(String password) {
        signUpPage.enterPassword(password);
    }

    @When("the user clicks the create account button")
    public void theUserClicksTheCreateAccountButton() {
        signUpPage.clickCreateAccountButton();
    }

    @When("the user signs up with full name {string}, email {string}, and password {string}")
    public void theUserSignsUpWithCredentials(String fullName, String email, String password) {
        signUpPage.signUp(fullName, email, password);
    }

    // ==================== Then Steps ====================

    @Then("the user should be successfully registered")
    public void theUserShouldBeSuccessfullyRegistered() {
        // Wait a bit for navigation
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertTrue("Dashboard page should be loaded", dashboardPage.isPageLoaded());
        logger.info("User successfully registered and redirected to dashboard page");
    }

    @Then("the user should see a signup error message")
    public void theUserShouldSeeSignupErrorMessage() {
        assertTrue("Error message should be displayed", signUpPage.isErrorMessageDisplayed());
        logger.info("Error message displayed: {}", signUpPage.getErrorMessageText());
    }

    @Then("the user should see a signup error message containing {string}")
    public void theUserShouldSeeSignupErrorMessageContaining(String expectedText) {
        assertTrue("Error message should be displayed", signUpPage.isErrorMessageDisplayed());
        String actualError = signUpPage.getErrorMessageText();
        assertTrue("Error message should contain: " + expectedText + ". Actual: " + actualError,
                actualError.contains(expectedText));
    }

    @Then("the user should see password validation message")
    public void theUserShouldSeePasswordValidationMessage() {
        assertTrue("Password validation message should be displayed",
                signUpPage.isPasswordValidationMessageDisplayed());
        logger.info("Password validation message: {}", signUpPage.getPasswordValidationMessage());
    }

    @Then("the create account button should be disabled")
    public void theCreateAccountButtonShouldBeDisabled() {
        assertFalse("Create account button should be disabled",
                signUpPage.isCreateAccountButtonEnabled());
    }

    @Then("the user should remain on the sign up page")
    public void theUserShouldRemainOnTheSignUpPage() {
        assertTrue("User should remain on sign up page", signUpPage.isPageLoaded());
        String currentUrl = signUpPage.getCurrentUrl();
        assertTrue("URL should contain /login. Current URL: " + currentUrl, currentUrl.contains("/login"));
    }
}
