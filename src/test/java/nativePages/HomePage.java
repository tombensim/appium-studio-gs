package nativePages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class HomePage extends BasePage{

	// Reference Objects
	@AndroidFindBy (xpath = "//*[@text='Make Payment']")
	private MobileElement makePayment;
	@AndroidFindBy(xpath = "//*[contains(@text,'Morgtage')]")
    private MobileElement mortgageRequest;
	@AndroidFindBy(xpath = "//*[contains(@text,'Expense')]")
    private MobileElement exspenseReport;
	@AndroidFindBy(xpath = "//*[contains(@text,'Logout')]")
    private MobileElement logoutButton;

	public HomePage(AppiumDriver driver) {
		super(driver);
	}
	
    public LoginPage logout() throws InterruptedException {
			logoutButton.click();
			return new LoginPage(driver);
    }

    
}
