package com.tutor.tests;

import com.tutor.base.BaseTest;
import com.tutor.data.StudentTabs;
import com.tutor.pages.HomePage;
import com.tutor.pages.LoginPage;
import com.tutor.pages.StudentDetailsPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AppointmentTest extends BaseTest {

    private LoginPage loginPage;
    private StudentDetailsPage studentDetailsPage;

    @BeforeMethod(dependsOnMethods = "setUp")
    public void initializeLoginPage() {
        loginPage = new LoginPage(driver);
        studentDetailsPage = new StudentDetailsPage(driver);
        loginPage.waitForPageLoad();
    }

    @Test(enabled = true)
    public void createSession() {
        HomePage homePage = loginPage.login();
        homePage.goToStudents();
        studentDetailsPage.clickStudent();
        studentDetailsPage.goToStudentTab(StudentTabs.RANDEVULAR);
        studentDetailsPage.openSingleSessionCreateModal();
        studentDetailsPage.fillSingleSessionForm("25.11.2025", "10.00", "11.00", false,"","");
    }
}
