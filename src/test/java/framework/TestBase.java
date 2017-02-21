/*
 * Copyright (c) 2017. Experitest
 *
 */

package framework;

import com.experitest.appium.SeeTestAndroidDriver;
import com.experitest.appium.SeeTestCapabilityType;
import com.experitest.appium.SeeTestIOSDriver;
import com.experitest.manager.client.PManager;
import com.experitest.manager.testng.ManagerITestListener;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import jdk.internal.dynalink.linker.MethodHandleTransformer;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.*;
import utils.AppiumStudioClient;
import utils.Utils;

import java.io.*;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Created by tom.ben-simhon on 12/26/2016.
 * Test Base Class holds all members required to run a test, and details general capabilities
 * For the SeeTestDrivers
 */

@Listeners({TestNGLogger.class, ManagerITestListener.class})
public class TestBase {

    public static final String PATH_TO_APK = System.getProperty("user.dir")+ File.separator + "apps" + File.separator + "eribank.apk";
//    public static final String PATH_TO_RESOURCES;

    private static final String DEFAULT_SUITE_OS = "android";
    protected Logger log;

    protected PManager reporteClient = PManager.getInstance();
    protected  AppiumStudioClient apc; // Appium Studio Client
    //Grid configurations
    protected AppiumDriver<MobileElement> driver;

    //Appium Studio parameters
    protected static final String LOCAL_SERVER_URL = "http://localhost:4723";
    protected static final String REMOTE_SERVER_URL = "http://192.168.1.210";

    public static final String REPORTER_URL = "cloudreports.experitest.com:80";
    public static String BUILD_ID = null;

    private String reportDirectory = "reports";
    private String reportFormat = "xml";

    // Set Reporter configuration for Tests to follow
    static {
        System.setProperty("manager.url", REPORTER_URL);
        Properties prop = new Properties();
        InputStream input = null;
        OutputStream output = null;
        String buildPropFile = TestBase.class.getClassLoader().getResource("build.properties").getFile();
        try {
            input = new FileInputStream(buildPropFile);
            prop.load(input);
            BUILD_ID = String.valueOf(prop.getOrDefault("eribank.build.id", 999999));
            int buildid = Integer.parseInt(BUILD_ID) + 1;
            prop.setProperty("eribank.build.id", String.valueOf(buildid));
            input.close();
            output = new FileOutputStream(buildPropFile);
            prop.store(output,null);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (output !=null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private URL serverURL;

    @Parameters({"os", "grid"})
    @BeforeClass
    public void setUp(final ITestContext context,
                      @Optional(DEFAULT_SUITE_OS) String os,
                      @Optional("false") boolean useGrid) throws MalformedURLException {
        log = LoggerFactory.getLogger(context.getName());
        log.info("Setting Appium Studio Client for test : {}" , context.getName());
        serverURL = useGrid ? new URL(REMOTE_SERVER_URL) : new URL(LOCAL_SERVER_URL);
        log.info("Appium Studio Server URL :  {}",serverURL.toString()) ;

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.APP, PATH_TO_APK);
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
        dc.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);
        driver = new SeeTestAndroidDriver<>(new URL(LOCAL_SERVER_URL), dc);
        driver.quit();


    }

    @BeforeMethod
    public void setUpBeforeMethod(Method m) throws Exception {

        reporteClient.addProperty("type","eribank");
        reporteClient.addProperty("eribank.build.id",BUILD_ID);

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(SeeTestCapabilityType.REPORT_DIRECTORY, reportDirectory);
        dc.setCapability(SeeTestCapabilityType.REPORT_FORMAT, reportFormat);
        dc.setCapability(SeeTestCapabilityType.TEST_NAME, m.getName());
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
        dc.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);
        driver = new SeeTestAndroidDriver<>(serverURL, dc);

        apc = Utils.getAppiumClient(driver);
    }

    @AfterMethod
    public void tearDownMethod(Method m) {
        String reportFolder =  Utils.generateReport(driver);
        reporteClient.addReportFolder(reportFolder);

        log.info("Report Folder : {}",reportFolder);
    }

    @AfterClass
    public void tearDown() {
        log.info("FINISHED : Execution for Class");
    }

    private Map<String, ITestNGMethod> mapMethodNamesToTestNGmethods(ITestContext context) {
        Map<String, ITestNGMethod> map = new HashMap<>();
        ITestNGMethod[] allTestMethods = context.getAllTestMethods();
        Arrays.stream(allTestMethods).forEach(tm -> map.put(tm.getMethodName(), tm));
        return map;
    }
}
