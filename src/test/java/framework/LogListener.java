/*
 * Copyright (c) 2017. Experitest
 *
 */

package framework;

import org.boon.datarepo.Repo;
import org.testng.*;
import org.testng.log4testng.Logger;

/**
 * Created by tom.ben-simhon on 1/14/2017.
 */
public class LogListener extends TestListenerAdapter {

    Logger LOGGER;

    public static final String ON_TEST_START = "------- Starting Test %s -------- \n";
    public static final String ON_TEST_FINISHED = "---------- Test %s is Finished with Result ---------\n";


    @Override
    public void onStart(ITestContext testContext) {
        super.onStart(testContext);
        log(testContext, ON_TEST_START);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
        log(testContext, ON_TEST_FINISHED);
    }

    private void log(ITestContext context, String string) {
        LOGGER = Logger.getLogger(context.getClass());
        LOGGER.info(String.format(string,context.getName()));
        Reporter.log(String.format(string,context.getName()),true);

    }
}
