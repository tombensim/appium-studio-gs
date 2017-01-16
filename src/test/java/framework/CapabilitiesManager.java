/*
 * Copyright (c) 2017. Experitest
 *
 */

package framework;

import com.experitest.appium.SeeTestCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.TestException;

import java.io.File;

/**
 * Created by tom.ben-simhon on 1/15/2017.
 */
public class CapabilitiesManager {
    //TODO: Replace file name with file from dir
    public static final String APP_FILE_NAME = "eribank";
    // Enum helps decide on the desired capabilities based on the tests package
    private enum PackageNames {
        APP("app.tests"),
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
    public CapabilitiesManager(DesiredCapabilities capabilities) {
        this.caps = capabilities;
    }

    public void getReportCapabilities() {

    }

    public DesiredCapabilities getTestCapabiliesForPackage(String packageName) {

        DesiredCapabilities testMethodCaps = new DesiredCapabilities();


        switch (PackageNames.valueOf(packageName.toUpperCase().replace(".TESTS",""))) {
            case APP:
                String appPath = System.getProperty("user.dir") + File.separator + "apps" + File.separator + APP_FILE_NAME;
                appPath = caps.getCapability(CapabilityType.PLATFORM).equals("android") ?
                        appPath + ".apk" : appPath + ".ipa";
                Reporter.log("APP under test : " + appPath, true);

                testMethodCaps.setCapability(MobileCapabilityType.APP, appPath);
                testMethodCaps.setCapability(MobileCapabilityType.NO_RESET, true);
                testMethodCaps.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, false);
                break;

            case HYBRID:
                //TODO : change Hybrid app path
                testMethodCaps.setCapability(MobileCapabilityType.APP, "/apps/tom.apl");
                testMethodCaps.setCapability(MobileCapabilityType.NO_RESET, true);
                testMethodCaps.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, false);
                break;
            case WEB:
                break;
            default:
                throw new TestException("package name is not defined in Package name enum, \n" +
                        "got package name : " + packageName);
        }
        return testMethodCaps;
    }
}
