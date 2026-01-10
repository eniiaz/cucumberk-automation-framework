package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Customers Page Object for MKF CRM Car Rental Management System
 * Handles customer listing and verification
 */
public class CustomersPage extends BasePage {

    // ==================== Web Elements ====================

    @FindBy(xpath = "//h1[contains(text(), 'Customers')]")
    private WebElement customersTitle;

    @FindBy(xpath = "//button[contains(text(), 'Add Customer')]")
    private WebElement addCustomerButton;

    @FindBy(xpath = "//input[@placeholder='Search' or contains(@placeholder, 'Search')]")
    private WebElement searchInput;

    @FindBy(xpath = "//table")
    private WebElement customersTable;

    @FindBy(xpath = "//table//tbody//tr")
    private List<WebElement> customerRows;

    // Dynamic locators
    private By customerRowByNameLocator(String customerName) {
        return By.xpath("//table//tbody//tr[contains(., '" + customerName + "')]");
    }

    private By customerCellByNameLocator(String customerName) {
        return By.xpath("//table//tbody//tr//td[contains(., '" + customerName + "')]");
    }

    // ==================== Page Actions ====================

    /**
     * Search for a customer by name
     * @param searchTerm the search term
     */
    public void searchCustomer(String searchTerm) {
        sendKeys(searchInput, searchTerm);
        logger.info("Searched for customer: {}", searchTerm);
        // Wait for search results to load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Check if a customer is visible in the customers table
     * @param firstName customer first name
     * @param lastName customer last name
     * @return true if customer is found in the table
     */
    public boolean isCustomerVisible(String firstName, String lastName) {
        try {
            String fullName = firstName + " " + lastName;
            WebElement customerCell = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(customerCellByNameLocator(fullName))
            );
            boolean isVisible = customerCell.isDisplayed();
            logger.info("Customer '{}' visible in table: {}", fullName, isVisible);
            return isVisible;
        } catch (Exception e) {
            logger.error("Customer not found in table: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if a customer with specific full name is visible
     * @param fullName full customer name (e.g., "John Doe")
     * @return true if customer is found
     */
    public boolean isCustomerVisibleByFullName(String fullName) {
        try {
            WebElement customerCell = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(customerCellByNameLocator(fullName))
            );
            boolean isVisible = customerCell.isDisplayed();
            logger.info("Customer '{}' visible in table: {}", fullName, isVisible);
            return isVisible;
        } catch (Exception e) {
            logger.error("Customer '{}' not found in table: {}", fullName, e.getMessage());
            return false;
        }
    }

    /**
     * Click on a customer row to view details
     * @param customerName customer name to click
     */
    public void clickOnCustomer(String customerName) {
        WebElement customerRow = getWait().until(
            ExpectedConditions.elementToBeClickable(customerRowByNameLocator(customerName))
        );
        click(customerRow);
        logger.info("Clicked on customer: {}", customerName);
    }

    /**
     * Get the total number of customers displayed
     * @return number of customer rows
     */
    public int getCustomerCount() {
        return customerRows.size();
    }

    /**
     * Click Add Customer button
     */
    public void clickAddCustomer() {
        click(addCustomerButton);
        logger.info("Clicked Add Customer button");
    }

    @Override
    public boolean isPageLoaded() {
        try {
            waitForVisibility(customersTitle);
            waitForVisibility(customersTable);
            return isDisplayed(customersTitle) && isDisplayed(customersTable);
        } catch (Exception e) {
            logger.error("Customers page not loaded properly: {}", e.getMessage());
            return false;
        }
    }
}
