/*
 * Copyright (c) 2017. Experitest
 *
 * Created by tom.ben-simhon on 12/26/2016.
 * Test Base Class holds all members required to run a test, and details general capabilities
 * For the Driver

 */

import com.experitest.appium.SeeTestAndroidDriver;
import com.experitest.appium.SeeTestCapabilityType;
import com.experitest.appium.SeeTestIOSDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
public class TestBase {
        protected String testName;
        protected String host;
        protected int port;
        protected String reportDirectory;
        protected String reportFormat;
        protected AppiumDriver<MobileElement> driver;
        String appPath = System.getProperty("user.dir") + File.separator +
                "apps" + File.separator + "eribank";
        private DesiredCapabilities caps;

        @Parameters({"os","grid","instrument"})
        @BeforeTest
        public void setUp(@Optional("android") String os, @Optional("false") boolean grid,
                    @Optional("false") boolean instrument)
                throws MalformedURLException {

            appPath = os.equals("android") ? appPath + ".apk" : appPath + ".ipa";
            caps = new DesiredCapabilities();

            // Device Capabilities
            caps.setCapability(CapabilityType.PLATFORM,os);
            caps.setCapability(MobileCapabilityType.DEVICE_NAME,"Device");

            // Application Capabilities
            caps.setCapability(MobileCapabilityType.APP, appPath);
            caps.setCapability(SeeTestCapabilityType.INSTRUMENT_APP,instrument );

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
            driver = os.equals("android") ?
                    new SeeTestAndroidDriver<MobileElement>(url,caps) : new SeeTestIOSDriver<MobileElement>(url,caps);




        }
        @AfterTest
        public void tearDown()
        {
            if (driver!=null)
                driver.quit();
        }
    }




