package com.automation.pages;

import com.automation.utils.ConfigReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Home Page Object - Example Page Implementation
 * Demonstrates Page Object Model pattern
 */
public class HomePage extends BasePage {

    // ==================== Page URL ====================
    private static final String PAGE_PATH = "/home";

    // ==================== Web Elements ====================
    @FindBy(css = ".welcome-message")
    private WebElement welcomeMessage;

    @FindBy(css = ".user-profile")
    private WebElement userProfile;

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(css = ".navigation-menu")
    private WebElement navigationMenu;

    @FindBy(css = ".dashboard-section")
    private WebElement dashboardSection;

    @FindBy(css = ".notification-bell")
    private WebElement notificationBell;

    @FindBy(css = ".notification-count")
    private WebElement notificationCount;

    @FindBy(css = ".search-input")
    private WebElement searchInput;

    @FindBy(css = ".search-button")
    private WebElement searchButton;

    // ==================== Page Actions ====================

    /**
     * Navigate to home page
     */
    public void navigateToHomePage() {
        String url = ConfigReader.getBaseUrl() + PAGE_PATH;
        navigateTo(url);
        logger.info("Navigated to home page");
    }

    /**
     * Click logout button
     */
    public void clickLogout() {
        click(logoutButton);
        logger.info("Clicked logout button");
    }

    /**
     * Click user profile
     */
    public void clickUserProfile() {
        click(userProfile);
    }

    /**
     * Click notification bell
     */
    public void clickNotificationBell() {
        click(notificationBell);
    }

    /**
     * Perform search
     * @param searchText text to search
     */
    public void performSearch(String searchText) {
        sendKeys(searchInput, searchText);
        click(searchButton);
        logger.info("Performed search for: {}", searchText);
    }

    // ==================== Page Verifications ====================

    /**
     * Get welcome message text
     * @return welcome message text
     */
    public String getWelcomeMessage() {
        return getText(welcomeMessage);
    }

    /**
     * Check if user is logged in (welcome message visible)
     * @return true if logged in
     */
    public boolean isUserLoggedIn() {
        return isDisplayed(welcomeMessage);
    }

    /**
     * Check if dashboard section is displayed
     * @return true if displayed
     */
    public boolean isDashboardDisplayed() {
        return isDisplayed(dashboardSection);
    }

    /**
     * Get notification count
     * @return notification count as string
     */
    public String getNotificationCount() {
        return getText(notificationCount);
    }

    /**
     * Check if navigation menu is displayed
     * @return true if displayed
     */
    public boolean isNavigationMenuDisplayed() {
        return isDisplayed(navigationMenu);
    }

    @Override
    public boolean isPageLoaded() {
        try {
            waitForVisibility(welcomeMessage);
            waitForVisibility(navigationMenu);
            return true;
        } catch (Exception e) {
            logger.error("Home page not loaded properly: {}", e.getMessage());
            return false;
        }
    }
}

