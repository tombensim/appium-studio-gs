/*
 * Copyright (c) 2017. Experitest
 *
 */

package app.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;

/**
 * appium-gradle-getting-started
 * Created by tom.ben-simhon on 2/21/2017.
 */
public class MakePaymentPage extends BasePage {
    public static final String PHONE_NUMBER = "432432423423";
    public static final String NAME = "Experi name";
    public static final String AMOUNT = "100";

    @iOSFindBy(xpath = "//*[@accessibilityLabel='Phone']")
    @AndroidFindBy(xpath = "//*[@id='phoneTextField']")
    MobileElement phone;
    @iOSFindBy(xpath = "//*[@accessibilityLabel='Name']")
    @AndroidFindBy(xpath = "//*[@id='nameTextField']")
    MobileElement name;
    @iOSFindBy(xpath = "//*[@accessibilityLabel='Amount']")
    @AndroidFindBy(xpath = "//*[@id='amountTextField']")
    MobileElement amount;
    @AndroidFindBy(xpath = "//*[@id='cancelButton']")
    @iOSFindBy(xpath = "//*[@accessibilityLabel='cancelButton']")
    MobileElement cancel;

    public MakePaymentPage(AppiumDriver driver) {
        super(driver);
    }

    public boolean fillUserInfo()
    {
        phone.sendKeys(PHONE_NUMBER);
        name.sendKeys(NAME);
        amount.sendKeys(AMOUNT);
        cancel.click();
        return true;
    }
}
