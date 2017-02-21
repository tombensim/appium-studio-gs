/*
 * Copyright (c) 2017. Experitest
 *
 */

package framework;

import com.experitest.appium.SeeTestAndroidDriver;
import com.experitest.appium.SeeTestCapabilityType;
import com.experitest.appium.SeeTestIOSDriver;
import com.experitest.client.log.ILogger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.Reporter;
import org.testng.TestException;
import org.testng.annotations.*;
import utils.AppiumStudioClient;
import utils.Utils;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by tom.ben-simhon on 1/15/2017.
 */
public class CapabilitiesManager {
    //TODO: Replace file name with file from dir
    public static final String APP_FILE_NAME = "appnext";

    // Enum helps decide on the desired capabilities based on the tests package
    private enum PackageNames {
        APP("appnext.tests"),
        WEB("web.tests"),
        HYBRID("hybrid.tests");
        private String packageName;

        PackageNames(String packageName) {
            this.packageName = packageName;
        }
    }

    protected DesiredCapabilities caps;

    public CapabilitiesManager() {
        this.caps = new DesiredCapabilities();
    }
    public void merge (DesiredCapabilities capsToMerge)
    {
        this.caps.merge(capsToMerge);
    }


    /**
     * Use this method in case you wish to work with SeeTestGrid
     *
     * @param username    your Cloud username
     * @param password    your Cloud password
     * @param projectName your Cloud project
     * @return the set of desired capabilities for Grid client Setup
     */
    public DesiredCapabilities getGridCapabilities(String username, String password, String projectName) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(SeeTestCapabilityType.USE_REMOTE_GRID, true);
        caps.setCapability(SeeTestCapabilityType.USERNAME, username);
        caps.setCapability(SeeTestCapabilityType.PASSWORD, password);
        caps.setCapability(SeeTestCapabilityType.PROJECT_NAME, projectName);
        return caps;

    }


    /**
     * @param reportDirectory root folder that stores and summerizes all reports generated within
     * @param reportFormat    pdf \ html
     * @param testName        Test title for the report
     * @return DesiredCapabilities for generating Test report with Test name, format and root directory for report summary
     */

    public DesiredCapabilities getReportCapabilities(String reportDirectory, String reportFormat, String testName) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(SeeTestCapabilityType.REPORT_DIRECTORY, reportDirectory);
        caps.setCapability(SeeTestCapabilityType.REPORT_FORMAT, reportFormat);
        caps.setCapability(SeeTestCapabilityType.TEST_NAME, testName);
        return caps;
    }

    /**
     * Creates desired capabilities for test methods executed in package
     * @param packageName APP,
     * @return DesiredCapabilities for the test within a package
     */
    public DesiredCapabilities getTestCapabiliesForPackage(String packageName) {
        DesiredCapabilities caps = new DesiredCapabilities();
        switch (PackageNames.valueOf(packageName.toUpperCase().replace(".TESTS", ""))) {
            case APP:
                String appPath = System.getProperty("user.dir") + File.separator + "apps" + File.separator + APP_FILE_NAME;
                appPath = this.caps.getCapability(MobileCapabilityType.PLATFORM_NAME).equals("android") ?
                        appPath + ".apk" : appPath + ".ipa";
                Reporter.log("APP under test : " + appPath, true);
                caps.setCapability(MobileCapabilityType.APP, appPath);
                //TODO : Change the app to SeeTest yet Again
                caps.setCapability(MobileCapabilityType.APP, "com.appnext.qa.android_sdk/.MainActivity");

                caps.setCapability(MobileCapabilityType.NO_RESET, true);
                caps.setCapability(MobileCapabilityType.FULL_RESET, false);
                caps.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);
                break;

            case HYBRID:
                //TODO : change Hybrid app path
                caps.setCapability(MobileCapabilityType.APP, "/apps/tom.apl");
                caps.setCapability(MobileCapabilityType.NO_RESET, true);
                caps.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, false);
                break;
            case WEB:
                caps.setCapability(MobileCapabilityType.BROWSER_NAME, "safari");
                caps.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);
                caps.setCapability(MobileCapabilityType.NO_RESET, false);
                break;
            default:
                throw new TestException("package name is not defined in Package name enum, \n" +
                        "got package name : " + packageName);
        }
        return caps;
    }

    protected DesiredCapabilities gridCapabilities() {
        DesiredCapabilities gridCaps = new DesiredCapabilities();
        gridCaps.setCapability(SeeTestCapabilityType.USERNAME, "tom");
        gridCaps.setCapability(SeeTestCapabilityType.PASSWORD, "xioN2401");
        gridCaps.setCapability(SeeTestCapabilityType.PROJECT_NAME, "Default");
        gridCaps.setCapability(SeeTestCapabilityType.USE_REMOTE_GRID, "true");
        return gridCaps;
    }
}
