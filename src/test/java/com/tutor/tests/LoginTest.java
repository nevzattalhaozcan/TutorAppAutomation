package com.tutor.tests;

import com.tutor.base.BaseTest;
import com.tutor.base.ConfigReader;
import com.tutor.listeners.FailureScreenshotListener;
import com.tutor.pages.HomePage;
import com.tutor.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(FailureScreenshotListener.class)
public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeMethod(dependsOnMethods = "setUp")
    public void initializeLoginPage() {
        loginPage = new LoginPage(driver);
        loginPage.waitForPageLoad();
    }

    @Test
    public void testSuccessfulLogin() {
        logger.info("Starting testSuccessfulLogin");
        HomePage homePage = loginPage.login(
                ConfigReader.getProperty("test.user.email"),
                ConfigReader.getProperty("test.user.password"));

        String welcomeText = homePage.getWelcomeText();
        Assert.assertTrue(
                normalize(welcomeText).contains(normalize(HomePage.WELCOME_ACCESSIBILITY_ID)),
                "Expected welcome text to resemble '" + HomePage.WELCOME_ACCESSIBILITY_ID + "' but was: " + welcomeText);
        logger.info("testSuccessfulLogin passed");
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }
        return value.replaceAll("[\\s,]", "").trim();
    }

    @Test(enabled = false)
    public void testInvalidCredentials() {
        logger.info("Starting testInvalidCredentials");
        loginPage.enterEmail("invalid@em.com")
                .enterPassword("invalid")
                .clickLoginButton();

        //Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(false);
        logger.info("testInvalidCredentials passed");
    }
}
