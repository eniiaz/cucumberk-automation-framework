package com.automation.pages.nomadtms;

import com.automation.pages.BasePage;
import com.automation.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Dashboard Page Object for Nomad TMS Fleet Management System
 * URL: https://nomadtms.up.railway.app/dashboard
 */
public class NomadTmsDashboardPage extends BasePage {

    // ==================== Page URL ====================
    private static final String PAGE_PATH = "/dashboard";

    // ==================== Web Elements ====================
    @FindBy(xpath = "//h1[contains(text(), 'Dashboard')] | //h2[contains(text(), 'Dashboard')] | //div[contains(@class, 'dashboard')]")
    private WebElement dashboardHeader;

    @FindBy(xpath = "//nav | //aside | //div[contains(@class, 'sidebar')]")
    private WebElement navigationMenu;

    @FindBy(xpath = "//button[contains(text(), 'Logout')] | //a[contains(text(), 'Logout')] | //button[contains(@aria-label, 'logout')]")
    private WebElement logoutButton;

    @FindBy(xpath = "//div[contains(@class, 'user')] | //span[contains(@class, 'user-name')] | //div[contains(@class, 'profile')]")
    private WebElement userProfile;

    // Dashboard elements
    private By grossRevenueLocator = By.xpath("//div[contains(text(), 'Gross Revenue')]");
    private By activeLoadsLocator = By.xpath("//div[contains(text(), 'Active Loads')]");
    private By activeDriversLocator = By.xpath("//div[contains(text(), 'Active Drivers')]");
    private By activeTrucksLocator = By.xpath("//div[contains(text(), 'Active Trucks')]");

    // ==================== Page Actions ====================

    /**
     * Navigate to dashboard page
     */
    public void navigateToDashboard() {
        String url = ConfigReader.getNomadTmsBaseUrl() + PAGE_PATH;
        navigateTo(url);
        logger.info("Navigated to Nomad TMS dashboard: {}", url);
    }

    /**
     * Click logout button
     */
    public void clickLogout() {
        try {
            waitForClickability(logoutButton);
            click(logoutButton);
            logger.info("Clicked logout button");
        } catch (Exception e) {
            logger.warn("Logout button not found, trying alternative methods");
        }
    }

    /**
     * Get dashboard header text
     * @return header text
     */
    public String getDashboardHeaderText() {
        try {
            return getText(dashboardHeader);
        } catch (Exception e) {
            return "";
        }
    }

    // ==================== Page Verifications ====================

    /**
     * Check if dashboard is displayed
     * @return true if dashboard is visible
     */
    public boolean isDashboardDisplayed() {
        try {
            // Check URL first
            String currentUrl = getCurrentUrl();
            if (currentUrl.contains("/dashboard") || currentUrl.contains("/home") || currentUrl.contains("/app")) {
                return true;
            }
            // Also check for dashboard elements
            return isDisplayed(dashboardHeader) || isDisplayed(navigationMenu);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if navigation menu is visible
     * @return true if navigation is visible
     */
    public boolean isNavigationMenuVisible() {
        try {
            return isDisplayed(navigationMenu);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if user profile is visible
     * @return true if user profile is visible
     */
    public boolean isUserProfileVisible() {
        try {
            return isDisplayed(userProfile);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if gross revenue card is visible
     * @return true if visible
     */
    public boolean isGrossRevenueCardVisible() {
        try {
            return isDisplayed(findElement(grossRevenueLocator));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if active loads card is visible
     * @return true if visible
     */
    public boolean isActiveLoadsCardVisible() {
        try {
            return isDisplayed(findElement(activeLoadsLocator));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if currently on dashboard by URL
     * @return true if on dashboard
     */
    public boolean isOnDashboard() {
        String currentUrl = getCurrentUrl();
        // Dashboard could be at /dashboard, /home, /app, or root after login
        return currentUrl.contains("/dashboard") || 
               currentUrl.contains("/home") || 
               currentUrl.contains("/app") ||
               (currentUrl.contains("nomadtms") && !currentUrl.contains("login"));
    }

    @Override
    public boolean isPageLoaded() {
        try {
            Thread.sleep(2000); // Wait for page to fully load after login
            String currentUrl = getCurrentUrl();
            // After successful login, user should not be on the landing page
            // They should be redirected to dashboard or another authenticated page
            boolean notOnLoginPage = !currentUrl.equals("https://nomadtms.up.railway.app/") &&
                                     !currentUrl.contains("login");
            boolean onAuthenticatedPage = currentUrl.contains("/dashboard") || 
                                          currentUrl.contains("/home") ||
                                          currentUrl.contains("/app") ||
                                          currentUrl.contains("/loads") ||
                                          currentUrl.contains("/drivers");
            return notOnLoginPage || onAuthenticatedPage;
        } catch (Exception e) {
            logger.error("Nomad TMS dashboard page not loaded properly: {}", e.getMessage());
            return false;
        }
    }
}
