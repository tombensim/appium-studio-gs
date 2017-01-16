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
import org.testng.ITestNGMethod;
import org.testng.Reporter;
import org.testng.annotations.*;
import utils.AppiumStudioClient;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static framework.LogListener.LOG_TO_SOUT;

/**
 * Created by tom.ben-simhon on 12/26/2016.
 * Test Base Class holds all members required to run a test, and details general capabilities
 * For the SeeTestDrivers
 */

@Listeners(LogListener.class)
public class TestBase {

    private static final String DEFAULT_SUITE_OS = "android";

    //Grid configurations
    private static final String USERNAME = "tom";
    private static final String PASSWORD = "xioN2401";
    private static final String PROJECT_NAME = "Default";
    private static final String USE_GRID = "false";

    private DesiredCapabilities testSuiteCaps;
    protected AppiumDriver<MobileElement> driver;

    private Map<String, ITestNGMethod> methodMap;
    //Appium Studio parameters
    protected static final String SERVER_URL = "http://localhost:8889";
    //
    private String testName;
    private String reportDirectory;
    private String reportFormat;


    @Parameters({"os", "grid"})
    @BeforeTest
    public void setUp(final ITestContext context,
                      @Optional(DEFAULT_SUITE_OS) String os,
                      @Optional("false") boolean useGrid) throws MalformedURLException {

        methodMap = mapMethodNamesToTestNGmethods(context);
        URL url = new URL(SERVER_URL);
        Reporter.log("Setting Appium Studio Client for test : " + testName, 2, LOG_TO_SOUT);
        AppiumStudioClient studioClient = new AppiumStudioClient(url, context.getName());

        String connectedDevices = studioClient.getConnectedDevices();
        Reporter.log(connectedDevices, true);


        // Device Capabilities
        testSuiteCaps = new DesiredCapabilities();
        testSuiteCaps.setCapability(CapabilityType.PLATFORM, os);
        testSuiteCaps.setCapability(MobileCapabilityType.DEVICE_NAME, os + "Device");

        //Reporting Configurations for Appium Studio Reporting
//        if (generateReport) {
//            testSuiteCaps.setCapability(SeeTestCapabilityType.REPORT_DIRECTORY, reportDirectory);
//            testSuiteCaps.setCapability(SeeTestCapabilityType.REPORT_FORMAT, reportFormat);
//            testSuiteCaps.setCapability(SeeTestCapabilityType.TEST_NAME, testName);
//        }
        //Grid Connection configuration

        testSuiteCaps.setCapability(SeeTestCapabilityType.USE_REMOTE_GRID, USE_GRID);
        testSuiteCaps.setCapability(SeeTestCapabilityType.USERNAME, USERNAME);
        testSuiteCaps.setCapability(SeeTestCapabilityType.PASSWORD, PASSWORD);
        testSuiteCaps.setCapability(SeeTestCapabilityType.PROJECT_NAME, PROJECT_NAME);

        Reporter.log("DEBUG : " + context.getCurrentXmlTest().getClasses().toString(), true);
    }

    private Map<String, ITestNGMethod> mapMethodNamesToTestNGmethods(ITestContext context) {
        Map<String, ITestNGMethod> map = new HashMap<String, ITestNGMethod>();
        ITestNGMethod[] allTestMethods = context.getAllTestMethods();
        for (int i = 0; i < allTestMethods.length; i++) {
            map.put(allTestMethods[i].getMethodName(), allTestMethods[i]);
        }
        return map;
    }

    @BeforeMethod
    public void setUpBeforeMethod(Method m) throws Exception {
//        Reporter.log (String.format (BEFORE_METHOD, m.getName ()), LOG_TO_SOUT);
        CapabilitiesManager cm = new CapabilitiesManager(testSuiteCaps);
        String packageName = methodMap.get(m.getName()).getRealClass().getPackage().getName();
        testSuiteCaps.merge(cm.getTestCapabiliesForPackage(packageName));

        URL url = new URL(SERVER_URL);
        //Driver initialization
        driver = testSuiteCaps.getCapability(CapabilityType.PLATFORM).equals("android") ?
                new SeeTestAndroidDriver<MobileElement>(url, testSuiteCaps) :
                new SeeTestIOSDriver<MobileElement>(url, testSuiteCaps);
    }

    @AfterMethod
    public void tearDownMethod(Method m) {
        driver.quit();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}

