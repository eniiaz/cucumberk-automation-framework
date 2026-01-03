package com.automation.pages;

import com.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Sign Up Page Object for MKF CRM Car Rental Management System
 */
public class SignUpPage extends BasePage {

    // ==================== Page URL ====================
    private static final String PAGE_PATH = "/login";

    // ==================== Web Elements ====================
    @FindBy(xpath = "//input[contains(@placeholder, 'Full Name') or contains(@placeholder, 'Name')]")
    private WebElement fullNameInput;

    @FindBy(xpath = "//input[@type='email' or contains(@placeholder, 'email')]")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[contains(text(), 'Create Account')]")
    private WebElement createAccountButton;

    @FindBy(xpath = "//button[contains(text(), 'Sign in')]")
    private WebElement signInLink;

    // Error message locator
    private By errorMessageLocator = By.xpath("//div[contains(@role, 'alert')] | //div[contains(@class, 'error')] | //p[contains(@class, 'error')]");

    // Password validation message
    private By passwordValidationMessage = By.xpath("//p[contains(text(), 'Min 6 characters')]");

    // ==================== Page Actions ====================

    /**
     * Navigate to sign up page
     */
    public void navigateToSignUpPage() {
        String url = ConfigReader.getBaseUrl() + PAGE_PATH;
        navigateTo(url);
        // Click sign up link to toggle to sign up form
        try {
            // Wait for page to load, then click sign up button
            Thread.sleep(1000);
            WebElement signUpButton = findElement(By.xpath("//button[contains(text(), 'Sign up')]"));
            click(signUpButton);
            logger.info("Clicked sign up button to show sign up form");
        } catch (Exception e) {
            logger.debug("Sign up form might already be visible or button not found: {}", e.getMessage());
        }
        logger.info("Navigated to sign up page");
    }

    /**
     * Enter full name
     * @param fullName full name to enter
     */
    public void enterFullName(String fullName) {
        sendKeys(fullNameInput, fullName);
        logger.debug("Entered full name: {}", fullName);
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
     * Click create account button
     */
    public void clickCreateAccountButton() {
        click(createAccountButton);
        logger.info("Clicked create account button");
    }

    /**
     * Perform sign up with credentials
     * @param fullName full name
     * @param email email
     * @param password password
     */
    public void signUp(String fullName, String email, String password) {
        enterFullName(fullName);
        enterEmail(email);
        enterPassword(password);
        clickCreateAccountButton();
        logger.info("Sign up attempted with email: {}", email);
    }

    /**
     * Click sign in link
     */
    public void clickSignInLink() {
        click(signInLink);
        logger.info("Clicked sign in link");
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
     * Check if password validation message is displayed
     * @return true if validation message is visible
     */
    public boolean isPasswordValidationMessageDisplayed() {
        try {
            return isDisplayed(findElement(passwordValidationMessage));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get password validation message text
     * @return validation message text
     */
    public String getPasswordValidationMessage() {
        try {
            return getText(findElement(passwordValidationMessage));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Check if create account button is enabled
     * @return true if enabled
     */
    public boolean isCreateAccountButtonEnabled() {
        return isEnabled(createAccountButton);
    }

    @Override
    public boolean isPageLoaded() {
        try {
            waitForVisibility(fullNameInput);
            waitForVisibility(emailInput);
            waitForVisibility(passwordInput);
            waitForVisibility(createAccountButton);
            return true;
        } catch (Exception e) {
            logger.error("Sign up page not loaded properly: {}", e.getMessage());
            return false;
        }
    }
}

