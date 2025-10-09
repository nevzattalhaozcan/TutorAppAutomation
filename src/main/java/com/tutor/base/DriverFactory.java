package com.tutor.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    public static AppiumDriver createDriver(String platform) throws MalformedURLException, URISyntaxException {
        if (platform.equalsIgnoreCase("android")) {
            return createAndroidDriver();
        } else if (platform.equalsIgnoreCase("ios")) {
            return createIOSDriver();
        }
        throw new IllegalArgumentException("Invalid platform: " + platform);
    }

    private static AppiumDriver createAndroidDriver() throws MalformedURLException, URISyntaxException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setAppPackage(ConfigReader.getProperty("android.app.package"));
        options.setAppActivity(ConfigReader.getProperty("android.app.activity"));
        options.setAutoGrantPermissions(true);
        options.setNoReset(false);
        options.setFullReset(false);
        options.setUninstallOtherPackages("com.tutorapp.other");

        URL serverUrl = new URI(ConfigReader.getProperty("appium.server.url")).toURL();
        AppiumDriver driver = new AndroidDriver(serverUrl, options);

        logger.info("Android driver initialized successfully");
        return driver;
    }

    private static AppiumDriver createIOSDriver() throws MalformedURLException, URISyntaxException {
        XCUITestOptions options = new XCUITestOptions();
        options.setDeviceName(ConfigReader.getProperty("ios.device.name"));
        options.setPlatformVersion(ConfigReader.getProperty("ios.platform.version"));
        options.setBundleId(ConfigReader.getProperty("ios.bundle.id"));
        options.setWaitForIdleTimeout(Duration.ofSeconds(10));

        URL serverUrl = new URI(ConfigReader.getProperty("appium.server.url")).toURL();
        AppiumDriver driver = new IOSDriver(serverUrl, options);

        logger.info("iOS driver initialized successfully");
        return driver;
    }
}
