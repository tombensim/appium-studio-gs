/*
 * Copyright (c) 2017. Experitest
 *
 */

package hybrid;

import junit.framework.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.app.HomePage;
import pages.app.LoginPage;

/**
 * Created by tom.ben-simhon on 1/12/2017.
 */

public class BalanceTest extends TestBaseHybrid{
    String username = "company";
    String password = "company";

    @Test(dependsOnMethods = { "loginWithValidCreds" })
    public void testBalanceVisible() throws Exception {
        HomePage homePage = new HomePage(this.driver);
        System.out.println(homePage.getBalance());
        Assert.assertTrue(homePage.getBalance().contains("$"));
    }
    @Test
    public void loginWithValidCreds () throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username,password);
    }
}
