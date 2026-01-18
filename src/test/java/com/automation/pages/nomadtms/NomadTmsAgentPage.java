package com.automation.pages.nomadtms;

import com.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Agent Page Object for Nomad TMS AI Agent Chat
 * URL: https://nomadtms.up.railway.app/agent
 */
public class NomadTmsAgentPage extends BasePage {

    @FindBy(xpath = "//a[@href='/agent']")
    private WebElement agentMenuLink;

    @FindBy(xpath = "//input[contains(@placeholder, 'Ask me anything')]")
    private WebElement chatInput;

    private By agentMessages = By.xpath("//main//div[contains(@class, 'rounded')]//p");

    /**
     * Click on Agent menu in sidebar
     */
    public void clickAgentMenu() {
        waitForClickability(agentMenuLink);
        click(agentMenuLink);
        logger.info("Clicked Agent menu");
    }

    /**
     * Send a message to the Agent
     */
    public void sendMessage(String message) {
        waitForVisibility(chatInput);
        sendKeys(chatInput, message);
        chatInput.sendKeys(Keys.ENTER);
        logger.info("Sent message: {}", message);
        waitForResponse();
    }

    /**
     * Get the last Agent response
     */
    public String getFullLastAgentResponse() {
        try {
            List<WebElement> responses = findElements(agentMessages);
            if (!responses.isEmpty()) {
                // Get the last response container's full text
                WebElement lastResponse = responses.get(responses.size() - 1);
                WebElement parent = lastResponse.findElement(By.xpath("./.."));
                return parent.getText().trim();
            }
        } catch (Exception e) {
            logger.error("Error getting agent response: {}", e.getMessage());
        }
        return "";
    }

    private void waitForResponse() {
        try {
            Thread.sleep(2000);
            getWait().until(ExpectedConditions.presenceOfElementLocated(agentMessages));
            Thread.sleep(500);
        } catch (Exception e) {
            logger.warn("Timeout waiting for response: {}", e.getMessage());
        }
    }

    @Override
    public boolean isPageLoaded() {
        try {
            waitForVisibility(chatInput);
            return getCurrentUrl().contains("/agent");
        } catch (Exception e) {
            return false;
        }
    }
}
