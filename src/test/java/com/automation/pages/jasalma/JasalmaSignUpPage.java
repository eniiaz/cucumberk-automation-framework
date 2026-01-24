package com.automation.pages.jasalma;

import com.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Sign Up Page Object for Jasalma AI Learning Platform
 * URL: https://www.jasalma.ai/sign-up
 */
public class JasalmaSignUpPage extends BasePage {

    // ==================== Page URL ====================
    private static final String BASE_URL = "https://www.jasalma.ai";
    private static final String SIGN_UP_PATH = "/sign-up";

    // ==================== Web Elements ====================
    @FindBy(xpath = "//input[@name='firstName'] | //input[contains(@placeholder, 'First name')] | //label[contains(text(), 'First name')]/following-sibling::input | //div[contains(text(), 'First name')]/following::input[1]")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@name='lastName'] | //input[contains(@placeholder, 'Last name')] | //label[contains(text(), 'Last name')]/following-sibling::input | //div[contains(text(), 'Last name')]/following::input[1]")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@placeholder='Enter your email address']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@placeholder='Enter your password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[contains(., 'Continue')]")
    private WebElement continueButton;

    @FindBy(xpath = "//button[contains(., 'Continue with Google')]")
    private WebElement googleSignUpButton;

    @FindBy(xpath = "//a[contains(@href, '/sign-in')]")
    private WebElement signInLink;

    // Locators for dynamic elements
    private By successMessageLocator = By.xpath("//div[contains(@class, 'success')] | //div[contains(text(), 'verification')] | //div[contains(text(), 'Verify')] | //h1[contains(text(), 'Verify')]");
    private By errorMessageLocator = By.xpath("//div[contains(@class, 'error')] | //div[contains(@role, 'alert')] | //p[contains(@class, 'error')]");

    // ==================== Page Actions ====================

    /**
     * Navigate to Jasalma sign-up page
     */
    public void navigateToSignUpPage() {
        String url = BASE_URL + SIGN_UP_PATH;
        navigateTo(url);
        logger.info("Navigated to Jasalma sign-up page: {}", url);
        waitForPageToLoad();
    }

    /**
     * Wait for sign-up form to load
     */
    private void waitForPageToLoad() {
        try {
            Thread.sleep(2000);
            waitForVisibility(emailInput);
            waitForVisibility(passwordInput);
        } catch (Exception e) {
            logger.debug("Waiting for sign-up form: {}", e.getMessage());
        }
    }

    /**
     * Enter first name
     * @param firstName first name to enter
     */
    public void enterFirstName(String firstName) {
        try {
            // Try to find first name input using different strategies
            WebElement input = getDriver().findElement(By.xpath(
                "//div[contains(text(), 'First name')]/ancestor::div[1]//input | " +
                "//label[contains(text(), 'First name')]//following::input[1] | " +
                "(//input[@type='text'])[1]"
            ));
            waitForVisibility(input);
            sendKeys(input, firstName);
            logger.debug("Entered first name: {}", firstName);
        } catch (Exception e) {
            // Fallback: use the @FindBy element
            waitForVisibility(firstNameInput);
            sendKeys(firstNameInput, firstName);
            logger.debug("Entered first name (fallback): {}", firstName);
        }
    }

    /**
     * Enter last name
     * @param lastName last name to enter
     */
    public void enterLastName(String lastName) {
        try {
            // Try to find last name input using different strategies
            WebElement input = getDriver().findElement(By.xpath(
                "//div[contains(text(), 'Last name')]/ancestor::div[1]//input | " +
                "//label[contains(text(), 'Last name')]//following::input[1] | " +
                "(//input[@type='text'])[2]"
            ));
            waitForVisibility(input);
            sendKeys(input, lastName);
            logger.debug("Entered last name: {}", lastName);
        } catch (Exception e) {
            // Fallback: use the @FindBy element
            waitForVisibility(lastNameInput);
            sendKeys(lastNameInput, lastName);
            logger.debug("Entered last name (fallback): {}", lastName);
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
     * Click continue button
     */
    public void clickContinueButton() {
        waitForClickability(continueButton);
        click(continueButton);
        logger.info("Clicked continue button");
    }

    /**
     * Perform sign up with credentials
     * @param firstName first name
     * @param lastName last name
     * @param email email
     * @param password password
     */
    public void signUp(String firstName, String lastName, String email, String password) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        clickContinueButton();
        logger.info("Sign up attempted with email: {}", email);
    }

    /**
     * Click sign in link
     */
    public void clickSignInLink() {
        waitForClickability(signInLink);
        click(signInLink);
        logger.info("Clicked sign in link");
    }

    // ==================== Page Verifications ====================

    /**
     * Check if sign-up form is visible
     * @return true if sign-up form is visible
     */
    public boolean isSignUpFormVisible() {
        try {
            return isDisplayed(emailInput) && isDisplayed(passwordInput);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if currently on sign-up page
     * @return true if on sign-up page
     */
    public boolean isOnSignUpPage() {
        String currentUrl = getCurrentUrl();
        return currentUrl.contains("jasalma.ai/sign-up");
    }

    /**
     * Check if success or verification message is displayed
     * @return true if success/verification message is visible
     */
    public boolean isSuccessOrVerificationMessageDisplayed() {
        try {
            Thread.sleep(2000);
            return isDisplayed(findElement(successMessageLocator));
        } catch (Exception e) {
            // Also check if we're redirected away from sign-up page (successful sign-up)
            String currentUrl = getCurrentUrl();
            return !currentUrl.contains("/sign-up");
        }
    }

    /**
     * Check if error message is displayed
     * @return true if error is visible
     */
    public boolean isErrorMessageDisplayed() {
        try {
            Thread.sleep(1000);
            return isDisplayed(findElement(errorMessageLocator));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isPageLoaded() {
        try {
            Thread.sleep(2000);
            waitForVisibility(emailInput);
            waitForVisibility(passwordInput);
            waitForVisibility(continueButton);
            return true;
        } catch (Exception e) {
            logger.error("Jasalma sign-up page not loaded properly: {}", e.getMessage());
            return false;
        }
    }
}
