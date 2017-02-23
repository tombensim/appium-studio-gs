/*
 * Copyright (c) 2017. Experitest
 * @author Tom Bensimhon
 */

package app.tests;

import com.experitest.appium.SeeTestCapabilityType;
import framework.ExperiTestBase;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utils.STDUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class EribankTestBase extends ExperiTestBase {
    // Path to Applications
    private static final String PATH_TO_APK = System.getProperty("user.dir") + File.separator + "apps" + File.separator + "eribank.apk";
    private static final String PATH_TO_IPA = System.getProperty("user.dir") + File.separator + "apps" + File.separator + "eribankO.ipa";

    //Appium Studio parameters
    private static final String DEFAULT_SUITE_OS = "android";
    private static final String LOCAL_SERVER_URL = "http://localhost:4723";
    private static final String GRID_SERVER_URL = "http://sales.experitest.com";

    @SuppressWarnings("FieldCanBeLocal")
    private String reportDirectory = "reports";
    private String reportFormat = "xml";

    private String deviceOS = null;
    private boolean useGrid;
    String deviceName;
    private URL serverURL;

    private static final String typeTag = "eribank";



    /**
     * setUpTest method runs before a TestNG tagged test (<test>...</test>)
     * it will init test parameters and install the latest application build on the device before
     * we trigger all "Feature" tests
     *
     * @param context - TestNG Test Context
     * @throws MalformedURLException - If we failed to parse the AppiumStudio URL
     */
    @BeforeTest
    public void setUpTest(final ITestContext context) throws MalformedURLException {
        // set the driver OS platform from XML or use default conf
        deviceOS = (context.getCurrentXmlTest().getParameter("DeviceOS") != null) ?
                context.getCurrentXmlTest().getParameter("DeviceOS") :
                DEFAULT_SUITE_OS;

        // set the grid parameter from XML or false as default
        useGrid = (context.getCurrentXmlTest().getParameter("useGrid") != null);

        String appPath = deviceOS.equals("android") ? PATH_TO_APK : PATH_TO_IPA;
        serverURL = useGrid ? new URL(GRID_SERVER_URL) : new URL(LOCAL_SERVER_URL);

        log = LoggerFactory.getLogger(context.getName());
        log.info("Setting Appium Studio Client for test : {} with OS {}", context.getName(), deviceOS);
        log.info("Appium Studio Server URL :  {}", serverURL.toString());

        // init the desired capabilites before test - Installing the latest build of the app on the target device
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.merge(getGridCapabilities(useGrid));
        dc.setCapability(SeeTestCapabilityType.TEST_NAME, "Setup Suite");
        dc.setCapability(MobileCapabilityType.APP, appPath);
        dc.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);
        driver = STDUtils.getDriver(deviceOS, serverURL, dc);

        // store the name of the device for the rest of the tagged TestNG (<test>...</test> test execution in context
        deviceName = STDUtils.getAppiumClient(driver).getDeviceProperty("device.name").split(":")[1];
        Map<String, String> parameters = context.getCurrentXmlTest().getTestParameters();
        parameters.put("device.name", deviceName);
        context.getCurrentXmlTest().setParameters(parameters);

        driver.quit();
    }

    /**
     * before we start a class which contains TestNG @Test methods, we init the parameters
     * for the class (deviceOS , grid \ local server url , deviceName)
     *
     * @param context - TestNG <test>...</test> context
     * @throws MalformedURLException - if we failed to parse the Appium Studio URL
     */
    @BeforeClass
    public void setUpClass(final ITestContext context) throws MalformedURLException {
        deviceOS = context.getCurrentXmlTest().getParameter("DeviceOS");
        useGrid = Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("useGrid"));
        deviceName = context.getCurrentXmlTest().getParameter("device.name");
        serverURL = useGrid ? new URL(GRID_SERVER_URL) : new URL(LOCAL_SERVER_URL);

        log = LoggerFactory.getLogger(context.getName());
        log.info("Starting execution for Test Class with params");
        log.info("Platform OS : {}" +
                "Device Name : {} " +
                "Appium Studio Server URL : {} ",
                deviceOS,deviceName,serverURL);
    }

    /**
     * executes before any TestNG @Test annotated method
     * sets the reporter parameters (type, build id) and launches the app on the targeted device
     * @param m - @Test method
     */
    @BeforeMethod
    public void setUpBeforeMethod(Method m)  {
        // Add report test properties
        reporteClient.addProperty("type", typeTag);
        reporteClient.addProperty("jenkins.eribank.build.id", BUILD_ID);

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.merge(getGridCapabilities(useGrid));
        dc.setCapability(SeeTestCapabilityType.REPORT_DIRECTORY, reportDirectory);
        dc.setCapability(SeeTestCapabilityType.REPORT_FORMAT, reportFormat);
        dc.setCapability(SeeTestCapabilityType.TEST_NAME, m.getName());
        dc.setCapability(SeeTestCapabilityType.ST_DEVICE_NAME, deviceName);
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
        dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBankO");
        dc.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);
        driver = STDUtils.getDriver(deviceOS, serverURL, dc);

        //set the AppiumStudioClient for the test session
        apc = STDUtils.getAppiumClient(driver);
    }

    /**
     * executed after each TestNG @Test annotated method
     * generates a test report and releases the client
     * @param m - executed @Test method
     */
    @AfterMethod
    public void tearDownMethod(Method m) {
        String reportFolder = STDUtils.generateReport(driver);
        reporteClient.addReportFolder(reportFolder);
        log.info("Report Folder : {}", reportFolder);
    }

    /**
     * executes after a class with @Test methods have been executed completly
     */
    @AfterClass
    public void tearDown()
    {
        log.info("FINISHED : Execution for Class");
    }
}

