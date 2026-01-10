package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Reservation Page Object for MKF CRM Car Rental Management System
 * Handles reservation creation and customer management from reservations
 */
public class ReservationPage extends BasePage {

    // ==================== Web Elements ====================

    // Page header elements
    @FindBy(xpath = "//h1[contains(text(), 'Reservations')]")
    private WebElement reservationsTitle;

    @FindBy(xpath = "//button[contains(text(), 'New Reservation')]")
    private WebElement newReservationButton;

    @FindBy(xpath = "//button[contains(text(), 'All Reservations')]")
    private WebElement allReservationsButton;

    // Vehicle selection
    @FindBy(xpath = "(//button[contains(text(), 'Select')])[1]")
    private WebElement firstVehicleSelectButton;

    // Reservation form modal
    @FindBy(xpath = "//h2[contains(text(), 'New Reservation')]")
    private WebElement reservationFormTitle;

    @FindBy(xpath = "//h2[contains(text(), 'New Reservation')]/following-sibling::button")
    private WebElement closeReservationFormButton;

    // Customer selection section
    @FindBy(xpath = "//button[contains(text(), 'Add Customer')]")
    private WebElement addCustomerButton;

    // New Customer form elements
    @FindBy(xpath = "//h4[contains(text(), 'New Customer')]")
    private WebElement newCustomerFormTitle;

    @FindBy(xpath = "//div[contains(text(), 'First Name')]/following-sibling::input | //input[@placeholder='John']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//div[contains(text(), 'Last Name')]/following-sibling::input | //input[@placeholder='Doe']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//div[contains(text(), 'Phone')]/following-sibling::input | //input[contains(@placeholder, '555')]")
    private WebElement phoneInput;

    @FindBy(xpath = "//div[contains(text(), 'Email')]/following-sibling::input | //input[contains(@placeholder, 'example.com')]")
    private WebElement emailInputInCustomerForm;

    @FindBy(xpath = "//h4[contains(text(), 'New Customer')]/ancestor::div[contains(@class, 'flex')]//button[contains(text(), 'Create')]")
    private WebElement createCustomerButton;

    @FindBy(xpath = "//h4[contains(text(), 'New Customer')]/ancestor::div[contains(@class, 'flex')]//button[contains(text(), 'Cancel')]")
    private WebElement cancelCustomerButton;

    @FindBy(xpath = "//h4[contains(text(), 'New Customer')]/following-sibling::button")
    private WebElement closeCustomerFormButton;

    // Reservation form buttons
    @FindBy(xpath = "//div[contains(@class, 'flex')]//button[contains(text(), 'Save')]")
    private WebElement saveReservationButton;

    @FindBy(xpath = "//div[contains(@class, 'flex')]//button[contains(text(), 'Cancel')]")
    private WebElement cancelReservationButton;

    // Dynamic locators
    private By customerInListLocator(String customerName) {
        return By.xpath("//button[contains(., '" + customerName + "')]");
    }

    // ==================== Page Actions ====================

    /**
     * Click New Reservation button
     */
    public void clickNewReservation() {
        click(newReservationButton);
        logger.info("Clicked New Reservation button");
    }

    /**
     * Select the first available vehicle
     */
    public void selectFirstAvailableVehicle() {
        waitForVisibility(firstVehicleSelectButton);
        click(firstVehicleSelectButton);
        logger.info("Selected first available vehicle");
    }

    /**
     * Click Add Customer button
     */
    public void clickAddCustomer() {
        waitForVisibility(addCustomerButton);
        click(addCustomerButton);
        logger.info("Clicked Add Customer button");
    }

    /**
     * Enter customer information in the new customer form
     * @param firstName customer first name
     * @param lastName customer last name
     * @param phone customer phone number
     * @param email customer email address
     */
    public void enterCustomerInformation(String firstName, String lastName, String phone, String email) {
        waitForVisibility(newCustomerFormTitle);

        sendKeys(firstNameInput, firstName);
        logger.debug("Entered first name: {}", firstName);

        sendKeys(lastNameInput, lastName);
        logger.debug("Entered last name: {}", lastName);

        sendKeys(phoneInput, phone);
        logger.debug("Entered phone: {}", phone);

        sendKeys(emailInputInCustomerForm, email);
        logger.debug("Entered email: {}", email);

        logger.info("Entered customer information: {} {} - {} - {}", firstName, lastName, phone, email);
    }

    /**
     * Click Create button to create the customer
     */
    public void clickCreateCustomer() {
        click(createCustomerButton);
        logger.info("Clicked Create customer button");
    }

    /**
     * Check if customer was created successfully by verifying it appears in the customer list
     * @param firstName customer first name
     * @param lastName customer last name
     * @return true if customer is visible in the list
     */
    public boolean isCustomerCreatedSuccessfully(String firstName, String lastName) {
        try {
            String fullName = firstName + " " + lastName;
            WebElement customerInList = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(customerInListLocator(fullName))
            );
            boolean isDisplayed = customerInList.isDisplayed();
            logger.info("Customer {} found in list: {}", fullName, isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Customer not found in list: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Close the reservation form
     */
    public void closeReservationForm() {
        try {
            click(closeReservationFormButton);
            logger.info("Closed reservation form");
        } catch (Exception e) {
            // Try clicking cancel button as alternative
            click(cancelReservationButton);
            logger.info("Closed reservation form using Cancel button");
        }
    }

    /**
     * Check if New Customer form is displayed
     * @return true if form is visible
     */
    public boolean isNewCustomerFormDisplayed() {
        try {
            return isDisplayed(newCustomerFormTitle);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if reservation form is displayed
     * @return true if form is visible
     */
    public boolean isReservationFormDisplayed() {
        try {
            return isDisplayed(reservationFormTitle);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isPageLoaded() {
        try {
            waitForVisibility(reservationsTitle);
            return isDisplayed(reservationsTitle) && isDisplayed(newReservationButton);
        } catch (Exception e) {
            logger.error("Reservation page not loaded properly: {}", e.getMessage());
            return false;
        }
    }
}
