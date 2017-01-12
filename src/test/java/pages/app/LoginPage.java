/*
 * Copyright (c) 2017. Experitest
 *
 */

package pages.app;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;


import java.util.NoSuchElementException;


public class LoginPage extends BasePage{

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
    
    public LoginPage(AppiumDriver driver) {
		super(driver);
	}
    // Page methods

    /*
     Tries to login to the application
    -  @params Creds, Username and Password
    */
    public void login(String username, String password) throws NoSuchElementException{
   		usernameField.sendKeys(username);
        passwordField.sendKeys(password);
		loginButton.click();
    }

}
