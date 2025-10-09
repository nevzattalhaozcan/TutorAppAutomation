package com.tutor.utils;

import com.tutor.base.ConfigReader;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    private static final Logger logger = LogManager.getLogger(WaitUtils.class);

    public static WebElement waitForElement(AppiumDriver driver, By locator) {
        int explicitWait = ConfigReader.getIntProperty("explicit.wait");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            logger.error("Element not found: " + locator, e);
            throw e;
        }
    }

    public static WebElement waitForElementToBeClickable(AppiumDriver driver, By locator) {
        int explicitWait = ConfigReader.getIntProperty("explicit.wait");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            logger.error("Element not clickable: " + locator, e);
            throw e;
        }
    }

    public static boolean waitForElementVisibility(AppiumDriver driver, By locator) {
        int explicitWait = ConfigReader.getIntProperty("explicit.wait");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            logger.warn("Element not visible: " + locator);
            return false;
        }
    }
}
