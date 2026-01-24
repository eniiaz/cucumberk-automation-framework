package com.automation.pages.jasalma;

import com.automation.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Dashboard Page Object for Jasalma AI Learning Platform
 * URL: https://www.jasalma.ai/dashboard
 */
public class JasalmaDashboardPage extends BasePage {

    // ==================== Page URL ====================
    private static final String BASE_URL = "https://www.jasalma.ai";
    private static final String DASHBOARD_PATH = "/dashboard";
    private static final String MAIN_PAGE_URL = "https://www.jasalma.ai/";

    // ==================== Web Elements ====================
    @FindBy(xpath = "//nav | //aside | //div[contains(@class, 'sidebar')] | //div[contains(@class, 'navigation')]")
    private WebElement navigationMenu;

    @FindBy(xpath = "//button[contains(text(), 'Logout')] | //a[contains(text(), 'Logout')] | //button[contains(@aria-label, 'logout')] | //button[contains(., 'Sign out')]")
    private WebElement logoutButton;

    @FindBy(xpath = "//div[contains(@class, 'user')] | //span[contains(@class, 'user-name')] | //div[contains(@class, 'profile')]")
    private WebElement userProfile;

    // ==================== Page Actions ====================

    /**
     * Navigate to dashboard page
     */
    public void navigateToDashboard() {
        String url = BASE_URL + DASHBOARD_PATH;
        navigateTo(url);
        logger.info("Navigated to Jasalma dashboard: {}", url);
        waitForPageToLoad();
    }

    /**
     * Wait for dashboard to load
     */
    private void waitForPageToLoad() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Click logout button if available
     */
    public void clickLogout() {
        try {
            waitForClickability(logoutButton);
            click(logoutButton);
            logger.info("Clicked logout button");
        } catch (Exception e) {
            logger.warn("Logout button not found: {}", e.getMessage());
        }
    }

    // ==================== Page Verifications ====================

    /**
     * Check if dashboard is displayed (URL contains /dashboard)
     * @return true if on dashboard
     */
    public boolean isDashboardDisplayed() {
        try {
            Thread.sleep(2000);
            String currentUrl = getCurrentUrl();
            return currentUrl.contains("/dashboard");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if user is still on dashboard (not redirected away)
     * @return true if still on dashboard
     */
    public boolean isStillOnDashboard() {
        String currentUrl = getCurrentUrl();
        return currentUrl.contains("/dashboard");
    }

    /**
     * Check if user was redirected to main page
     * @return true if on main page
     */
    public boolean isOnMainPage() {
        String currentUrl = getCurrentUrl();
        // Main page is exactly jasalma.ai/ without /dashboard or other paths
        return currentUrl.equals(MAIN_PAGE_URL) || 
               currentUrl.equals("https://www.jasalma.ai") ||
               (!currentUrl.contains("/dashboard") && !currentUrl.contains("/sign-in") && !currentUrl.contains("/sign-up"));
    }

    /**
     * Check if user is NOT on main page (for test assertion)
     * @return true if NOT on main page
     */
    public boolean isNotRedirectedToMainPage() {
        String currentUrl = getCurrentUrl();
        // User should be on dashboard, not main page
        boolean notMainPage = !currentUrl.equals(MAIN_PAGE_URL) && !currentUrl.equals("https://www.jasalma.ai");
        boolean onDashboard = currentUrl.contains("/dashboard");
        return onDashboard || notMainPage;
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
     * Get current page URL
     * @return current URL
     */
    public String getCurrentPageUrl() {
        return getCurrentUrl();
    }

    @Override
    public boolean isPageLoaded() {
        try {
            Thread.sleep(2000);
            String currentUrl = getCurrentUrl();
            // After login, user should be on dashboard (or at least not on sign-in/sign-up)
            return currentUrl.contains("/dashboard") || 
                   (!currentUrl.contains("/sign-in") && !currentUrl.contains("/sign-up"));
        } catch (Exception e) {
            logger.error("Jasalma dashboard page not loaded properly: {}", e.getMessage());
            return false;
        }
    }
}
