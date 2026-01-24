package com.automation.pages.jasalma;

import com.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * AI Study Page Object for Jasalma AI Learning Platform
 * URL: https://www.jasalma.ai/ai-chat
 * 
 * This page handles the AI Study feature where users can:
 * - Select a course/project from dropdown
 * - Choose language
 * - Start new learning sessions
 * - Chat with AI agent
 */
public class JasalmaAIStudyPage extends BasePage {

    // ==================== Page URL ====================
    private static final String BASE_URL = "https://www.jasalma.ai";
    private static final String AI_CHAT_PATH = "/ai-chat";

    // ==================== Web Elements ====================
    
    // Navigation
    @FindBy(xpath = "//a[contains(@href, '/ai-chat')]")
    private WebElement aiStudyNavLink;

    // Course/Project Selection
    @FindBy(xpath = "//div[contains(@class, 'combobox') or @role='combobox']//div[contains(text(), 'Select a project') or contains(text(), 'AI Skills')]")
    private WebElement courseDropdown;

    // Language Selection
    @FindBy(xpath = "//select[contains(@aria-label, 'Language')] | //select[preceding-sibling::*[contains(text(), 'Language')]]")
    private WebElement languageSelect;

    // New Learning Session Button
    @FindBy(xpath = "//button[contains(., 'New Learning Session')]")
    private WebElement newLearningSessionButton;

    // Chat Input
    @FindBy(xpath = "//input[contains(@placeholder, 'Ask in') or contains(@placeholder, 'Select project')] | //textarea[contains(@placeholder, 'Ask')]")
    private WebElement chatInput;

    // Send Message Button
    @FindBy(xpath = "//button[contains(., 'Send message') or @aria-label='Send message']")
    private WebElement sendMessageButton;

    // AI Response Container
    @FindBy(xpath = "//div[contains(@class, 'message') or contains(@class, 'response')]//p | //div[img[contains(@alt, 'AI Teacher')]]//following-sibling::div//p")
    private List<WebElement> aiResponses;

    // Session Loading Indicator
    @FindBy(xpath = "//h3[contains(text(), 'Starting New Session')] | //button[contains(., 'Starting Session')]")
    private WebElement sessionLoadingIndicator;

    // Welcome Message
    @FindBy(xpath = "//h3[contains(text(), 'Welcome')]")
    private WebElement welcomeMessage;

    // ==================== Page Actions ====================

    /**
     * Navigate to AI Study page
     */
    public void navigateToAIStudy() {
        String url = BASE_URL + AI_CHAT_PATH;
        navigateTo(url);
        logger.info("Navigated to AI Study page: {}", url);
        waitForPageToLoad();
    }

    /**
     * Click on AI Study in navigation menu
     */
    public void clickAIStudyNav() {
        try {
            waitForClickability(aiStudyNavLink);
            click(aiStudyNavLink);
            logger.info("Clicked AI Study navigation link");
            waitForPageToLoad();
        } catch (Exception e) {
            logger.error("Failed to click AI Study nav: {}", e.getMessage());
            // Fallback to direct navigation
            navigateToAIStudy();
        }
    }

    /**
     * Wait for AI Study page to load
     */
    private void waitForPageToLoad() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Select a course/project from dropdown
     * @param courseName name of the course to select
     */
    public void selectCourse(String courseName) {
        try {
            // Click on the combobox to open dropdown
            WebElement combobox = getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.='Select a project to chat about']")));
            combobox.click();
            logger.info("Opened course dropdown");
            
            Thread.sleep(1000); // Wait for dropdown to open
            
            // Select the course option
            WebElement courseOption = getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@role='option' and contains(., '" + courseName + "')] | //div[@role='listbox']//div[contains(text(), '" + courseName + "')]")));
            courseOption.click();
            logger.info("Selected course: {}", courseName);
            
