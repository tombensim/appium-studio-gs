/*
 * Copyright (c) 2017. Experitest
 *
 */

package app.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{

	// Reference Objects
	@FindBy(xpath = "//*[@text='Make Payment']")
	private MobileElement makePayment;
	@FindBy(xpath = "//*[contains(@text,'Morgtage')]")
    private MobileElement mortgageRequest;
	@FindBy(xpath = "//*[contains(@text,'Expense')]")
    private MobileElement exspenseReport;
	@FindBy(xpath = "//*[contains(@text,'Logout')]")
    private MobileElement logoutButton;
	private WebElement balanceInfo;

	public HomePage(AppiumDriver driver) {
		super(driver);

	}

	public MakePaymentPage navigateToMakePage(){
		makePayment.click();
		return new MakePaymentPage(driver);
	}

    public boolean logout() throws InterruptedException {
			logoutButton.click();
			return true;
    }

    public String getBalance()
	{
        driver.context("WEBVIEW_1");
        balanceInfo = driver.findElement(By.xpath("//*[contains(@text,'balance')]"));
        driver.context("NATIVE_APP");
		return balanceInfo.getText();
	}
    
}
