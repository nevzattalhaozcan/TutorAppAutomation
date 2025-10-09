package com.tutor.listeners;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.tutor.base.DriverManager;
import com.tutor.utils.ScreenshotUtil;

import io.appium.java_client.AppiumDriver;

public class FailureScreenshotListener extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult result) {
        AppiumDriver driver = DriverManager.getDriver();
        if (driver != null) {
            ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());
        }
    }
}