            Thread.sleep(2000); // Wait for course to load
        } catch (Exception e) {
            logger.error("Failed to select course '{}': {}", courseName, e.getMessage());
            throw new RuntimeException("Failed to select course: " + courseName, e);
        }
    }

    /**
     * Select language for the session
     * @param language language to select (e.g., "English", "Russian")
     */
    public void selectLanguage(String language) {
        try {
            WebElement langSelect = getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//select[contains(@aria-label, 'Language')] | //select[preceding-sibling::*[contains(text(), 'Language')] or following-sibling::*[contains(text(), 'Language')]]")));
            
            // Map language name to option value
            String optionText = getLanguageOptionText(language);
            new org.openqa.selenium.support.ui.Select(langSelect).selectByVisibleText(optionText);
            logger.info("Selected language: {}", language);
        } catch (Exception e) {
            logger.warn("Language select not found or already set: {}", e.getMessage());
            // Language might already be set or not changeable
        }
    }

    /**
     * Map language name to dropdown option text
     */
    private String getLanguageOptionText(String language) {
        switch (language.toLowerCase()) {
            case "english":
                return "🇺🇸 English";
            case "russian":
                return "🇷🇺 Русский (Russian)";
            case "turkish":
                return "🇹🇷 Türkçe (Turkish)";
            case "kyrgyz":
                return "🇰🇬 Кыргызча (Kyrgyz)";
            default:
                return language;
        }
    }

    /**
     * Click New Learning Session button
     */
    public void clickNewLearningSession() {
        try {
            WebElement button = getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(., 'New Learning Session')]")));
            button.click();
            logger.info("Clicked New Learning Session button");
        } catch (Exception e) {
            logger.error("Failed to click New Learning Session: {}", e.getMessage());
            throw new RuntimeException("Failed to start new learning session", e);
        }
    }

    /**
     * Wait for AI agent to load and be ready for chat
     */
    public void waitForAgentToLoad() {
        try {
            logger.info("Waiting for AI agent to load...");
            
            // Wait for loading indicator to appear and disappear
            try {
                getWait().until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//h3[contains(text(), 'Starting')] | //button[contains(., 'Starting')]")));
            } catch (Exception e) {
                // Loading indicator might have already passed
            }
            
            // Wait for chat input to become enabled
            Thread.sleep(5000); // Initial wait for session creation
            
            getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[contains(@placeholder, 'Ask in') and not(@disabled)] | //textarea[not(@disabled)]")));
            
            logger.info("AI agent is ready");
        } catch (Exception e) {
            logger.warn("Agent load wait completed with: {}", e.getMessage());
            // Continue anyway - agent might be ready
        }
    }

    /**
     * Send a message to the AI agent
     * @param message message to send
     */
    public void sendMessage(String message) {
        try {
            // Find the chat input
            WebElement input = getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[contains(@placeholder, 'Ask in') or contains(@placeholder, 'English')] | //textarea[contains(@placeholder, 'Ask')]")));
            
            input.clear();
            input.sendKeys(message);
            logger.info("Entered message: {}", message);
            
            Thread.sleep(500); // Small delay before clicking send
            
            // Click send button
            WebElement sendBtn = getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(., 'Send message') or @aria-label='Send message']")));
            sendBtn.click();
            logger.info("Sent message to AI agent");
            
        } catch (Exception e) {
            logger.error("Failed to send message: {}", e.getMessage());
            throw new RuntimeException("Failed to send message to AI agent", e);
        }
    }

    /**
     * Wait for AI response and get the latest response text
     * @return the AI agent's response text
     */
    public String waitForAndGetResponse() {
        try {
            logger.info("Waiting for AI response...");
            
            // Wait for response to appear (AI responses take time)
            Thread.sleep(10000); // Wait for AI to generate response
            
            // Get all paragraph elements in the response area
            List<WebElement> responseElements = getDriver().findElements(
                By.xpath("//div[img[contains(@alt, 'AI') or contains(@alt, 'Teacher')]]//following-sibling::div//p"));
            
            if (responseElements.isEmpty()) {
                // Try alternative selector
                responseElements = getDriver().findElements(
                    By.xpath("//main//div[contains(@class, 'message')]//p"));
            }
            
            if (!responseElements.isEmpty()) {
                // Get the last response (most recent)
                StringBuilder fullResponse = new StringBuilder();
                for (WebElement elem : responseElements) {
                    String text = elem.getText();
                    if (!text.isEmpty()) {
                        fullResponse.append(text).append(" ");
                    }
                }
                String response = fullResponse.toString().trim();
                logger.info("Received AI response (length: {} chars)", response.length());
                return response;
            }
            
            logger.warn("No AI response found");
            return "";
        } catch (Exception e) {
            logger.error("Failed to get AI response: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Get the latest AI response after sending a message
     * Waits up to 30 seconds for a non-empty response with polling
     * @return latest response text
     */
    public String getLatestResponse() {
        final int MAX_WAIT_SECONDS = 30;
        final int POLL_INTERVAL_MS = 2000;
        int elapsedSeconds = 0;
        String response = "";
        
        logger.info("Waiting for AI response (max {} seconds)...", MAX_WAIT_SECONDS);
        
        // XPath selectors to find AI response elements
        String[] responseXPaths = {
            "//div[img[contains(@alt, 'AI') or contains(@alt, 'Teacher')]]//div//p",
            "//main//div[contains(@class, 'message')]//p",
            "//div[contains(@class, 'ai-response')]//p",
            "//div[contains(@class, 'chat')]//div//p[last()]"
        };
        
        try {
            while (elapsedSeconds < MAX_WAIT_SECONDS) {
                // Try each XPath selector
                for (String xpath : responseXPaths) {
                    response = extractResponseText(xpath);
                    if (!response.isEmpty()) {
                        logger.info("AI response found after {} seconds (length: {} chars)", 
                            elapsedSeconds, response.length());
                        return response;
                    }
                }
                
                // Wait before next poll
                Thread.sleep(POLL_INTERVAL_MS);
                elapsedSeconds += (POLL_INTERVAL_MS / 1000);
                logger.debug("Polling for AI response... ({}/{}s)", elapsedSeconds, MAX_WAIT_SECONDS);
            }
            
            logger.warn("Timeout waiting for AI response after {} seconds", MAX_WAIT_SECONDS);
            return response;
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Interrupted while waiting for AI response: {}", e.getMessage());
            return response;
        } catch (Exception e) {
            logger.error("Error getting latest response: {}", e.getMessage());
            return response;
        }
    }
    
    /**
     * Extract response text from elements matching the given XPath
     * @param xpath XPath to find response elements
     * @return concatenated text from matching elements, or empty string if none found
     */
    private String extractResponseText(String xpath) {
        try {
            List<WebElement> elements = getDriver().findElements(By.xpath(xpath));
            
            if (elements.isEmpty()) {
                return "";
            }
            
            // Get the latest messages (last 15 paragraphs to capture full response)
            StringBuilder responseBuilder = new StringBuilder();
            int startIndex = Math.max(0, elements.size() - 15);
            
            for (int i = startIndex; i < elements.size(); i++) {
                try {
                    WebElement element = elements.get(i);
                    // Wait for element to have text
                    String text = element.getText();
                    if (text != null && !text.trim().isEmpty()) {
                        responseBuilder.append(text.trim()).append(" ");
                    }
                } catch (Exception e) {
                    // Element might be stale, continue to next
                    logger.debug("Skipping element due to: {}", e.getMessage());
                }
            }
            
            return responseBuilder.toString().trim();
        } catch (Exception e) {
            logger.debug("Failed to extract text with xpath '{}': {}", xpath, e.getMessage());
            return "";
        }
    }

    // ==================== Page Verifications ====================

    /**
     * Check if on AI Study page
     * @return true if on AI chat page
     */
    public boolean isOnAIStudyPage() {
        String currentUrl = getCurrentUrl();
        return currentUrl.contains("/ai-chat");
    }

    /**
     * Check if chat input is enabled
     * @return true if chat is ready
     */
    public boolean isChatReady() {
        try {
            WebElement input = getDriver().findElement(
                By.xpath("//input[contains(@placeholder, 'Ask')] | //textarea[contains(@placeholder, 'Ask')]"));
            return input.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if course is selected
     * @param courseName expected course name
     * @return true if course is selected
     */
    public boolean isCourseSelected(String courseName) {
        try {
            WebElement combobox = getDriver().findElement(By.xpath("//div[@role='combobox']"));
            return combobox.getText().contains(courseName);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean isPageLoaded() {
        try {
            Thread.sleep(2000);
            return isOnAIStudyPage();
        } catch (Exception e) {
            logger.error("AI Study page not loaded properly: {}", e.getMessage());
            return false;
        }
    }
}
