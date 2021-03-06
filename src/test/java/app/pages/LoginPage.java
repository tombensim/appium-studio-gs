/*
 * Copyright (c) 2017. Experitest
 *
 */

package app.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;


import java.util.NoSuchElementException;


public class LoginPage extends BasePage{

    public static final String VALID_USERNAME = "company";
    public static final String VALID_PASSWORD = "company";
    //Reference Objects
    @AndroidFindBy(id = "usernameTextField")
    @iOSFindBy (accessibility = "usernameTextField")
    protected MobileElement usernameField;
    @AndroidFindBy(id = "passwordTextField")
    @iOSFindBy (accessibility = "passwordTextField")
    protected MobileElement passwordField;
    @AndroidFindBy(xpath = "//*[@text='Login']")
    @iOSFindBy (xpath = "//*[@text='Login']")
    protected MobileElement loginButton;
    
    public LoginPage(AppiumDriver driver)
    {
        super(driver);
	}
    // Page methods
    /**
     Tries to login to the application
    -  @params Creds, Username and Password
    **/
    public void login(String username, String password) throws NoSuchElementException, InterruptedException {
   		usernameField.sendKeys(username);
        passwordField.sendKeys(password);
		loginButton.click();
		Thread.sleep(1000);
    }

    public HomePage loginToHomePage()
    {
        usernameField.sendKeys(VALID_USERNAME);
        passwordField.sendKeys(VALID_PASSWORD);
        loginButton.click();
        return new HomePage(driver);
    }

    public boolean closeFailDialog()
    {
        MobileElement close = driver.findElement(By.xpath("//*[contains(@text,'Close') or contains(@text,'Dismiss')]"));
        close.click();
        return true;
    }

}
