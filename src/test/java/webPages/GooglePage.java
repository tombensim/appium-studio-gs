/*
 * Copyright (c) 2017. Experitest
 *
 */

package webPages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by tom.ben-simhon on 1/11/2017.
 */


public class GooglePage extends BaseWebPage {

    @FindBy(xpath = "//*[@name='q']")
    WebElement input;

    public GooglePage(AppiumDriver driver) {
        super(driver);
    }

    public boolean search (String searchItem)
    {
        driver.context("WEBVIEW_1");
        input.sendKeys(searchItem);
        driver.findElementByXPath("//*[@id='tsbb']").click();
        return true;
    }

}
