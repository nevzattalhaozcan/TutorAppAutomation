package com.tutor.tests;

import com.tutor.base.BaseTest;
import com.tutor.base.ConfigReader;
import com.tutor.listeners.FailureScreenshotListener;
import com.tutor.pages.HomePage;
import com.tutor.pages.LoginPage;
import com.tutor.utils.TestUtils;
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

    @Test(enabled = true)
    public void testSuccessfulLogin() {
        logger.info("Starting testSuccessfulLogin");
        HomePage homePage = loginPage.login(
                ConfigReader.getProperty("test.user.email"),
                ConfigReader.getProperty("test.user.password"));

        String welcomeText = homePage.getWelcomeText();
        Assert.assertTrue(
                TestUtils.normalize(welcomeText).contains(TestUtils.normalize(HomePage.USERS_NAME)),
                "Expected welcome text to resemble '" + HomePage.USERS_NAME + "' but was: " + welcomeText);
        logger.info("testSuccessfulLogin passed");
    }

}
