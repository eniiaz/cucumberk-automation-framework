package com.automation.stepdefinitions;

import com.automation.pages.jasalma.JasalmaDashboardPage;
import com.automation.pages.jasalma.JasalmaLoginPage;
import com.automation.pages.jasalma.JasalmaSignUpPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.*;

/**
 * Authentication Step Definitions for Jasalma AI Learning Platform
 * URL: https://www.jasalma.ai
 */
public class JasalmaAuthenticationSteps {
    private static final Logger logger = LogManager.getLogger(JasalmaAuthenticationSteps.class);

    private final JasalmaLoginPage loginPage = new JasalmaLoginPage();
    private final JasalmaSignUpPage signUpPage = new JasalmaSignUpPage();
    private final JasalmaDashboardPage dashboardPage = new JasalmaDashboardPage();

    // ==================== Given Steps ====================

    @Given("the Jasalma user is on the sign in page")
    public void theJasalmaUserIsOnTheSignInPage() {
        loginPage.navigateToSignInPage();
        assertTrue("Jasalma sign-in page should be loaded", loginPage.isPageLoaded());
        logger.info("User is on Jasalma sign-in page");
    }

    @Given("the Jasalma user is on the sign up page")
    public void theJasalmaUserIsOnTheSignUpPage() {
        signUpPage.navigateToSignUpPage();
        assertTrue("Jasalma sign-up page should be loaded", signUpPage.isPageLoaded());
        logger.info("User is on Jasalma sign-up page");
    }

    // ==================== When Steps ====================

    @When("the Jasalma user enters email {string}")
    public void theJasalmaUserEntersEmail(String email) {
        // Check which page we're on and use appropriate page object
        String currentUrl = loginPage.getCurrentUrl();
        if (currentUrl.contains("/sign-up")) {
            signUpPage.enterEmail(email);
        } else {
            loginPage.enterEmail(email);
        }
        logger.info("Entered email: {}", email);
    }

    @When("the Jasalma user enters password {string}")
    public void theJasalmaUserEntersPassword(String password) {
        // Check which page we're on and use appropriate page object
        String currentUrl = loginPage.getCurrentUrl();
        if (currentUrl.contains("/sign-up")) {
            signUpPage.enterPassword(password);
        } else {
            // For sign-in, password is entered on Step 2 (factor-one page)
            // First click continue to get to Step 2 if we're still on Step 1
            if (!loginPage.isOnPasswordStep()) {
                loginPage.clickContinueButton();
                // Wait for Step 2 page to load
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            loginPage.enterPassword(password);
        }
        logger.info("Entered password");
    }

    @When("the Jasalma user enters first name {string}")
    public void theJasalmaUserEntersFirstName(String firstName) {
        signUpPage.enterFirstName(firstName);
        logger.info("Entered first name: {}", firstName);
    }

    @When("the Jasalma user enters last name {string}")
    public void theJasalmaUserEntersLastName(String lastName) {
        signUpPage.enterLastName(lastName);
        logger.info("Entered last name: {}", lastName);
    }

    @When("the Jasalma user clicks the continue button")
    public void theJasalmaUserClicksTheContinueButton() {
        // Check which page we're on and use appropriate page object
        String currentUrl = loginPage.getCurrentUrl();
        if (currentUrl.contains("/sign-up")) {
            signUpPage.clickContinueButton();
        } else {
            loginPage.clickContinueButton();
        }
        logger.info("Clicked continue button");
    }

    @When("the Jasalma user navigates to the dashboard")
    public void theJasalmaUserNavigatesToTheDashboard() {
        dashboardPage.navigateToDashboard();
        logger.info("Navigated to dashboard");
    }

    // ==================== Then Steps ====================

    @Then("the Jasalma user should be logged in successfully")
    public void theJasalmaUserShouldBeLoggedInSuccessfully() {
        try {
            Thread.sleep(3000); // Wait for authentication to complete
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String currentUrl = loginPage.getCurrentUrl();
        // User should NOT be on sign-in page anymore after successful login
        assertFalse("User should not be on sign-in page after login", 
                currentUrl.contains("/sign-in"));
        logger.info("User logged in successfully. Current URL: {}", currentUrl);
    }

    @Then("the Jasalma user should remain on the dashboard page")
    public void theJasalmaUserShouldRemainOnTheDashboardPage() {
        try {
            Thread.sleep(2000); // Wait for any redirects
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        assertTrue("User should be on dashboard page", 
                dashboardPage.isStillOnDashboard());
        logger.info("User is on dashboard page. Current URL: {}", dashboardPage.getCurrentPageUrl());
    }

    @Then("the Jasalma user should not be redirected to the main page")
    public void theJasalmaUserShouldNotBeRedirectedToTheMainPage() {
        String currentUrl = dashboardPage.getCurrentPageUrl();
        
        // Check that user is NOT on the main/landing page
        boolean notOnMainPage = !currentUrl.equals("https://www.jasalma.ai/") && 
                                !currentUrl.equals("https://www.jasalma.ai");
        
        assertTrue("User should NOT be redirected to main page. Current URL: " + currentUrl, 
                notOnMainPage);
        logger.info("User was NOT redirected to main page. Current URL: {}", currentUrl);
    }

    @Then("the Jasalma user should see a verification or success message")
    public void theJasalmaUserShouldSeeAVerificationOrSuccessMessage() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // For sign up, either we see a verification message OR we're redirected away from sign-up
        boolean success = signUpPage.isSuccessOrVerificationMessageDisplayed();
        assertTrue("User should see verification/success message or be redirected after sign up", 
                success);
        logger.info("Sign up was processed (verification page shown or redirected)");
    }

    @Then("the Jasalma user should see the sign in form")
    public void theJasalmaUserShouldSeeTheSignInForm() {
        assertTrue("Sign-in form should be visible", loginPage.isSignInFormVisible());
        logger.info("Sign-in form is visible");
    }

    @Then("the Jasalma user should see the sign up form")
    public void theJasalmaUserShouldSeeTheSignUpForm() {
        assertTrue("Sign-up form should be visible", signUpPage.isSignUpFormVisible());
        logger.info("Sign-up form is visible");
    }

    @Then("the Jasalma user should remain on the sign in page")
    public void theJasalmaUserShouldRemainOnTheSignInPage() {
        assertTrue("User should remain on sign-in page", loginPage.isOnSignInPage());
        logger.info("User remained on sign-in page");
    }
}
