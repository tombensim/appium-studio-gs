/*
 * Copyright (c) 2017. Experitest
 *
 */

package pages.web;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * Created by tom.ben-simhon on 1/11/2017.
 */


public class GooglePage extends BaseWebPage {

    @FindBy(xpath = "//*[@id='lst-ib']")
    WebElement input;
    EventFiringWebDriver eventListnerer;

    public GooglePage(AppiumDriver driver) {
        super(driver);
//        eventListnerer.register(new AppiumWebDriverEventListener() {
//            @Override
//            public void beforeNavigateRefresh(WebDriver driver) {
//
//            }
//
//            @Override
//            public void afterNavigateRefresh(WebDriver driver) {
//
//            }
//
//            @Override
//            public void beforeNavigateTo(String s, WebDriver webDriver) {
//
//            }
//
//            @Override
//            public void afterNavigateTo(String s, WebDriver webDriver) {
//
//            }
//
//            @Override
//            public void beforeNavigateBack(WebDriver webDriver) {
//
//            }
//
//            @Override
//            public void afterNavigateBack(WebDriver webDriver) {
//
//            }
//
//            @Override
//            public void beforeNavigateForward(WebDriver webDriver) {
//
//            }
//
//            @Override
//            public void afterNavigateForward(WebDriver webDriver) {
//
//            }
//
//            @Override
//            public void beforeFindBy(By by, WebElement webElement, WebDriver webDriver) {
//
//            }
//
//            @Override
//            public void afterFindBy(By by, WebElement webElement, WebDriver webDriver) {
//
//            }
//
//            @Override
//            public void beforeClickOn(WebElement webElement, WebDriver webDriver) {
//
//            }
//
//            @Override
//            public void afterClickOn(WebElement webElement, WebDriver webDriver) {
//                webDriver.findElement(By.xpath("//*[@text='Looking for results in English?']"))
//            }
//
//            @Override
//            public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver) {
//
//            }
//
//            @Override
//            public void afterChangeValueOf(WebElement webElement, WebDriver webDriver) {
//
//            }
//
//            @Override
//            public void beforeScript(String s, WebDriver webDriver) {
//
//            }
//
//            @Override
//            public void afterScript(String s, WebDriver webDriver) {
//
//            }
//
//            @Override
//            public void onException(Throwable throwable, WebDriver webDriver) {
//
//            }
//        });
    }

    public boolean search(String searchItem) {
        driver.context("WEBVIEW_1");
        input.sendKeys(searchItem);
        driver.findElementByXPath("//*[@id='tsbb']").click();
        return true;
    }

}
