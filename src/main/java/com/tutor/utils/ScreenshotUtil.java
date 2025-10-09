package com.tutor.utils;

import com.tutor.base.ConfigReader;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {
    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);

    public static String captureScreenshot(AppiumDriver driver, String testName) {
        try {
            String screenshotPath = ConfigReader.getProperty("screenshot.path");
            File screenshotDir = new File(screenshotPath);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = screenshotPath + File.separator + fileName;

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                fos.write(bytes);
            }

            logger.info("Screenshot captured: " + filePath);
            return filePath;
        } catch (IOException e) {
            logger.error("Failed to capture screenshot", e);
            throw new RuntimeException(e);
        }
    }
}
