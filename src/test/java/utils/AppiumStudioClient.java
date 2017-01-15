/*
 * Copyright (c) 2017. Experitest
 *
 */

package utils;

import com.experitest.client.Client;
import org.testng.Reporter;

import java.net.URL;

/**
 * Created by tom.ben-simhon on 1/15/2017.
 */
public class AppiumStudioClient extends Client {
    public AppiumStudioClient(URL url,String testName) {
        super(url.getHost (),url.getPort (),true);
    }




}

