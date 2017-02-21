/*
 * Copyright (c) 2017. Experitest
 *
 */

package app.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AppiumStudioClient;
import utils.Utils;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public abstract class BasePage {
    public static final int TIME_OUT_WAIT_IN_SECONDS = 10;

    protected final AppiumDriver<MobileElement> driver;
    protected final AppiumStudioClient apc;
    protected final WebDriverWait wda;

    protected BasePage(AppiumDriver driver){
        this.driver = driver;
        apc = Utils.getAppiumClient(driver);
        wda = new WebDriverWait(driver, TIME_OUT_WAIT_IN_SECONDS);
        Utils.setLogger(driver,Utils.getLogger(false));
        PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
        Utils.setLogger(driver,Utils.getLogger(true));
    }
    //public abstract void verifyPage();
}
