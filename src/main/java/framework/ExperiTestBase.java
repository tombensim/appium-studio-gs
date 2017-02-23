/*
 * Copyright (c) 2017. Experitest
 *
 */

package framework;

import com.experitest.appium.SeeTestCapabilityType;
import com.experitest.manager.client.PManager;
import com.experitest.manager.testng.ManagerITestListener;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.testng.annotations.Listeners;
import utils.AppiumStudioClient;

import java.net.URL;

/**
 * Created by tom.ben-simhon on 12/26/2016.
 * Test Base Class holds all members required to run a test, and details general capabilities
 * For the SeeTestDrivers
 */

@Listeners({TestNGLogger.class, ManagerITestListener.class})
public class ExperiTestBase {
    protected Logger log;
    // Client and Driver Setup
    protected PManager reporteClient = PManager.getInstance();
    protected AppiumStudioClient apc; // Appium Studio Client
    protected AppiumDriver driver;
    public static final String REPORTER_URL = "cloudreports.experitest.com:80";

    protected static String BUILD_ID = null;
    protected static String typeTag;
    protected static String buildTag;
    private static String griduser;
    private static String gridpass;

    public static final String LOCAL_BUILD = "LOCAL_BUILD_ID";

    // Set Reporter configuration for Tests to follow
    static {
        System.setProperty("manager.url", REPORTER_URL);
        // Set the build.id from the CI enviroment or Local
        buildTag = System.getenv("eribank.build.id") != null ? "eribank.build.id" : "TRAVIS_BUILD_NUMBER";
        BUILD_ID = System.getenv(buildTag) != null ? System.getenv(buildTag) : LOCAL_BUILD;
        griduser = System.getenv("griduser");
        gridpass = System.getenv("gridpass");
        typeTag = BUILD_ID.equals(LOCAL_BUILD) ? "eribank" : "eribank.debug";
    }

    public DesiredCapabilities getGridCapabilities(boolean useGrid) {
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(SeeTestCapabilityType.USE_REMOTE_GRID, useGrid);
        dc.setCapability(SeeTestCapabilityType.USERNAME, griduser);
        dc.setCapability(SeeTestCapabilityType.PASSWORD, gridpass);
        return dc;
    }


}
