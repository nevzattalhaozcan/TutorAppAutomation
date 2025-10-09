package com.tutor.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class LoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(LoginPage.class);
    private static final Duration POST_LOGIN_TIMEOUT = Duration.ofSeconds(10);
    private static final int PAGE_SOURCE_PREVIEW = 500;

    private final By emailFieldByHint = By.xpath("//android.widget.EditText[@hint='Email Adresi']");
    private final By passwordField = By.xpath("//android.widget.EditText[@hint='Şifre']");
    private final By loginButton = AppiumBy.accessibilityId("Giriş Yap");
    private final By flutterErrorMessage = By
            .xpath("//*[contains(@text,'Invalid') or contains(@text,'credentials') or contains(@text,'error')]");

    public LoginPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        logger.info("LoginPage initialized");
    }

    public LoginPage enterEmail(String email) {
        logger.info("Entering email: " + email);
        enterText(emailFieldByHint, email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        logger.info("Entering password");
        enterText(passwordField, password);
        return this;
    }

    public HomePage clickLoginButton() {
        logger.info("Clicking login button");
        click(loginButton);
        waitForPostLoginState();
        return new HomePage(driver);
    }

    public HomePage login(String email, String password) {
        logger.info("Performing login with email: " + email);
        HomePage homePage = enterEmail(email)
                .enterPassword(password)
                .clickLoginButton();
        return homePage.waitForPageLoad();
    }

    private void waitForPostLoginState() {
        waitForAnyPresence(POST_LOGIN_TIMEOUT, HomePage.WELCOME_TEXT, flutterErrorMessage);
    }

    public LoginPage waitForPageLoad() {
        logger.info("Waiting for LoginPage to load...");
        logPageSourcePreview();
        waitForVisibility(emailFieldByHint);
        logger.info("LoginPage loaded successfully");
        return this;

    }

    private void logPageSourcePreview() {
        String pageSource = driver.getPageSource();
        int endIndex = Math.min(PAGE_SOURCE_PREVIEW, pageSource.length());
        logger.info("Current page source (first {} chars): {}", endIndex, pageSource.substring(0, endIndex));
    }
}
