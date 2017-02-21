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
import org.testng.Reporter;

/**
 * Created by tom.ben-simhon on 1/18/2017.
 */
public class Utils {
    public  static AppiumStudioClient getAppiumClient (AppiumDriver driver)
    {
        Client client = (driver instanceof SeeTestAndroidDriver) ? ((SeeTestAndroidDriver) driver).getClient() :
        ((SeeTestIOSDriver) driver).getClient();
        return new AppiumStudioClient(client);
    }
    public  static String generateReport (AppiumDriver driver)
    {
        driver.quit();
        return (driver instanceof SeeTestAndroidDriver) ? ((SeeTestAndroidDriver) driver).getReportFolder() :
                ((SeeTestIOSDriver) driver).getReportFolder();

    }
    public static void setLogger(AppiumDriver driver, ILogger logger) {
//        if (driver instanceof SeeTestAndroidDriver) {
//            ((SeeTestAndroidDriver) driver).setLogger(logger);
//        }
//        if (driver instanceof SeeTestIOSDriver) {
//            ((SeeTestIOSDriver) driver).setLogger(logger);
//        }
    }

    public static ILogger getLogger(boolean printToConsole) {
        return (level, o, throwable) -> Reporter.log(o.toString(),printToConsole);
    }
}
