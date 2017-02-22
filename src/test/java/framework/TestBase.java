/*
 * Copyright (c) 2017. Experitest
 *
 */

package framework;

import com.experitest.appium.SeeTestCapabilityType;
import com.experitest.manager.client.PManager;
import com.experitest.manager.testng.ManagerITestListener;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utils.AppiumStudioClient;
import utils.Utils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by tom.ben-simhon on 12/26/2016.
 * Test Base Class holds all members required to run a test, and details general capabilities
 * For the SeeTestDrivers
 */

@Listeners({TestNGLogger.class, ManagerITestListener.class})
public class TestBase {

    // Path to Applications
    public static final String PATH_TO_APK = System.getProperty("user.dir") + File.separator + "apps" + File.separator + "eribank.apk";
    public static final String PATH_TO_IPA = System.getProperty("user.dir") + File.separator + "apps" + File.separator + "eribankO.ipa";

    protected Logger log;

    protected PManager reporteClient = PManager.getInstance();
    protected AppiumStudioClient apc; // Appium Studio Client
    protected AppiumDriver<MobileElement> driver;
    //Grid configurations

    //Appium Studio parameters
    private static final String DEFAULT_SUITE_OS = "android";
    protected static final String LOCAL_SERVER_URL = "http://localhost:4723";
    protected static final String GRID_SERVER_URL = "http://sales.experitest.com";

    public static final String REPORTER_URL = "cloudreports.experitest.com:80";
    @SuppressWarnings("FieldCanBeLocal")
    private String reportDirectory = "reports";
    private String reportFormat = "xml";

    private String deviceOS = null;
    private boolean useGrid;
    String deviceName;
    protected URL serverURL;

    protected static String BUILD_ID = null;
    private static String typeTag;
    private static String griduser;
    private static String gridpass;

    // Set Reporter configuration for Tests to follow
    static {
        System.setProperty("manager.url", REPORTER_URL);
        BUILD_ID = System.getenv("eribank.build.id") != null ? System.getenv("eribank.build.id") : "99999999";
        griduser = System.getenv("griduser");
        gridpass = System.getenv("gridpass");
        typeTag = BUILD_ID.equals("99999999") ? "eribank.debug" : "eribank";
    }

    @BeforeTest
    public void setUpTest(final ITestContext context) throws MalformedURLException {

        deviceOS = (context.getCurrentXmlTest().getParameter("DeviceOS") != null) ?
                context.getCurrentXmlTest().getParameter("DeviceOS") :
                DEFAULT_SUITE_OS;

        // don't use grid for local or debug execution
        useGrid = (context.getCurrentXmlTest().getParameter("useGrid") != null);
        String appPath = deviceOS.equals("android") ? PATH_TO_APK : PATH_TO_IPA;
        serverURL = useGrid ? new URL(GRID_SERVER_URL) : new URL(LOCAL_SERVER_URL);

        log = LoggerFactory.getLogger(context.getName());
        log.info("Setting Appium Studio Client for test : {} with OS {}", context.getName(), deviceOS);
        log.info("Appium Studio Server URL :  {}", serverURL.toString());


        DesiredCapabilities dc = new DesiredCapabilities();
        dc.merge(getGridCapabilities());
        dc.setCapability(SeeTestCapabilityType.TEST_NAME, "Setup Suite");
        dc.setCapability(MobileCapabilityType.APP, appPath);
        dc.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);
        driver = Utils.getDriver(deviceOS, serverURL, dc);

        deviceName = Utils.getAppiumClient(driver).getDeviceProperty("device.name").split(":")[1];
        Map<String, String> parameters = context.getCurrentXmlTest().getTestParameters();
        parameters.put("device.name", deviceName);
        context.getCurrentXmlTest().setParameters(parameters);

        driver.quit();

    }

    @BeforeClass
    public void setUpClass(final ITestContext context) throws MalformedURLException {
        deviceOS = context.getCurrentXmlTest().getParameter("DeviceOS");
        useGrid = Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("useGrid"));
        deviceName = context.getCurrentXmlTest().getParameter("device.name");
        serverURL = useGrid ? new URL(GRID_SERVER_URL) : new URL(LOCAL_SERVER_URL);

        log = LoggerFactory.getLogger(context.getName());
        log.info("Setting Appium Studio Client for class : {} with OS {}", context.getCurrentXmlTest(), deviceOS);
    }

    @BeforeMethod
    public void setUpBeforeMethod(Method m) throws Exception {
        // Add reported basic properties
        reporteClient.addProperty("type", typeTag);
        reporteClient.addProperty("jenkins.eribank.build.id", BUILD_ID);

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(SeeTestCapabilityType.REPORT_DIRECTORY, reportDirectory);
        dc.setCapability(SeeTestCapabilityType.REPORT_FORMAT, reportFormat);
        dc.setCapability(SeeTestCapabilityType.TEST_NAME, m.getName());
        dc.merge(getGridCapabilities());
        dc.setCapability(SeeTestCapabilityType.ST_DEVICE_NAME, deviceName);
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
        dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBankO");
        dc.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);
        driver = Utils.getDriver(deviceOS, serverURL, dc);

        apc = Utils.getAppiumClient(driver);
    }

    @AfterMethod
    public void tearDownMethod(Method m) {
        String reportFolder = Utils.generateReport(driver);
        reporteClient.addReportFolder(reportFolder);
        log.info("Report Folder : {}", reportFolder);
    }

    @AfterClass
    public void tearDown() {
        log.info("FINISHED : Execution for Class");
    }

    private DesiredCapabilities getGridCapabilities() {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(SeeTestCapabilityType.USE_REMOTE_GRID, useGrid);
        dc.setCapability(SeeTestCapabilityType.USERNAME, griduser);
        dc.setCapability(SeeTestCapabilityType.PASSWORD, gridpass);
        return dc;
    }


}
