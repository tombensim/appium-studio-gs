/*
 * Copyright (c) 2017. Experitest
 *
 */

package utils;

import com.experitest.client.Client;

import java.net.URL;

/**
 * Created by tom.ben-simhon on 1/15/2017.
 */
public class AppiumStudioClient extends Client {
    public AppiumStudioClient(URL url) {
        super(url.getHost (),url.getPort (),true);
    }

}

