/*
 * Copyright (c) 2017. Experitest
 *
 */

package app;

import com.experitest.appium.*;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import framework.LogListener;
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

/**
 * Created by tom.ben-simhon on 12/26/2016.
 * Test Base Class holds all members required to run a test, and details general capabilities
 * For the Driver
 */

@Listeners(LogListener.class)
public class TestBaseNative {

    public static final  String  AFTER_METHOD      = "Finished Execution for test method : %s";
    public static final  String  BEFORE_METHOD     = "Executing Test Method : %s \n";
    public static final  String  BEFORE_TEST_CLASS = "Starting test execution for mesthods in class %s  \n";
    public static final  String  DEFAULT_SUITE_OS  = "android";
    public static final  boolean LOG_TO_SOUT       = true;
    //Appium Studio parameters
    public static final  String  SERVER_URL        = "http://localhost:8889";
    // Logger configurations
    private static final Logger  LOGGER            = Logger.getLogger (TestBaseNative.class);
    public static final  String  USERNAME          = "tom";
    public static final  String  PASSWORD          = "xioN2401";
    public static final  String  PROJECT_NAME      = "Default";
    public static final  String  USE_GRID          = "false";

    public static String SUITE_OS = null;

    protected String                      testName;
    protected String                      reportDirectory;
    protected String                      reportFormat;
    protected AppiumDriver<MobileElement> driver;
    protected DesiredCapabilities         testSuiteCaps;
    protected DesiredCapabilities         testCapabilities;
    //Test Classes packaged in app
    private String appPath = System.getProperty ("user.dir") + File.separator + "apps" + File.separator + "eribank";

    @Parameters({"os", "generateReport"})
    @BeforeTest
    public void setUp(final ITestContext context,
                      @Optional(DEFAULT_SUITE_OS) String os,
                      @Optional("false") boolean generateReport) throws MalformedURLException {
        SUITE_OS = os;

        Reporter.log (String.format (BEFORE_TEST_CLASS, context.getName ()), LOG_TO_SOUT);
        Reporter.log ("Test Parameters as list : ", LOG_TO_SOUT);
        context.getCurrentXmlTest ()
                .getTestParameters ()
                .forEach ((key, value) -> Reporter.log (key + " : " + value, LOG_TO_SOUT));

        // Device Capabilities
        testSuiteCaps = new DesiredCapabilities ();
        testSuiteCaps.setCapability (CapabilityType.PLATFORM, os);
        testSuiteCaps.setCapability (MobileCapabilityType.DEVICE_NAME, os + "Device");

        //Reporting Configurations for Appium Studio Reporting
        if (generateReport) {
            testSuiteCaps.setCapability (SeeTestCapabilityType.REPORT_DIRECTORY, reportDirectory);
            testSuiteCaps.setCapability (SeeTestCapabilityType.REPORT_FORMAT, reportFormat);
            testSuiteCaps.setCapability (SeeTestCapabilityType.TEST_NAME, testName);
        }
        //Grid Connection configuration

        testSuiteCaps.setCapability (SeeTestCapabilityType.USE_REMOTE_GRID, USE_GRID);
        testSuiteCaps.setCapability (SeeTestCapabilityType.USERNAME, USERNAME);
        testSuiteCaps.setCapability (SeeTestCapabilityType.PASSWORD, PASSWORD);
        testSuiteCaps.setCapability (SeeTestCapabilityType.PROJECT_NAME, PROJECT_NAME);

        Reporter.log ("DEBUG : " + context.getCurrentXmlTest ().getClasses ().toString (), true);
        Reporter.log ("app path : " + appPath, LOG_TO_SOUT);
        appPath = os.equals ("android") ? appPath + ".apk" : appPath + ".ipa";
    }

    @BeforeMethod
    public void setUpBeforeMethod(Method m) throws Exception {
        LOGGER.info (String.format (BEFORE_METHOD, m.getName ()));
        Reporter.log (String.format (BEFORE_METHOD, m.getName ()), LOG_TO_SOUT);
        m.getClass ().getPackage ();

        // App Capabilities
        testSuiteCaps.setCapability (MobileCapabilityType.APP, appPath);
        testSuiteCaps.setCapability (SeeTestCapabilityType.INSTRUMENT_APP, false);
        testSuiteCaps.setCapability (MobileCapabilityType.NO_RESET, true);

        //Driver initialization
        URL url = new URL (SERVER_URL);
        driver = DEFAULT_SUITE_OS.equals ("android") ?
                new SeeTestAndroidDriver<MobileElement> (url, testSuiteCaps) :
                new SeeTestIOSDriver<MobileElement> (url, testSuiteCaps);
    }

    @AfterMethod
    public void tearDownMethod(Method m) {
        LOGGER.info (String.format (AFTER_METHOD, m.getName ()));
        Reporter.log (String.format (AFTER_METHOD, m.getName ()), LOG_TO_SOUT);
        driver.quit ();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null)
            driver.quit ();
    }
}

