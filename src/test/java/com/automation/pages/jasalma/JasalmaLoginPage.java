package com.automation.pages.jasalma;

import com.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Login Page Object for Jasalma AI Learning Platform
 * URL: https://www.jasalma.ai/sign-in
 * 
 * Note: Jasalma uses Clerk authentication with a TWO-STEP login flow:
 * Step 1: Enter email → Click Continue
 * Step 2: Enter password on /sign-in/factor-one → Click Continue
 */
public class JasalmaLoginPage extends BasePage {

    // ==================== Page URL ====================
    private static final String BASE_URL = "https://www.jasalma.ai";
    private static final String SIGN_IN_PATH = "/sign-in";

    // ==================== Web Elements - Step 1 (Email) ====================
    @FindBy(xpath = "//input[@placeholder='Enter your email address']")
    private WebElement emailInput;

    @FindBy(xpath = "//button[.//text()[contains(., 'Continue')] and not(contains(., 'Google'))]")
    private WebElement continueButton;

    @FindBy(xpath = "//button[contains(., 'Continue with Google')]")
    private WebElement googleSignInButton;

    @FindBy(xpath = "//a[contains(@href, '/sign-up')]")
    private WebElement signUpLink;

    // ==================== Web Elements - Step 2 (Password) ====================
    // Password input appears on /sign-in/factor-one page
    private By passwordInputLocator = By.xpath("//input[@placeholder='Enter your password']");
    private By continueButtonStep2Locator = By.xpath("//button[.//text()[contains(., 'Continue')]]");

    // ==================== Page Actions ====================

    /**
     * Navigate to Jasalma sign-in page
     */
    public void navigateToSignInPage() {
        String url = BASE_URL + SIGN_IN_PATH;
        navigateTo(url);
        logger.info("Navigated to Jasalma sign-in page: {}", url);
        waitForPageToLoad();
    }

    /**
     * Wait for sign-in form to load
     */
    private void waitForPageToLoad() {
        try {
            Thread.sleep(2000);
            waitForVisibility(emailInput);
        } catch (Exception e) {
            logger.debug("Waiting for sign-in form: {}", e.getMessage());
        }
    }

    /**
     * Enter email address (Step 1)
     * @param email email to enter
     */
    public void enterEmail(String email) {
        waitForVisibility(emailInput);
        sendKeys(emailInput, email);
        logger.debug("Entered email: {}", email);
    }

    /**
     * Enter password (Step 2 - on factor-one page)
     * @param password password to enter
     */
    public void enterPassword(String password) {
        try {
            Thread.sleep(1000); // Wait for step 2 page to load
            WebElement passwordInput = waitForVisibility(passwordInputLocator);
            sendKeys(passwordInput, password);
            logger.debug("Entered password");
        } catch (Exception e) {
            logger.error("Failed to enter password: {}", e.getMessage());
        }
    }

    /**
     * Click continue button (works for both steps)
     */
    public void clickContinueButton() {
        try {
            // First try the main continue button (Step 1)
            if (isDisplayed(continueButton)) {
                waitForClickability(continueButton);
                click(continueButton);
            } else {
                // Try Step 2 continue button
                WebElement step2Button = waitForClickability(continueButtonStep2Locator);
                click(step2Button);
            }
            logger.info("Clicked continue button");
        } catch (Exception e) {
            // Fallback: find any continue button
            WebElement anyButton = waitForClickability(continueButtonStep2Locator);
            click(anyButton);
            logger.info("Clicked continue button (fallback)");
        }
    }

    /**
     * Perform full two-step login with credentials
     * @param email email
     * @param password password
     */
    public void login(String email, String password) {
        // Step 1: Enter email and click continue
        enterEmail(email);
        clickContinueButton();
        
        // Wait for step 2 to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Step 2: Enter password and click continue
        enterPassword(password);
        clickContinueButton();
        
        logger.info("Two-step login attempted with email: {}", email);
    }

    /**
     * Click sign up link
     */
    public void clickSignUpLink() {
        waitForClickability(signUpLink);
        click(signUpLink);
        logger.info("Clicked sign up link");
    }

    // ==================== Page Verifications ====================

    /**
     * Check if sign-in form is visible (email input visible)
     * @return true if sign-in form is visible
     */
    public boolean isSignInFormVisible() {
        try {
            return isDisplayed(emailInput);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if currently on sign-in page
     * @return true if on sign-in page
     */
    public boolean isOnSignInPage() {
        String currentUrl = getCurrentUrl();
        return currentUrl.contains("jasalma.ai/sign-in");
    }

    /**
     * Check if on password step (factor-one)
     * @return true if on password step
     */
    public boolean isOnPasswordStep() {
        String currentUrl = getCurrentUrl();
        return currentUrl.contains("/factor-one");
    }

    @Override
    public boolean isPageLoaded() {
        try {
            Thread.sleep(2000);
            waitForVisibility(emailInput);
            waitForVisibility(continueButton);
            return true;
        } catch (Exception e) {
            logger.error("Jasalma sign-in page not loaded properly: {}", e.getMessage());
            return false;
        }
    }
}
