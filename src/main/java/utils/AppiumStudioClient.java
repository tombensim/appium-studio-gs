/*
 * Copyright (c) 2017. Experitest
 *
 */

package utils;

import com.experitest.appium.SeeTestAndroidDriver;
import com.experitest.appium.SeeTestIOSDriver;
import com.experitest.client.Client;
import com.experitest.client.ClientWrapper;
import io.appium.java_client.MobileElement;
import org.testng.Reporter;

/**
 * appium-gradle-getting-started
 * Created by tom.ben-simhon on 1/15/2017.
 */
//public class AppiumStudioClient extends ClientWrapper
public class AppiumStudioClient extends ClientWrapper {
    public AppiumStudioClient(Client client)
    {
        super(client);
    }
//{
//
//
//    public AppiumStudioClient(SeeTestAndroidDriver<MobileElement> driver) {
//        super(driver.getClient());
//        Reporter.log(String.format("Finished initiazlization for Device : %s \n", true));
//    }
//
//    public AppiumStudioClient(SeeTestIOSDriver<MobileElement> driver) {
//        super(driver.getClient());
//        Reporter.log(String.format("Finished initiazlization for Device : %s \n"
//                , this.getDeviceProperty("device.name")), true);
//    }
//
//    public void launch(String os) {
//        if (os.equals("android"))
//            super.launch("cloud:com.experitest.ExperiBank/.LoginActivity", false, false);
//        else
//            super.launch("com.experitest.ExperiBank", false, false);
//
//    }

}
