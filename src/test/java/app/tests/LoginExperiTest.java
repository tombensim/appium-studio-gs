/*
 * Copyright (c) 2017. Experitest
 *
 */

package app.tests;

import app.pages.HomePage;
import framework.ExperiTestBase;
import org.testng.Assert;
import org.testng.annotations.*;
import app.pages.LoginPage;

/**
 * Created by tom.ben-simhon on 12/27/2016.
 * consolidates login functionallity tests
 */

public class LoginExperiTest extends EribankTestBase {

    private static final String VALID_USER = "company";
    private static final String VALID_PASSWORD = "company";
    private static final String INVALID_PASSWORD = "invalid";
    private static final String INVALID_USER = "invalid";

    private LoginPage loginPage = null;

    @BeforeMethod
    public void setUpLoginTests() {
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testLoginWithValidCreds() throws InterruptedException {
        loginPage.login(VALID_USER, VALID_PASSWORD);
        HomePage homePage = new HomePage(driver);
        driver.context("WEBVIEW_1");
        Assert.assertTrue(homePage.getBalance().contains("$"), "Failed to reached the app home page");
    }

    @Test
    public void testFailToLoginWithInvalidCreds() throws InterruptedException {
        loginPage.login(INVALID_USER, INVALID_PASSWORD);
        Assert.assertTrue(loginPage.closeFailDialog(), "Failed to close wrong password dialog");
    }
}
