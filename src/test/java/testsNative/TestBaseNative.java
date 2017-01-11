package testsNative;

import com.experitest.appium.SeeTestAndroidDriver;
import com.experitest.appium.SeeTestCapabilityType;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by tom.ben-simhon on 12/26/2016.
 * Test Base Class holds all members required to run a test, and details general capabilities
 * For the Driver
 */
public class TestBaseNative {


    protected String testName;
    protected String host;
    protected int port;
    protected String reportDirectory;
    protected String reportFormat;
    protected AppiumDriver<MobileElement> driver;

    private DesiredCapabilities caps;

    //Constructors
    public TestBaseNative(DesiredCapabilities caps){
        this.caps = caps;
        host = "localhost";
        port = 4723;
        reportDirectory = "reports";
        reportFormat = "xml";
        driver = null;

    }
    protected TestBaseNative(){
        this(null);
    }

    @BeforeTest
    public void setUp() throws MalformedURLException {
        caps = new DesiredCapabilities();
        // App Capabilities
//        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
//        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
        caps.setCapability(MobileCapabilityType.APP,System.getProperty("user.dir") + "\\eribank.apk");
        caps.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, false);
        caps.setCapability(MobileCapabilityType.NO_RESET, true);

        // Device Capabilities
        caps.setCapability(CapabilityType.PLATFORM,"android");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME,"Android Device");
        caps.setCapability(SeeTestCapabilityType.DEVICE_QUERY,"@name='LGE Nexus 5'");

//        caps.setCapability(SeeTestCapabilityType.DEVICE_QUERY, "@name='samsung SM-N9005'");

//        //Grid Connection configuration
//        caps.setCapability(SeeTestCapabilityType.USE_REMOTE_GRID,"true");
//        caps.setCapability(SeeTestCapabilityType.USERNAME, "tom");
//        caps.setCapability(SeeTestCapabilityType.PASSWORD, "xioN2401");
//        caps.setCapability(SeeTestCapabilityType.PROJECT_NAME, "Default");

        /* Appium Studio Additional
                 Here we place all of the additonal capabilites that will be used to automatically generate reports, and dynamicall select Device

        //Reporting Configurations
//        caps.setCapability(SeeTestCapabilityType.REPORT_DIRECTORY, reportDirectory);
//        caps.setCapability(SeeTestCapabilityType.REPORT_FORMAT, reportFormat);
//        caps.setCapability(SeeTestCapabilityType.TEST_NAME, testName);
          */

        //Driver initialization
        URL url =  new URL ("http://localhost:8889");
        driver = new SeeTestAndroidDriver<MobileElement>(url,caps);



    }
    @AfterTest
    public void tearDown()
    {

        if (driver!=null)
            driver.quit();
    }
}

