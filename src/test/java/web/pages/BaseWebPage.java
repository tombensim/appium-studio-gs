package web.pages;

/**
 * Created by tom.ben-simhon on 1/11/2017.
 */
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("ALL")
public abstract class BaseWebPage {
    protected final AppiumDriver<MobileElement> driver;

    protected BaseWebPage(AppiumDriver driver){
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, 5, TimeUnit.SECONDS), this);
    }
    //public abstract void verifyPage();
}