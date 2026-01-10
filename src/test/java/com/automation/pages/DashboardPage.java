package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Dashboard Page Object for MKF CRM Car Rental Management System
 */
public class DashboardPage extends BasePage {

    // ==================== Web Elements ====================
    @FindBy(xpath = "//h1[contains(text(), 'Dashboard')] | //h2[contains(text(), 'Dashboard')]")
    private WebElement dashboardTitle;

    // Sidebar navigation elements
    @FindBy(xpath = "//a[contains(text(), 'Dashboard')] | //button[contains(text(), 'Dashboard')]")
    private WebElement dashboardNavLink;

    @FindBy(xpath = "//a[contains(text(), 'Calendar')] | //button[contains(text(), 'Calendar')]")
    private WebElement calendarNavLink;

    @FindBy(xpath = "//a[contains(text(), 'Reservations')] | //nav//a[contains(@href, '/reservations')]")
    private WebElement reservationsNavLink;

    @FindBy(xpath = "//a[contains(text(), 'Customers')] | //nav//a[contains(@href, '/customers')]")
    private WebElement customersNavLink;

    @FindBy(xpath = "//a[contains(text(), 'Vehicles')] | //nav//a[contains(@href, '/vehicles')]")
    private WebElement vehiclesNavLink;

    @FindBy(xpath = "//button[contains(text(), 'Logout')]")
    private WebElement logoutButton;

    // ==================== Page Verifications ====================

    /**
     * Check if dashboard title is displayed
     * @return true if displayed
     */
    public boolean isDashboardTitleDisplayed() {
        try {
            return isDisplayed(dashboardTitle);
        } catch (Exception e) {
            // Try alternative locator
            try {
                return isDisplayed(findElement(By.xpath("//*[contains(text(), 'Dashboard')]")));
            } catch (Exception ex) {
                return false;
            }
        }
    }

    /**
     * Check if navigation menu is displayed
     * @return true if displayed
     */
    public boolean isNavigationMenuDisplayed() {
        try {
            return isDisplayed(dashboardNavLink) || isDisplayed(calendarNavLink);
        } catch (Exception e) {
            return false;
        }
    }

    // ==================== Navigation Methods ====================

    /**
     * Navigate to Reservations page
     */
    public void navigateToReservations() {
        waitForClickability(reservationsNavLink);
        click(reservationsNavLink);
        logger.info("Navigated to Reservations page");
    }

    /**
     * Navigate to Customers page
     */
    public void navigateToCustomers() {
        waitForClickability(customersNavLink);
        click(customersNavLink);
        logger.info("Navigated to Customers page");
    }

    /**
     * Navigate to Vehicles page
     */
    public void navigateToVehicles() {
        waitForClickability(vehiclesNavLink);
        click(vehiclesNavLink);
        logger.info("Navigated to Vehicles page");
    }

    /**
     * Click logout button
     */
    public void logout() {
        click(logoutButton);
        logger.info("Clicked logout button");
    }

    @Override
    public boolean isPageLoaded() {
        try {
            // Check if we're on dashboard by URL or page elements
            String currentUrl = getCurrentUrl();
            if (currentUrl.contains("/dashboard") || currentUrl.contains("/home") || 
                !currentUrl.contains("/login")) {
                // Try to verify with page elements
                return isDashboardTitleDisplayed() || isNavigationMenuDisplayed();
            }
            return false;
        } catch (Exception e) {
            logger.error("Dashboard page not loaded properly: {}", e.getMessage());
            return false;
        }
    }
}

