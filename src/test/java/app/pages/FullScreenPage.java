/*
 * Copyright (c) 2017. Experitest
 *
 */

package app.pages;

import app.pages.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import java.util.List;

/**
 * appium-gradle-getting-started
 * Created by tom.ben-simhon on 2/14/2017.
 */
public class FullScreenPage  extends BasePage{
    @AndroidFindBy(xpath = "//*[@class='android.widget.Button']")
    List<MobileElement> buttons;
    public FullScreenPage(AppiumDriver driver)
    {
        super(driver);
    }

    public  void clickOnButton()
    {

        for (MobileElement button : buttons)
            button.getAttribute("bounds");
        buttons.get(0).click();
    }


}
