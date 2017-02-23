/*
 * Copyright (c) 2017. Experitest
 *
 */

package app.tests;

import app.pages.HomePage;
import app.pages.LoginPage;
import app.pages.MakePaymentPage;
import framework.ExperiTestBase;
import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * appium-gradle-getting-started
 * Created by tom.ben-simhon on 2/21/2017.
 * consolidates all tests that has to do with make payment app functionality
 */
public class MakePaymentExperiTest extends ExperiTestBase {
    LoginPage loginPage;
    MakePaymentPage makePaymentPage;
    HomePage homePage;

    @Test
    public void cancelMakePaymentForm() throws InterruptedException {
        driver.launchApp();
        loginPage = new LoginPage(driver);
        homePage = loginPage.loginToHomePage();
        String balance = homePage.getBalance();
        makePaymentPage = homePage.navigateToMakePage();
        makePaymentPage.fillUserInfo();
        homePage = makePaymentPage.cancel();
        Assert.assertEquals("Balance before and after shouldd equal",homePage.getBalance(),balance);
    }
}
