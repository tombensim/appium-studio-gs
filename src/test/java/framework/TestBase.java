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
    private String reportDirectory = "reports";
    private String reportFormat = "xml";

    private String testOS = null;
    private boolean useGrid;
    String deviceName;
    private URL serverURL;

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

    @Parameters({"DeviceOS", "useGrid"})
    @BeforeTest
    public void setUpTest(final ITestContext context,
                          @Optional(DEFAULT_SUITE_OS) String os,
                          @Optional("false") boolean useGrid) throws MalformedURLException {

        testOS = context.getCurrentXmlTest().getParameter("DeviceOS");
        log = LoggerFactory.getLogger(context.getName());
        log.warn("{} TEST OS : {}", context.getCurrentXmlTest().getName(), testOS);
        this.useGrid = useGrid;

        log.info("Setting Appium Studio Client for test : {} with OS {}", context.getName(), os);

        serverURL = useGrid ? new URL(GRID_SERVER_URL) : new URL(LOCAL_SERVER_URL);
        log.info("Appium Studio Server URL :  {}", serverURL.toString());
        String appPath = os.equals("android") ? PATH_TO_APK : PATH_TO_IPA;

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(SeeTestCapabilityType.USE_REMOTE_GRID, useGrid);
        dc.setCapability(SeeTestCapabilityType.USERNAME, "admin");
        dc.setCapability(SeeTestCapabilityType.PASSWORD, "Experitest2012");
        dc.setCapability(SeeTestCapabilityType.TEST_NAME, "Setup Suite");
        dc.setCapability(MobileCapabilityType.APP, appPath);
        dc.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);

        driver = Utils.getDriver(os, serverURL, dc);
        deviceName = Utils.getAppiumClient(driver).getDeviceProperty("device.name").split(":")[1];
        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("device.name",deviceName);
        parameters.putAll(context.getCurrentXmlTest().getAllParameters());
        context.getCurrentXmlTest().setParameters(parameters);

        driver.quit();

    }

    @BeforeClass
    public void setUpClass(final ITestContext context) throws MalformedURLException {
        testOS = context.getCurrentXmlTest().getParameter("DeviceOS");
        useGrid = Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("useGrid"));
        deviceName = context.getCurrentXmlTest().getParameter("device.name");
        serverURL = useGrid ? new URL(GRID_SERVER_URL) : new URL(LOCAL_SERVER_URL);

        log = LoggerFactory.getLogger(context.getName());
        log.info("Setting Appium Studio Client for class : {} with OS {}", context.getCurrentXmlTest(), testOS);
    }

    @BeforeMethod
    public void setUpBeforeMethod(Method m) throws Exception {
        reporteClient.addProperty("type", typeTag);
        reporteClient.addProperty("jenkins.eribank.build.id", BUILD_ID);

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(SeeTestCapabilityType.REPORT_DIRECTORY, reportDirectory);
        dc.setCapability(SeeTestCapabilityType.REPORT_FORMAT, reportFormat);
        dc.setCapability(SeeTestCapabilityType.TEST_NAME, m.getName());

        dc.setCapability(SeeTestCapabilityType.USE_REMOTE_GRID, useGrid);
        dc.setCapability(SeeTestCapabilityType.USERNAME, "admin");
        dc.setCapability(SeeTestCapabilityType.PASSWORD, "Experitest2012");

        dc.setCapability(SeeTestCapabilityType.ST_DEVICE_NAME, deviceName);
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
        dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBankO");
        dc.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);
        driver = Utils.getDriver(testOS, serverURL, dc);

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


}
