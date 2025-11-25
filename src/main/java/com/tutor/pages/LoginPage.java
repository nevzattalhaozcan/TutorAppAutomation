package com.tutor.pages;

import com.tutor.base.ConfigReader;
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
    private final By forgotPasswordLink = AppiumBy.accessibilityId("Şifremi Unuttum");
    private final By forgotPasswordEmailField = AppiumBy
            .androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")");
    private final By sendResetLinkButton = AppiumBy
            .androidUIAutomator("new UiSelector().description(\"Gönder\")");
    
    private final By registerLink = AppiumBy.androidUIAutomator("new UiSelector().description(\"Kayıt Olun\")");
    private final By registerEmailField = AppiumBy
            .androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)");
    private final By registerPasswordField = AppiumBy
            .androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)");
    private final By registerConfirmPasswordField = AppiumBy
            .androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(2)");
    private final By registerButton = AppiumBy
            .androidUIAutomator("new UiSelector().description(\"Hesap Oluştur\")");
    private final By checkEmailVerificationButton = AppiumBy
            .androidUIAutomator("new UiSelector().description(\"Doğrulamayı Kontrol Et\")");
    private final By registerNameSurnameField = AppiumBy
            .androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)");
    private final By registerUsernameField = AppiumBy
            .androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)");
    private final By registerPhoneNumberField = AppiumBy
            .androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(2)");
    private final By registerSexDropdown = AppiumBy
            .androidUIAutomator("new UiSelector().description(\"Cinsiyet\")");
    private final By registerAccountTypeDropdown = AppiumBy
            .androidUIAutomator("new UiSelector().description(\"Hesap Tipi\")");
    private final By registerAcceptTermsCheckbox = AppiumBy
            .androidUIAutomator("new UiSelector().description(\"Kullanım Koşulları ve Gizlilik Politikasını okudum ve kabul ediyorum\")");
    private final By registerFinalRegisterButton = AppiumBy
            .androidUIAutomator("new UiSelector().description(\"Kaydı Tamamla\")");

    private final By loginWithGoogleButton = AppiumBy.accessibilityId("Gönder");
    private final By addAnotherAccountButton = AppiumBy
            .androidUIAutomator("new UiSelector().resourceId(\"com.google.android.gms:id/add_account_chip_title\")");
    private final By flutterErrorMessage = By
            .xpath("//*[contains(@text,'Invalid') or contains(@text,'credentials') or contains(@text,'error')]");
    private final By googleEmailField = AppiumBy
            .androidUIAutomator("new UiSelector().resourceId(\"identifierId\")");
    private final By googleNextButton = AppiumBy
            .androidUIAutomator("new UiSelector().text(\"NEXT\")");
    private final By googlePasswordField = AppiumBy
            .androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")");
    private final By googleSkipButton = AppiumBy
            .androidUIAutomator("new UiSelector().text(\"Skip\")");
    private final By googleAgreeButton = AppiumBy
            .androidUIAutomator("new UiSelector().text(\"I agree\")");

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

    public HomePage login() {
        logger.info("Performing login with email: " + ConfigReader.getProperty("test.user.email"));
        String email = ConfigReader.getProperty("test.user.email");
        String password = ConfigReader.getProperty("test.user.password");
        HomePage homePage = enterEmail(email)
                .enterPassword(password)
                .clickLoginButton();
        return homePage.waitForPageLoad();
    }

    public LoginPage clickLoginWithGoogleButton() {
        logger.info("Clicking login with Google button");
        click(loginWithGoogleButton);
        return this;
    }

    public LoginPage clickAddAnotherAccountButton() {
        logger.info("Clicking Add another account button");
        click(addAnotherAccountButton);
        return this;
    }

    public LoginPage enterGoogleEmail(String email) {
        logger.info("Entering Google email: " + email);
        enterText(googleEmailField, email);
        return this;
    }

    public LoginPage clickGoogleNextButton() {
        logger.info("Clicking Google NEXT button");
        click(googleNextButton);
        return this;
    }

    public LoginPage enterGooglePassword(String password) {
        logger.info("Entering Google password");
        enterText(googlePasswordField, password);
        return this;
    }

    public LoginPage clickGoogleSkipButton() {
        logger.info("Clicking Google Skip button");
        click(googleSkipButton);
        return this;
    }

    public HomePage clickGoogleAgreeButton() {
        logger.info("Clicking Google I agree button");
        click(googleAgreeButton);
        waitForPostLoginState();
        return new HomePage(driver);
    }

    public HomePage loginWithGoogle(String email, String password) {
        logger.info("Performing Google login with email: " + email);
        HomePage homePage = clickLoginWithGoogleButton()
                .clickAddAnotherAccountButton()
                .enterGoogleEmail(email)
                .clickGoogleNextButton()
                .enterGooglePassword(password)
                .clickGoogleNextButton()
                .clickGoogleSkipButton()
                .clickGoogleAgreeButton();
        waitForPostLoginState();
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

    public LoginPage clickForgotPasswordLink() {
        logger.info("Clicking forgot password link");
        click(forgotPasswordLink);
        return this;
    }

    public LoginPage enterEmailForPasswordReset(String email) {
        logger.info("Entering email for password reset: " + email);
        enterText(forgotPasswordEmailField, email);
        return this;
    }

    public LoginPage clickSendResetLinkButton() {
        logger.info("Clicking send reset link button");
        click(sendResetLinkButton);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public LoginPage clickRegisterLink() {
        logger.info("Clicking register link");
        click(registerLink);
        waitForVisibility(registerEmailField);
        return this;
    }

    public LoginPage enterRegistrationCreds(String email, String password) {
        logger.info("Entering registration credentials: " + email);
        enterText(registerEmailField, email);
        enterText(registerPasswordField, password);
        enterText(registerConfirmPasswordField, password);
        hideKeyboardIfVisible();
        if (!clickUntilVisible(registerButton, checkEmailVerificationButton, Duration.ofSeconds(6), 3)) {
            throw new IllegalStateException("Register verification screen did not appear after tapping register button.");
        }
        logger.info("Register verification screen loaded, triggering Mailosaur poll…");
        return this;
    }

    public LoginPage enterProfileInfo(String firstName, String lastName, String username, String phoneNumber, String gender, String accountType) {
        logger.info("Entering profile information");
        enterText(registerNameSurnameField, firstName + " " + lastName);
        enterText(registerUsernameField, username);
        enterText(registerPhoneNumberField, phoneNumber);
        click(registerSexDropdown);
        By genderSelector = AppiumBy.androidUIAutomator(gender);
        click(genderSelector);
        click(registerAccountTypeDropdown);
        By accountTypeSelector = AppiumBy.androidUIAutomator(accountType);
        click(accountTypeSelector);
        click(registerAcceptTermsCheckbox);
        return this;
    }

    public LoginPage clickCheckEmailVerificationButton() {
        logger.info("Clicking check email verification button");
        click(checkEmailVerificationButton);
        return this;
    }

    public HomePage clickFinalRegisterButton() {
        logger.info("Clicking final register button");
        click(registerFinalRegisterButton);
        waitForPostLoginState();
        return new HomePage(driver);
    }

}
