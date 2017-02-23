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
import utils.STDUtils;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public abstract class BasePage {
    public static final int TIME_OUT_WAIT_IN_SECONDS = 10;
    public static final int TIME_WAIT_BEFORE_INITELEMENTS = 1000; //In millies

    protected final AppiumDriver<MobileElement> driver;
    protected final AppiumStudioClient apc;
    protected final WebDriverWait wda;

    protected BasePage(AppiumDriver driver){
        this.driver = driver;
        apc = STDUtils.getAppiumClient(driver);
        wda = new WebDriverWait(driver, TIME_OUT_WAIT_IN_SECONDS);
        apc.sleep(TIME_WAIT_BEFORE_INITELEMENTS);
        PageFactory.initElements(new AppiumFieldDecorator(driver, 10, TimeUnit.SECONDS), this);
    }
    //public abstract void verifyPage();
}
