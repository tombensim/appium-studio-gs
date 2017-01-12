/*
 * Copyright (c) 2017. Experitest
 *
 */

package app;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.app.LoginPage;

/**
 * Created by tom.ben-simhon on 12/27/2016.
 */
public class LoginTest extends TestBaseNative {
    //Class private members
    private static final String VALID_USER = "company";
    private static final String VALID_PASSWORD = "company";
    private static final String INVALID_PASSWORD = "invalid";
    private static final String INVALID_USER = "invalid";

    private LoginPage loginPage = null;
    @BeforeTest
    public void setUpLoginTests() {
        loginPage = new LoginPage(driver);
    }
    @Test
    public void testLoginWithValidCreds()
    {
        loginPage.login(VALID_USER,VALID_PASSWORD);
    }
    @Test
    public void testFailToLoginWithInvalidCreds()
    {
        loginPage.login(INVALID_USER,INVALID_PASSWORD);
    }
    @AfterTest
    public void tearDownLoginTests()
    {
        driver.quit();
    }
}
