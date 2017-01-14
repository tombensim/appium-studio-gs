/*
 * Copyright (c) 2017. Experitest
 *
 */

package framework;

import com.experitest.appium.SeeTestAndroidDriver;
import com.experitest.appium.SeeTestCapabilityType;
import com.experitest.appium.SeeTestIOSDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.log4testng.Logger;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
@Listeners(LogListener.class)
public class TestBase {

//    protected String testName;
//    protected String host;
//    protected int port;
//    protected String reportDirectory;
//    protected String reportFormat;

    private static final Logger LOGGER = Logger.getLogger(TestBase.class);

    protected AppiumDriver<MobileElement> driver;
    String appPath = System.getProperty("user.dir") + File.separator +
            "apps" + File.separator + "eribank";
    private DesiredCapabilities caps;
    private String suite;

    @Parameters({"os", "grid"})
    @BeforeTest
    public void setUp(Method m,@Optional("android") String os, @Optional("false") boolean grid,
                      @Optional("native") String suite)
            throws MalformedURLException {

        Reporter.log("message",true);
        appPath = os.equals("android") ? appPath + ".apk" : appPath + ".ipa";
        caps = new DesiredCapabilities();
        System.out.println(this.suite);
        // Device Capabilities
        caps.setCapability(CapabilityType.PLATFORM, os);
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Device");

        // Application Capabilities
        caps.setCapability(MobileCapabilityType.APP, appPath);
        caps.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, false);

        //Grid Connection configuration
        if (grid) {
            caps.setCapability(SeeTestCapabilityType.USE_REMOTE_GRID, "true");
            caps.setCapability(SeeTestCapabilityType.USERNAME, "tom");
            caps.setCapability(SeeTestCapabilityType.PASSWORD, "xioN2401");
            caps.setCapability(SeeTestCapabilityType.PROJECT_NAME, "Default");
        }

        //Driver initialization
        URL url = new URL("http://localhost:8889");
        driver = os.equals("android") ?
                new SeeTestAndroidDriver<MobileElement>(url, caps) : new SeeTestIOSDriver<MobileElement>(url, caps);
    }

    @AfterTest
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }

    @Test(dataProvider = "suiteType")
    public void getSuite(String suiteName) {
        this.suite = suiteName;
    }

    @DataProvider(name = "suiteType")
    public static Object[][] getTestTypeData(ITestContext context) {
        String suiteName = context.getClass().getPackage().getName();
        return new Object[][]{{suiteName}};
    }
}




