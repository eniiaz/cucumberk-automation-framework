package com.automation.pages.nomadtms;

import com.automation.pages.BasePage;
import com.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Sign Up Page Object for Nomad TMS Fleet Management System
 * URL: https://nomadtms.up.railway.app
 */
public class NomadTmsSignUpPage extends BasePage {

    // ==================== Page URL ====================
    private static final String PAGE_PATH = "";

    // ==================== Web Elements ====================
    @FindBy(xpath = "//button[text()='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//input[@placeholder='First name']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@placeholder='Last name']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@placeholder='Your organization name']")
    private WebElement organizationNameInput;

    @FindBy(xpath = "//input[@placeholder='Enter your email']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@placeholder='Create a password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[text()='Create Account']")
    private WebElement createAccountButton;

    @FindBy(xpath = "//button[@role='tab' and text()='Sign In']")
    private WebElement signInTab;

    @FindBy(xpath = "//button[@role='tab' and text()='Sign Up']")
    private WebElement signUpTab;

    @FindBy(xpath = "//button[text()='← Back to Home']")
    private WebElement backToHomeButton;

    // Error/Validation message locators
    private By errorMessageLocator = By.xpath("//div[contains(@class, 'error')] | //div[contains(@role, 'alert')] | //p[contains(@class, 'error')] | //span[contains(@class, 'error')]");
    private By toastMessageLocator = By.xpath("//div[contains(@class, 'toast')] | //div[contains(@class, 'notification')]");
    private By validationMessageLocator = By.xpath("//p[contains(@class, 'text-red')] | //span[contains(@class, 'text-red')] | //div[contains(@class, 'validation')]");

    // ==================== Page Actions ====================

    /**
     * Navigate to Nomad TMS signup page
     */
    public void navigateToSignUpPage() {
        String url = ConfigReader.getNomadTmsBaseUrl() + PAGE_PATH;
        navigateTo(url);
        logger.info("Navigated to Nomad TMS home page: {}", url);
        
        try {
            Thread.sleep(1000);
            // Click Login button first to open auth modal
            waitForClickability(loginButton);
            click(loginButton);
            logger.info("Clicked Login button");
            Thread.sleep(500);
            
            // Click Sign Up tab to switch to signup form
            waitForClickability(signUpTab);
            click(signUpTab);
            logger.info("Clicked Sign Up tab to show signup form");
            Thread.sleep(500);
        } catch (Exception e) {
            logger.debug("Signup form might already be visible: {}", e.getMessage());
        }
    }

    /**
     * Enter first name
     * @param firstName first name to enter
     */
    public void enterFirstName(String firstName) {
        waitForVisibility(firstNameInput);
        sendKeys(firstNameInput, firstName);
        logger.debug("Entered first name: {}", firstName);
    }

    /**
     * Enter last name
     * @param lastName last name to enter
     */
    public void enterLastName(String lastName) {
        waitForVisibility(lastNameInput);
        sendKeys(lastNameInput, lastName);
        logger.debug("Entered last name: {}", lastName);
    }

    /**
     * Enter organization name
     * @param orgName organization name to enter
     */
    public void enterOrganizationName(String orgName) {
        waitForVisibility(organizationNameInput);
        sendKeys(organizationNameInput, orgName);
        logger.debug("Entered organization name: {}", orgName);
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
     * Click create account button
     */
    public void clickCreateAccountButton() {
        waitForClickability(createAccountButton);
        click(createAccountButton);
        logger.info("Clicked create account button");
    }

    /**
     * Perform complete sign up
     * @param firstName first name
     * @param lastName last name
     * @param orgName organization name
     * @param email email
     * @param password password
     */
    public void signUp(String firstName, String lastName, String orgName, String email, String password) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterOrganizationName(orgName);
        enterEmail(email);
        enterPassword(password);
        clickCreateAccountButton();
        logger.info("Sign up attempted with email: {}", email);
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
            Thread.sleep(1000);
            return isDisplayed(findElement(errorMessageLocator)) || isDisplayed(findElement(toastMessageLocator));
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
                return getText(findElement(toastMessageLocator));
            } catch (Exception ex) {
                return "";
            }
        }
    }

    /**
     * Check if validation message is displayed
     * @return true if validation message is visible
     */
    public boolean isValidationMessageDisplayed() {
        try {
            return isDisplayed(findElement(validationMessageLocator));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get validation message text
     * @return validation message text
     */
    public String getValidationMessageText() {
        try {
            return getText(findElement(validationMessageLocator));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Check if create account button is enabled
     * @return true if enabled
     */
    public boolean isCreateAccountButtonEnabled() {
        try {
            return isEnabled(createAccountButton);
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
     * Check if Sign Up tab is selected/active
     * @return true if selected
     */
    public boolean isSignUpTabSelected() {
        try {
            String ariaSelected = signUpTab.getAttribute("aria-selected");
            return "true".equals(ariaSelected);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if signup form is visible (first name input is shown)
     * @return true if signup form is visible
     */
    public boolean isSignUpFormVisible() {
        try {
            return isDisplayed(firstNameInput) && isDisplayed(lastNameInput);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isPageLoaded() {
        try {
            waitForVisibility(firstNameInput);
            waitForVisibility(lastNameInput);
            waitForVisibility(organizationNameInput);
            waitForVisibility(emailInput);
            waitForVisibility(passwordInput);
            waitForVisibility(createAccountButton);
            return true;
        } catch (Exception e) {
            logger.error("Nomad TMS signup page not loaded properly: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if currently on signup page by URL
     * @return true if on signup page
     */
    public boolean isOnSignUpPage() {
        String currentUrl = getCurrentUrl();
        return currentUrl.contains("nomadtms.up.railway.app");
    }
}
