/*
 * Copyright (c) 2017. Experitest
 *
 */

package app.tests;

import framework.TestBase;
import org.testng.annotations.*;
import app.pages.LoginPage;

/**
 * Created by tom.ben-simhon on 12/27/2016.
 */


public class LoginTest extends TestBase {
    //Class private members
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
        loginPage.login(VALID_USER,VALID_PASSWORD);
    }
    @Test
    public void testFailToLoginWithInvalidCreds() throws InterruptedException {
        loginPage.login(INVALID_USER,INVALID_PASSWORD);
    }
    @AfterMethod
    public void tearDownLoginTests()
    {
        driver.quit();
    }
}