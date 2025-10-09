package com.tutor.base;

import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DriverManager {
    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(DriverManager.class);

    public static void setDriver(AppiumDriver appiumDriver) {
        driver.set(appiumDriver);
        logger.info("Driver set in DriverManager");
    }

    public static AppiumDriver getDriver() {
        AppiumDriver appiumDriver = driver.get();
        if (appiumDriver == null) {
            throw new RuntimeException("Driver not initialized. Please call setDriver() first.");
        }
        return appiumDriver;
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            logger.info("Driver quit successfully");
        }
    }

    public static boolean isDriverActive() {
        return driver.get() != null;
    }
}
