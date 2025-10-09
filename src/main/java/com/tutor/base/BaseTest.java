package com.tutor.base;

import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class BaseTest {
    protected AppiumDriver driver;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() throws MalformedURLException, URISyntaxException {
        String platform = ConfigReader.getProperty("platform");
        driver = DriverFactory.createDriver(platform);
        DriverManager.setDriver(driver);
        logger.info("Test setup completed for platform: " + platform);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
        logger.info("Test teardown completed");
    }
}
