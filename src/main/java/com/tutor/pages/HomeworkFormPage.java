package com.tutor.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class HomeworkFormPage extends BasePage {

    private static final By HOMEWORK_TITLE_INPUT = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)");
    private static final By HOMEWORK_DUE_BTN = AppiumBy.accessibilityId("Tarih seçin");
    private static final By HOMEWORK_DUE_EXISTING_DATE = AppiumBy.xpath("//android.view.View[contains(@content-desc, '.') and contains(@content-desc, '20')]");
    private static final By HOMEWORK_MANUEL_ENTRY_BTN = AppiumBy.accessibilityId("Girişe geç");
    private static final By HOMEWORK_DUE_INPUT = AppiumBy.xpath("//android.widget.EditText[@hint=\"Tarih Girin\"]");
    private static final By HOMEWORK_DUE_ACCEPT_BTN = AppiumBy.accessibilityId("Seç");
    private static final By HOMEWORK_DESCRIPTION_INPUT = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)");
    private static final By HOMEWORK_FILE_UPLOAD_FIELD = AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Dosya seçmek için tıklayın\")");
    private static final By HOMEWORK_FILE = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.google.android.documentsui:id/icon_thumb\")");
    private static final By HOMEWORK_FILE_UPLOAD_BTN = AppiumBy.accessibilityId("Yükle");
    private static final By HOMEWORK_CREATE_BTN = AppiumBy.accessibilityId("Ödev Oluştur");
    private static final By HOMEWORK_UPLOADED_FILE = AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Yüklendi\")");
    private static final By HOMEWORK_UPLOADED_FILE_REMOVE_BTN = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(1)");
    private static final By HOMEWORK_UPDATE_BTN = AppiumBy.androidUIAutomator("new UiSelector().description(\"Güncelle\")");
    private static final By HOMEWORK_UPDATE_CANCEL_BTN = AppiumBy.androidUIAutomator("new UiSelector().description(\"İptal\")");

    public HomeworkFormPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        logger.info("HomeworkFormPage initialized");
    }

    public HomeworkFormPage enterTitle(String title) {
        logger.info("Entering homework title as: {}", title);
        enterText(HOMEWORK_TITLE_INPUT, title);
        return this;
    }

    public HomeworkFormPage enterDue(String date) {
        logger.info("Entering homework due date as: {}", date);
        hideKeyboardIfVisible();
        if (waitForOptionalVisibility(HOMEWORK_DUE_BTN, java.time.Duration.ofSeconds(2))) {
            click(HOMEWORK_DUE_BTN);
        } else {
            logger.info("Default due date button not found, trying existing date button");
            scrollIntoView(HOMEWORK_DUE_EXISTING_DATE);
            click(HOMEWORK_DUE_EXISTING_DATE);
        }
        click(HOMEWORK_MANUEL_ENTRY_BTN);
        enterText(HOMEWORK_DUE_INPUT, date);
        if (waitForOptionalVisibility(HOMEWORK_DUE_ACCEPT_BTN, java.time.Duration.ofSeconds(2))) {
            click(HOMEWORK_DUE_ACCEPT_BTN);
        } else {
            click(AppiumBy.accessibilityId("Tamam"));
        }
        return this;
    }

    public HomeworkFormPage enterDescription(String description) {
        logger.info("Entering homework description");
        enterText(HOMEWORK_DESCRIPTION_INPUT, description);
        hideKeyboardIfVisible();
        swipe(Direction.UP);
        return this;
    }

    public HomeworkFormPage uploadFile() {
        logger.info("Uploading the first file by default");
        scrollIntoView(HOMEWORK_FILE_UPLOAD_FIELD);
        click(HOMEWORK_FILE_UPLOAD_FIELD);
        logger.info("Clicked on file upload field");
        click(HOMEWORK_FILE);
        logger.info("Clicked on the first file");
        click(HOMEWORK_FILE_UPLOAD_BTN);
        logger.info("Clicked on upload button");
        return this;
    }

    public HomeworkFormPage clickCreate() {
        logger.info("Clicking homework create button");
        waitForVisibility(HOMEWORK_UPLOADED_FILE);

        // Wait for the info popup (Flutter snackbar) to disappear
        try {
            logger.info("Waiting for info popup to disappear...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.warn("Interrupted while waiting for popup", e);
        }
        
        hideKeyboardIfVisible();
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().descriptionContains(\"Ödev Oluştur\"))"));
        } catch (Exception e) {
            logger.warn("UiScrollable failed, falling back to manual scroll");
            scrollIntoView(HOMEWORK_CREATE_BTN);
        }
        
        click(HOMEWORK_CREATE_BTN);
        return this;
    }

    public HomeworkFormPage fillHomeworkFields(String title, String due, String description) {
        return enterTitle(title).enterDue(due).enterDescription(description).uploadFile();
    }

    public void removeUploadedFile() {
        click(HOMEWORK_UPLOADED_FILE_REMOVE_BTN);
    }

    public void clickUpdate() {
        logger.info("Clicking homework update button");
        waitForVisibility(HOMEWORK_UPLOADED_FILE);

        // Wait for the info popup (Flutter snackbar) to disappear
        try {
            logger.info("Waiting for info popup to disappear...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.warn("Interrupted while waiting for popup", e);
        }
        
        hideKeyboardIfVisible();
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().descriptionContains(\"Güncelle\"))"));
        } catch (Exception e) {
            logger.warn("UiScrollable failed, falling back to manual scroll");
            scrollIntoView(HOMEWORK_UPDATE_BTN);
        }
        click(HOMEWORK_UPDATE_BTN);
    }

    public void clickUpdateCancel() {
        click(HOMEWORK_UPDATE_CANCEL_BTN);
    }
}
