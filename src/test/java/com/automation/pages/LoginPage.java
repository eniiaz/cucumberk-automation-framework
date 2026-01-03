package com.automation.pages;

import com.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Login Page Object for MKF CRM Car Rental Management System
 */
public class LoginPage extends BasePage {

    // ==================== Page URL ====================
    private static final String PAGE_PATH = "/login";

    // ==================== Web Elements ====================
    @FindBy(xpath = "//input[@type='email' or contains(@placeholder, 'email')]")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[contains(text(), 'Sign In')]")
    private WebElement signInButton;

    @FindBy(xpath = "//button[contains(text(), 'Sign up')]")
    private WebElement signUpLink;

    // Error message locator (will be found dynamically)
    private By errorMessageLocator = By.xpath("//div[contains(@role, 'alert')] | //div[contains(@class, 'error')] | //p[contains(@class, 'error')]");

    // ==================== Page Actions ====================

    /**
     * Navigate to login page
     */
    public void navigateToLoginPage() {
        String url = ConfigReader.getBaseUrl() + PAGE_PATH;
        navigateTo(url);
        logger.info("Navigated to login page");
    }

    /**
     * Enter email
     * @param email email to enter
     */
    public void enterEmail(String email) {
        sendKeys(emailInput, email);
        logger.debug("Entered email: {}", email);
    }

    /**
     * Enter password
     * @param password password to enter
     */
    public void enterPassword(String password) {
        sendKeys(passwordInput, password);
        logger.debug("Entered password");
    }

    /**
     * Click sign in button
     */
    public void clickSignInButton() {
        click(signInButton);
        logger.info("Clicked sign in button");
    }

    /**
     * Perform login with credentials
     * @param email email
     * @param password password
     */
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignInButton();
        logger.info("Login attempted with email: {}", email);
    }

    /**
     * Click sign up link
     */
    public void clickSignUpLink() {
        click(signUpLink);
        logger.info("Clicked sign up link");
    }

    // ==================== Page Verifications ====================

    /**
     * Check if error message is displayed
     * @return true if error message is visible
     */
    public boolean isErrorMessageDisplayed() {
        try {
            return isDisplayed(findElement(errorMessageLocator));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get error message text
     * @return error message text
     */
    public String getErrorMessageText() {
        try {
            return getText(findElement(errorMessageLocator));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Check if sign in button is enabled
     * @return true if enabled
     */
    public boolean isSignInButtonEnabled() {
        return isEnabled(signInButton);
    }

    @Override
    public boolean isPageLoaded() {
        try {
            waitForVisibility(emailInput);
            waitForVisibility(passwordInput);
            waitForVisibility(signInButton);
            return true;
        } catch (Exception e) {
            logger.error("Login page not loaded properly: {}", e.getMessage());
            return false;
        }
    }
}
