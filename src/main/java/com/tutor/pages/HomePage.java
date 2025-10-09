package com.tutor.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    public static final String WELCOME_ACCESSIBILITY_ID = "Hoş geldiniz,";
    public static final By WELCOME_TEXT = AppiumBy.accessibilityId(WELCOME_ACCESSIBILITY_ID);

    public HomePage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        logger.info("HomePage initialized");
    }

    public String getWelcomeText() {
        logger.info("Getting welcome text");
        String text = getText(WELCOME_TEXT);
        return text == null ? "" : text.trim();
    }

    public HomePage waitForPageLoad() {
        waitForVisibility(WELCOME_TEXT);
        logger.info("HomePage loaded successfully");
        return this;
    }
}
