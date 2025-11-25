package com.tutor.pages;

import com.tutor.base.ConfigReader;
import com.tutor.data.MenuOptions;
import com.tutor.data.StudentTabs;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);
    public static final String WELCOME_ACCESSIBILITY_ID = "Hoş geldiniz,";
    public static final String USERS_NAME = ConfigReader.getProperty("test.user.name").replace("i","ı");
    public static final By WELCOME_TEXT = AppiumBy.accessibilityId(USERS_NAME);
    private static final By MENU_BTN = AppiumBy.accessibilityId("Gezinme menüsünü aç");
    private static final By MENU_HOME_OPT = AppiumBy.accessibilityId("Ana Sayfa\\nSekme 1 / 9");
    private static final By MENU_STUDENTS_OPT = AppiumBy.accessibilityId("Öğrenciler\\nSekme 2 / 9");
    private static final By MENU_SESSIONS_OPT = AppiumBy.accessibilityId("Randevular\\nSekme 3 / 9");
    private static final By MENU_CALENDAR_OPT = AppiumBy.accessibilityId("Takvim\\nSekme 4 / 9");
    private static final By MENU_ANALYTIC_OPT = AppiumBy.accessibilityId("Analitik\\nSekme 5 / 9");
    private static final By MENU_NOTES_OPT = AppiumBy.accessibilityId("Notlar\\nSekme 6 / 9");
    private static final By MENU_MESSAGES_OPT = AppiumBy.accessibilityId("Mesajlar\\nSekme 7 / 9");
    private static final By MENU_NOTIFICATIONS_OPT = AppiumBy.accessibilityId("Bildirimler\\nSekme 8 / 9");
    private static final By MENU_SETTINGS_OPT = AppiumBy.accessibilityId("Ayarlar\\nSekme 9 / 9");
    private static final By MENU_VERSION_TXT = AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Sürüm\")");


    public HomePage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        logger.info("HomePage initialized");
    }

    public String getWelcomeText() {
        logger.info("Getting welcome text");
        String text = getText(WELCOME_TEXT);
        return text == null ? "" : text.trim();
    }

    public HomePage waitForPageLoad() {
        waitForVisibility(WELCOME_TEXT);
        logger.info("HomePage loaded successfully");
        return this;
    }

    public void clickOnMenu() {
        click(MENU_BTN);
        logger.info("Clicked on menu button");
    }

    public void clickMenuOption(MenuOptions option) {
        switch (option) {
            case ANASAYFA:
                click(MENU_HOME_OPT);
                break;
            case ÖĞRENCİLER:
                click(MENU_STUDENTS_OPT);
                break;
            case RANDEVULAR:
                click(MENU_SESSIONS_OPT);
                break;
            case TAKVİM:
                click(MENU_CALENDAR_OPT);
                break;
            case ANALİTİK:
                click(MENU_ANALYTIC_OPT);
                break;
            case NOTLAR:
                click(MENU_NOTES_OPT);
                break;
            case MESAJLAR:
                click(MENU_MESSAGES_OPT);
                break;
            case BİLDİRİMLER:
                click(MENU_NOTIFICATIONS_OPT);
                break;
            case AYARLAR:
                click(MENU_SETTINGS_OPT);
                break;
            default:
                throw new IllegalArgumentException("No such menu option: " + option);
        }
        logger.info("Clicked on {}", option);
    }

    public void goToStudents() {
        clickOnMenu();
        
        // Wait for menu animation to complete
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.warn("Sleep interrupted", e);
        }

        //clickMenuOption(MenuOptions.ÖĞRENCİLER);
        int studentsX = 300;
        int studentsY = 950;
        tapAt(studentsX,studentsY);
        logger.info("Tapped at ({},{}) to go to Students screen", studentsX, studentsY);
        
        // Wait for students page to load
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            logger.warn("Sleep interrupted", e);
        }
    }


}
