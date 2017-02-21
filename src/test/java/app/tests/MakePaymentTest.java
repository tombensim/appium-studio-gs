/*
 * Copyright (c) 2017. Experitest
 *
 */

package app.tests;

import app.pages.HomePage;
import app.pages.LoginPage;
import app.pages.MakePaymentPage;
import framework.TestBase;
import org.testng.annotations.Test;

/**
 * appium-gradle-getting-started
 * Created by tom.ben-simhon on 2/21/2017.
 */
public class MakePaymentTest extends TestBase {
    public static final String VALID_PASSWORD = "company";
    public static final String VALID_USER = "company";
    LoginPage loginPage;
    MakePaymentPage makePaymentPage;
    HomePage homePage;
    @Test
    public void testMakePayment() throws InterruptedException {
            driver.launchApp();
            loginPage = new LoginPage(driver);
            homePage = loginPage.loginToHomePage();
            makePaymentPage = homePage.navigateToMakePage();
            makePaymentPage.fillUserInfo();
    }

}
