package com.tutor.pages;

import com.tutor.base.ConfigReader;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.RemoteWebElement;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasePage {
    protected AppiumDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    private static final List<String> TEXT_ATTRIBUTES = Arrays.asList("text", "contentDescription", "label", "name", "value");
    private static final String MOBILE_SET_TEXT_COMMAND = "mobile: setText";
    private static final String MOBILE_GET_TEXT_COMMAND = "mobile: getText";

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        int explicitWait = ConfigReader.getIntProperty("explicit.wait");
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForAnyPresence(Duration timeout, By... locators) {
        if (locators == null || locators.length == 0) {
            return;
        }
        WebDriverWait customWait = new WebDriverWait(driver, timeout);
        ExpectedCondition<?>[] conditions = Arrays.stream(locators)
                .map(ExpectedConditions::presenceOfElementLocated)
                .toArray(ExpectedCondition[]::new);
        customWait.until(ExpectedConditions.or(conditions));
    }

    protected WebElement findElement(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Element not found: " + locator);
            throw e;
        }
    }

    protected void click(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            logger.info("Clicked on element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to click element: " + locator, e);
            throw e;
        }
    }

    protected void enterText(By locator, String text) {
        try {
            WebElement element = waitForClickability(locator);
            element.click();
            element.clear();
            if (text != null) {
                element.sendKeys(text);
            }

            if (shouldRetryTextEntry(element, text)) {
                applyMobileSetText(element, text);
            }

            logger.info("Entered text in element: " + locator);
        } catch (Exception e) {
            logger.error("Failed to enter text in element: " + locator, e);
            throw e;
        }
    }

    protected String getText(By locator) {
        try {
            WebElement element = waitForVisibility(locator);
            String visibleText = extractVisibleText(element);

            if (visibleText == null || visibleText.isBlank()) {
                visibleText = fetchTextWithMobileCommand(element);
            }

            if (visibleText == null || visibleText.isBlank()) {
                throw new NoSuchElementException("No text found for locator " + locator);
            }

            logger.info("Retrieved text '" + visibleText + "' from element: " + locator);
            return visibleText;
        } catch (Exception e) {
            logger.error("Failed to get text from element: " + locator, e);
            throw e;
        }
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            return findElement(locator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            logger.warn("Element not displayed: " + locator);
            return false;
        }
    }

    protected void swipe(Direction direction) {
        Dimension size = driver.manage().window().getSize();
        int startX = size.getWidth() / 2;
        int startY = size.getHeight() / 2;
        int endX, endY;

        switch (direction) {
            case UP:
                endX = startX;
                endY = (int) (size.getHeight() * 0.25);
                break;
            case DOWN:
                endX = startX;
                endY = (int) (size.getHeight() * 0.75);
                break;
            case LEFT:
                endX = (int) (size.getWidth() * 0.25);
                endY = startY;
                break;
            case RIGHT:
                endX = (int) (size.getWidth() * 0.75);
                endY = startY;
                break;
            default:
                return;
        }

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1);
        sequence.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(
                finger.createPointerMove(Duration.ofMillis(600), PointerInput.Origin.viewport(), endX, endY));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(java.util.Collections.singletonList(sequence));
        logger.info("Swiped " + direction);
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private boolean shouldRetryTextEntry(WebElement element, String expectedText) {
        if (expectedText == null || expectedText.isEmpty()) {
            return false;
        }

        String currentValue = extractVisibleText(element);
        return currentValue == null
                || currentValue.isBlank()
                || currentValue.equals(element.getAttribute("hint"));
    }

    private String extractVisibleText(WebElement element) {
        String directText = element.getText();
        if (directText != null && !directText.isBlank()) {
            return directText;
        }

        for (String attribute : TEXT_ATTRIBUTES) {
            String value = element.getAttribute(attribute);
            if (value != null && !value.isBlank()) {
                return value;
            }
        }

        return null;
    }

    private void applyMobileSetText(WebElement element, String text) {
        if (!(element instanceof RemoteWebElement remoteWebElement)) {
            logger.warn("Cannot fallback to mobile:setText, unsupported element type: {}", element.getClass());
            return;
        }

        Map<String, Object> args = new HashMap<>();
        args.put("elementId", remoteWebElement.getId());
        args.put("text", text);
        args.put("replace", true);

        executeMobileCommand(MOBILE_SET_TEXT_COMMAND, args);
        logger.info("Fallback mobile:setText executed for element id: " + remoteWebElement.getId());
    }

    private String fetchTextWithMobileCommand(WebElement element) {
        if (!(element instanceof RemoteWebElement remoteWebElement)) {
            logger.warn("Cannot execute mobile:getText, unsupported element type: {}", element.getClass());
            return null;
        }

        Map<String, Object> args = new HashMap<>();
        args.put("elementId", remoteWebElement.getId());

        Object result = executeMobileCommand(MOBILE_GET_TEXT_COMMAND, args);
        if (result != null) {
            return result.toString();
        }

        return null;
    }

    private Object executeMobileCommand(String command, Map<String, Object> args) {
        try {
            return driver.executeScript(command, args);
        } catch (Exception ex) {
            logger.warn("Execution of {} failed with args {}", command, args, ex);
            return null;
        }
    }
}
