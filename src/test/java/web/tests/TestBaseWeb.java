package web.tests;//package <set your test package>;
import com.experitest.appium.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;

import java.net.URL;
import java.net.MalformedURLException;

public class TestBaseWeb {
    private String host = "localhost";
    private int port = 8889;

    private String reportDirectory = "reports";
    private String reportFormat = "xml";
    private String testName = "untitled";
    protected AppiumDriver<MobileElement> driver = null;

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(SeeTestCapabilityType.REPORT_DIRECTORY, reportDirectory);
        capabilities.setCapability(SeeTestCapabilityType.REPORT_FORMAT, reportFormat);
        capabilities.setCapability(SeeTestCapabilityType.TEST_NAME, testName);
        capabilities.setCapability(SeeTestCapabilityType.DEVICE_QUERY, "@name='_iPhone6s-Discover'");
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,"safari");
        capabilities.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        driver = new SeeTestIOSDriver<MobileElement>(new URL("http://"+host+":"+port), capabilities);
        driver.get("http://www.google.com");
    }


    @After
    public void tearDown() {
        driver.quit();
    }
}