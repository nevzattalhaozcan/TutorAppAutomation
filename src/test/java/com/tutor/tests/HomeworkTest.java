package com.tutor.tests;

import com.tutor.base.BaseTest;
import com.tutor.data.StudentTabs;
import com.tutor.pages.HomePage;
import com.tutor.pages.HomeworkFormPage;
import com.tutor.pages.LoginPage;
import com.tutor.pages.StudentDetailsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomeworkTest extends BaseTest {

    private LoginPage loginPage;
    private StudentDetailsPage studentDetailsPage;
    private HomeworkFormPage homeworkFormPage;
    String homeworkTitle = "Otomasyon Ödev Testi - " + System.currentTimeMillis();

    @BeforeMethod(dependsOnMethods = "setUp")
    public void initializeLoginPage() {
        loginPage = new LoginPage(driver);
        studentDetailsPage = new StudentDetailsPage(driver);
        homeworkFormPage = new HomeworkFormPage(driver);
        loginPage.waitForPageLoad();
    }

    @Test(enabled = true)
    public void createHomework() {
        HomePage homePage = loginPage.login();
        homePage.goToStudents();
        studentDetailsPage.clickStudent();
        studentDetailsPage.goToStudentTab(StudentTabs.ÖDEVLER);
        studentDetailsPage.clickCreateHomework();
        homeworkFormPage.fillHomeworkFields(homeworkTitle, "02.12.2025", "Bu ödev Appium otomasyonu ile oluşturuldu.");
        homeworkFormPage.clickCreate();
        Assert.assertTrue(studentDetailsPage.isHomeworkOnStudentDetails(homeworkTitle));
    }

    @Test(enabled = true)
    public void updateHomework() {
        HomePage homePage = loginPage.login();
        homePage.goToStudents();
        studentDetailsPage.clickStudent();
        studentDetailsPage.goToStudentTab(StudentTabs.ÖDEVLER);
        studentDetailsPage.clickOnHomework(homeworkTitle);
        studentDetailsPage.clickHomeworkEdit();
        homeworkTitle = "Güncellenmiş Otomasyon Ödev Testi - " + System.currentTimeMillis();
        homeworkFormPage.fillHomeworkFields(homeworkTitle, "01.12.2025", "Bu ödev Appium otomasyonu ile oluşturuldu. Güncellendi.");
        homeworkFormPage.clickUpdate();
        Assert.assertTrue(studentDetailsPage.isHomeworkOnStudentDetails(homeworkTitle));
    }

    @Test(enabled = true, dependsOnMethods = "updateHomework")
    public void deleteHomework() {
        HomePage homePage = loginPage.login();
        homePage.goToStudents();
        studentDetailsPage.clickStudent();
        studentDetailsPage.goToStudentTab(StudentTabs.ÖDEVLER);
        studentDetailsPage.clickOnHomework(homeworkTitle);
        studentDetailsPage.clickHomeworkDelete();
        studentDetailsPage.clickDeleteConfirm();
        Assert.assertFalse(studentDetailsPage.isHomeworkOnStudentDetails(homeworkTitle));
    }
}
