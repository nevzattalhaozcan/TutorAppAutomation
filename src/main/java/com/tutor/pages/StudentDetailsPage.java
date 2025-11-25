package com.tutor.pages;

import com.tutor.base.ConfigReader;
import com.tutor.data.StudentTabs;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class StudentDetailsPage extends BasePage {

    private static final By STUDENT_INFO_TAB = AppiumBy.accessibilityId("Bilgiler\\nSekme 1 / 3");
    private static final By STUDENT_HOMEWORKS_TAB = AppiumBy.accessibilityId("Ödevler\\nSekme 2 / 3");
    private static final By STUDENT_APPOINTMENTS_TAB = AppiumBy.accessibilityId("Randevular\\nSekme 3 / 3");
    private static final By CREATE_HOMEWORK_BTN = AppiumBy.accessibilityId("Yeni Ödev");
    private static final By STUDENT_DETAILS_MENU = AppiumBy.accessibilityId("Menüyü göster");
    private static final By STUDENT_EDIT_OPTION = AppiumBy.accessibilityId("Düzenle");
    private static final By STUDENT_DELETE_OPTION = AppiumBy.accessibilityId("Sil");
    private static final By STUDENT_DELETE_CONFIRM_BTN = AppiumBy.xpath("//android.widget.Button[@content-desc=\"Sil\"]");
    private static final By STUDENT_DELETE_CANCEL_BTN = AppiumBy.xpath("//android.widget.Button[@content-desc=\"İptal\"]");
    private static final By CREATE_APPOINTMENT_BTN = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(2)");
    private static final By CREATE_SINGLE_APPOINTMENT_BTN = AppiumBy.accessibilityId("Randevu Ekle");
    private static final By CREATE_WEEKLY_APPOINTMENT_BTN = AppiumBy.accessibilityId("Haftalık Kalıp");
    private static final By CREATE_SESSIONS_BTN = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.Button\").instance(2)");
    private static final By ADD_SINGLE_SESSION_BTN = AppiumBy.accessibilityId("Randevu Ekle");
    private static final By ADD_WEEKLY_SESSION_BTN = AppiumBy.accessibilityId("Haftalık Kalıp");
    private static final By CHOOSE_DATE_BTN = AppiumBy.accessibilityId("Tarih seçin");
    private static final By MANUEL_DATE_BTN = AppiumBy.accessibilityId("Girişe geç");
    private static final By MANUEL_DATE_INPUT_BX = AppiumBy.xpath("//android.widget.EditText[@hint=\"Tarih Girin\"]");
    private static final By MANUEL_DATE_CONFIRM_BTN = AppiumBy.accessibilityId("Tamam");
    private static final By CHOOSE_START_TIME_BTN = AppiumBy.androidUIAutomator("new UiSelector().description(\"Saat seçin\").instance(0)");
    private static final By CHOOSE_END_TIME_BTN = AppiumBy.androidUIAutomator("new UiSelector().description(\"Saat seçin\").instance(1)");
    private static final By MANUEL_TIME_BTN = AppiumBy.accessibilityId("Metin giriş moduna geç");
    private static final By MANUEL_TIME_HOUR_INPUT_BX = AppiumBy.xpath("//android.widget.EditText[@hint=\"Saat\"]");
    private static final By MANUEL_TIME_MINUTE_INPUT_BX = AppiumBy.xpath("//android.widget.EditText[@hint=\"Dakika\"]");
    private static final By MANUEL_TIME_CONFIRM_BTN = AppiumBy.accessibilityId("Tamam");
    private static final By SESSION_IN_PERSON_RADIO_BTN = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.RadioButton\").instance(0)");
    private static final By SESSION_ONLINE_RADIO_BTN = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.RadioButton\").instance(1)");
    private static final By SESSION_NOTE_TXT_AREA = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")");
    private static final By CREATE_SESSION_BTN = AppiumBy.accessibilityId("Randevu Oluştur");
    private static final By CANCEL_SESSION_BTN = AppiumBy.accessibilityId("İptal");
    private static final By SESSION_LINK_INPUT_BX = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)");
    private static final By HOMEWORK_EDIT_BTN = AppiumBy.androidUIAutomator("new UiSelector().description(\"Düzenle\")");
    private static final By HOMEWORK_DELETE_BTN = AppiumBy.androidUIAutomator("new UiSelector().description(\"Sil\")");
    private static final By HOMEWORK_DELETE_CONFIRM_BTN = AppiumBy.androidUIAutomator("new UiSelector().description(\"Sil\")");
    private static final By HOMEWORK_DELETE_CANCEL_BTN = AppiumBy.androidUIAutomator("new UiSelector().description(\"İptal\")");

    public StudentDetailsPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
        logger.info("StudentDetailsPage initialized");
    }

    public boolean isHomeworkOnStudentDetails(String keyword) {
        logger.info("Getting homework details on student details page...");
        return isElementDisplayed(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\""+ keyword +"\")"));
    }

    public void clickInfoTab() {
        logger.info("Clicking on info tab");
        click(STUDENT_INFO_TAB);
    }

    public void clickHomeworksTab() {
        logger.info("Clicking on homeworks tab");
        click(STUDENT_HOMEWORKS_TAB);
    }

    public void clickSessionTab() {
        logger.info("Clicking on session tab");
        click(STUDENT_APPOINTMENTS_TAB);
    }

    public void clickCreateHomework() {
        logger.info("Clicking on create homework button");
        scrollIntoView(CREATE_HOMEWORK_BTN);
        click(CREATE_HOMEWORK_BTN);
    }

    public void clickStudent(String student) {
        String[] fullName = student.split(" ");
        if (fullName.length <= 1) { throw new IllegalArgumentException("Provide full name"); }
        StringBuilder nameForLocator = new StringBuilder();
        for (String name : fullName) {
            nameForLocator.append(" ").append(name);
        }
        click(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\""+ nameForLocator +"\")"));
    }

    public void clickStudent() {
        String testStudentName = ConfigReader.getProperty("test.student.name");
        click(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\""+ testStudentName +"\")"));
        
        // Wait for student details page to load
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            logger.warn("Sleep interrupted", e);
        }
    }

    public void goToStudentTab(StudentTabs tab) {
        int infoX = 210;
        int homeworksX = 650;
        int sessionsX = 1070;
        int tabsY = 450;
        switch (tab) {
            case BİLGİLER -> tapAt(infoX, tabsY);
            case ÖDEVLER -> tapAt(homeworksX, tabsY);
            case RANDEVULAR -> tapAt(sessionsX, tabsY);
        }
        
        // Wait for tab content to load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.warn("Sleep interrupted", e);
        }
    }

    public void openSingleSessionCreateModal() {
        click(CREATE_SESSIONS_BTN);
        click(ADD_SINGLE_SESSION_BTN);
    }

    public void openSWeeklySessionCreateModal() {
        click(CREATE_SESSIONS_BTN);
        click(ADD_WEEKLY_SESSION_BTN);
    }

    public void fillSingleSessionForm(String date, String startTime, String endTime, boolean online, String link, String note) {
        enterDateForSession(date);

        enterStartTimeForSession(startTime);

        enterEndTimeForSession(endTime);

        if (online) {
            chooseOnlineAndEnterLinkForSession(link);
        }

        enterNoteForSession(note);

        clickCreateSessionButton();
    }

    public void enterDateForSession(String date) {
        click(CHOOSE_DATE_BTN);
        click(MANUEL_DATE_BTN);
        enterText(MANUEL_DATE_INPUT_BX, date);
        click(MANUEL_DATE_CONFIRM_BTN);
    }

    public void enterStartTimeForSession(String startTime) {
        click(CHOOSE_START_TIME_BTN);
        click(MANUEL_TIME_BTN);
        enterText(MANUEL_TIME_HOUR_INPUT_BX, startTime.substring(0,2));
        enterText(MANUEL_TIME_MINUTE_INPUT_BX, startTime.substring(3,5));
        click(MANUEL_TIME_CONFIRM_BTN);
    }

    public void enterEndTimeForSession(String endTime) {
        logger.info("Trying to enter end time");
        //click(CHOOSE_END_TIME_BTN);
        tapAt(900, 1400);


        click(MANUEL_TIME_BTN);
        enterText(MANUEL_TIME_HOUR_INPUT_BX, endTime.substring(0,2));
        enterText(MANUEL_TIME_MINUTE_INPUT_BX, endTime.substring(3,5));
        click(MANUEL_TIME_CONFIRM_BTN);
    }

    public void chooseOnlineAndEnterLinkForSession(String link) {
        click(SESSION_ONLINE_RADIO_BTN);
        enterText(SESSION_LINK_INPUT_BX, link);
    }

    public void enterNoteForSession(String note) {
        enterText(SESSION_NOTE_TXT_AREA, note);
    }

    public void clickCreateSessionButton() {
        click(CREATE_SESSION_BTN);
    }

    public void clickOnHomework(String keyword) {
        logger.info("Clicking on the homework for keyword: {}", keyword);
        click(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\""+ keyword +"\")"));
    }

    public void clickHomeworkEdit() {
        click(HOMEWORK_EDIT_BTN);
    }

    public void clickHomeworkDelete() {
        click(HOMEWORK_DELETE_BTN);
    }

    public void clickDeleteConfirm() {
        click(HOMEWORK_DELETE_CONFIRM_BTN);
    }

    public void clickDeleteCancel() {
        click(HOMEWORK_DELETE_CANCEL_BTN);
    }

    public void openHomeworkUpdateForm() {
        clickHomeworkEdit();
    }
}
