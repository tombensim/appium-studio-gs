/*
 * Copyright (c) 2017. Experitest
 *
 */

package utils;

import com.experitest.appium.SeeTestAndroidDriver;
import com.experitest.appium.SeeTestIOSDriver;
import com.experitest.client.Client;
import com.experitest.client.log.ILogger;
import com.experitest.client.log.Level;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import java.net.URL;

/**
 * Created by tom.ben-simhon on 1/18/2017.
 * Utility class for SeeTestAppium Drivers
 */
public class STDUtils {
    /**
     * Return AppiumStudioClient from SeeTestAndroid OR SeeTestIOSDriver initilized instances
     * @param driver
     * @return
     */
    public  static AppiumStudioClient getAppiumClient (AppiumDriver driver)
    {
        if (driver instanceof SeeTestAndroidDriver || driver instanceof SeeTestIOSDriver) {
            Client client = (driver instanceof SeeTestAndroidDriver) ? ((SeeTestAndroidDriver) driver).getClient() :
                    ((SeeTestIOSDriver) driver).getClient();
            return new AppiumStudioClient(client);
        }
        throw new RuntimeException("AppiumDriver wasn't initilized using new SeeTestAndroidDriver OR SeeTestIOSDriver" +
                "Please follow our documentation on Driver initilization" +
                "https://docs.experitest.com/display/AS/Appium+Studio+Home");
    }

    /**
     * Quits the session and generates a report for the test
     * @param driver
     * @return
     */
    public  static String generateReport (AppiumDriver driver)
    {
        driver.quit();
        return (driver instanceof SeeTestAndroidDriver) ? ((SeeTestAndroidDriver) driver).getReportFolder() :
                ((SeeTestIOSDriver) driver).getReportFolder();
    }

    public static AppiumDriver getDriver(String os,URL url, DesiredCapabilities caps)
    {
        return os.equals("android") ? new SeeTestAndroidDriver<>(url,caps) : new SeeTestIOSDriver<>(url,caps);
    }
}
