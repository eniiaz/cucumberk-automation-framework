package com.automation.pages.nomadtms;

import com.automation.pages.BasePage;
import com.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Login Page Object for Nomad TMS Fleet Management System
 * URL: https://nomadtms.up.railway.app
 */
public class NomadTmsLoginPage extends BasePage {

    // ==================== Page URL ====================
    private static final String PAGE_PATH = "";

    // ==================== Web Elements ====================
    @FindBy(xpath = "//button[text()='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//input[@placeholder='Enter your email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@placeholder='Enter your password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[text()='Sign In' and not(@role='tab')]")
    private WebElement signInButton;

    @FindBy(xpath = "//button[text()='← Back to Home']")
    private WebElement backToHomeButton;

    @FindBy(xpath = "//button[@role='tab' and text()='Sign In']")
    private WebElement signInTab;

    @FindBy(xpath = "//button[@role='tab' and text()='Sign Up']")
    private WebElement signUpTab;

    // Error message locators
    private By errorMessageLocator = By.xpath("//div[contains(@class, 'error')] | //div[contains(@role, 'alert')] | //p[contains(@class, 'error')] | //span[contains(@class, 'error')]");
    private By toastErrorLocator = By.xpath("//div[contains(@class, 'toast')] | //div[contains(@class, 'notification')]");

    // ==================== Page Actions ====================

    /**
     * Navigate to Nomad TMS login page
     */
    public void navigateToLoginPage() {
        String url = ConfigReader.getNomadTmsBaseUrl() + PAGE_PATH;
        navigateTo(url);
        logger.info("Navigated to Nomad TMS home page: {}", url);
        
        // Click the Login button on the home page to show login form
        try {
            Thread.sleep(1000);
            waitForClickability(loginButton);
            click(loginButton);
            logger.info("Clicked Login button to show login form");
            Thread.sleep(500);
        } catch (Exception e) {
            logger.debug("Login form might already be visible: {}", e.getMessage());
        }
    }

    /**
     * Enter email address
     * @param email email to enter
     */
    public void enterEmail(String email) {
        waitForVisibility(emailInput);
        sendKeys(emailInput, email);
        logger.debug("Entered email: {}", email);
    }

    /**
     * Enter password
     * @param password password to enter
     */
    public void enterPassword(String password) {
        waitForVisibility(passwordInput);
        sendKeys(passwordInput, password);
        logger.debug("Entered password");
    }

    /**
     * Click sign in button
     */
    public void clickSignInButton() {
        waitForClickability(signInButton);
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
     * Click Sign In tab
     */
    public void clickSignInTab() {
        waitForClickability(signInTab);
        click(signInTab);
        logger.info("Clicked Sign In tab");
    }

    /**
     * Click Sign Up tab
     */
    public void clickSignUpTab() {
        waitForClickability(signUpTab);
        click(signUpTab);
        logger.info("Clicked Sign Up tab");
    }

    /**
     * Click back to home button
     */
    public void clickBackToHome() {
        waitForClickability(backToHomeButton);
        click(backToHomeButton);
        logger.info("Clicked back to home button");
    }

    // ==================== Page Verifications ====================

    /**
     * Check if error message is displayed
     * @return true if error message is visible
     */
    public boolean isErrorMessageDisplayed() {
        try {
            Thread.sleep(1000); // Wait for potential error to appear
            return isDisplayed(findElement(errorMessageLocator)) || isDisplayed(findElement(toastErrorLocator));
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
            try {
                return getText(findElement(toastErrorLocator));
            } catch (Exception ex) {
                return "";
            }
        }
    }

    /**
     * Check if sign in button is enabled
     * @return true if enabled
     */
    public boolean isSignInButtonEnabled() {
        try {
            return isEnabled(signInButton);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if Sign In tab is visible
     * @return true if visible
     */
    public boolean isSignInTabVisible() {
        try {
            return isDisplayed(signInTab);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if Sign Up tab is visible
     * @return true if visible
     */
    public boolean isSignUpTabVisible() {
        try {
            return isDisplayed(signUpTab);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if Sign In tab is selected/active
     * @return true if selected
     */
    public boolean isSignInTabSelected() {
        try {
            String ariaSelected = signInTab.getAttribute("aria-selected");
            return "true".equals(ariaSelected);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if email input is visible (sign in form is displayed)
     * @return true if sign in form is visible
     */
    public boolean isSignInFormVisible() {
        try {
            return isDisplayed(emailInput) && isDisplayed(passwordInput);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isPageLoaded() {
        try {
            waitForVisibility(emailInput);
            waitForVisibility(passwordInput);
            waitForVisibility(signInButton);
            return true;
        } catch (Exception e) {
            logger.error("Nomad TMS login page not loaded properly: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if currently on login page by URL
     * @return true if on login page
     */
    public boolean isOnLoginPage() {
        String currentUrl = getCurrentUrl();
        return currentUrl.contains("nomadtms.up.railway.app");
    }
}
