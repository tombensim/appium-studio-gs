/*
 * Copyright (c) 2017. Experitest
 *
 */

package framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerFactory;
import org.testng.*;


public class TestNGLogger implements ITestListener, ISuiteListener, IInvokedMethodListener, IConfigurationListener2 {

    protected Logger log = new Log4jLoggerFactory().getLogger(this.getClass().getName());

    public static final String SUCCESS_CONFIGURATION_METHODS = "SUCCESS : Configuration Methods [{}]";
    public static final String ERROR_FAILED_WITH_CONFIGURATION_METHOD = "ERROR : Failed with Configuration Method {}";
    public static final String SKIPPED_IGNORED_TEST = "SKIPPED : Ignored Test {}";
    public static final String BEFORE_METHOD = "START : Executing Test Method : {}";
    public static final String ON_TEST_START = "START : Test execution for methods in class {} ";
    public static final String ON_TEST_FINISHED = "FINISHED : Finished test execution for methods in class {} ";
    public static final String ON_SUITE_FINISH = "FINISHED : Finished execution for SUITE : {}";
    public static final String ON_SUITE_START = "START : Starting SUITE : {} execution \n" +
            "---------------------------------------------------------------------------------------- \n";

    public static final String SUCCESS_AFTER_METHOD = "SUCCESS : Successful Execution for @After Method [{}]";
    public static final String BEFORE_METHOD_CONF = "START : Executing @Before Method [{}]";
    public static final String FAILED_AFTER_METHOD = "Failed in @After Method : [{}]";


    @Override
    public void beforeConfiguration(ITestResult tr) {
        log.debug(BEFORE_METHOD_CONF,tr.getName());
    }

    @Override
    public void onConfigurationSuccess(ITestResult itr) {
        log.debug(SUCCESS_CONFIGURATION_METHODS,itr.getName());
    }

    @Override
    public void onConfigurationFailure(ITestResult itr) {
        log.error(String.format(ERROR_FAILED_WITH_CONFIGURATION_METHOD, itr.getMethod().getMethodName()));
    }


    @Override
    public void onConfigurationSkip(ITestResult itr) {

    }

    // This belongs to ISuiteListener and will execute before the Suite start
    @Override
    public void onStart(ISuite suite) {
        log = LoggerFactory.getLogger(suite.getName());
        log.info(ON_SUITE_START, suite.getName());
    }

    // This belongs to ISuiteListener and will execute, once the Suite is finished
    @Override
    public void onFinish(ISuite suite) {
        log = LoggerFactory.getLogger(suite.getName());
        log.info(ON_SUITE_FINISH, suite.getName());
    }

    // This belongs to ITestListener and will execute before starting of Test set/batch
    public void onStart(ITestContext context) {
        log = LoggerFactory.getLogger(context.getName());
        log.info(ON_TEST_START, context.getName());
    }

    // This belongs to ITestListener and will execute, once the Test set/batch is finished
    public void onFinish(ITestContext context) {
        log = LoggerFactory.getLogger(context.getName());
        log.info(ON_TEST_FINISHED, context.getName());
    }
    // This belongs to ITestListener and will execute only when the test is pass

    public void onTestSuccess(ITestResult arg0) {
        // This is calling the printTestResults method
        log = LoggerFactory.getLogger(arg0.getName());
        log.info(SUCCESS_AFTER_METHOD, arg0.getName());
    }

    // This belongs to ITestListener and will execute only on the event of fail test
    public void onTestFailure(ITestResult arg0) {
        // This is calling the printTestResults method
        log = LoggerFactory.getLogger(arg0.getName());
        log.error(FAILED_AFTER_METHOD, arg0.getMethod().getMethodName());
    }

    // This belongs to ITestListener and will execute before the main test start (@Test)
    public void onTestStart(ITestResult arg0) {
        log = LoggerFactory.getLogger(arg0.getName());
        log.info(BEFORE_METHOD, arg0.getMethod().getMethodName());
    }

    // This belongs to ITestListener and will execute only if any of the main test(@Test) get skipped
    public void onTestSkipped(ITestResult arg0) {
        log = LoggerFactory.getLogger(arg0.getName());
        log.info(SKIPPED_IGNORED_TEST,arg0.getName());
    }

    // This is just a piece of shit, ignore this
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

    }


    // This belongs to IInvokedMethodListener and will execute before every method including @Before @After @Test
    public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
//        log(String.format(BEFORE_METHOD,returnMethodName(arg0.getTestMethod())));
    }

    // This belongs to IInvokedMethodListener and will execute after every method including @Before @After @Test
    public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
//        log(String.format(AFTER_METHOD,returnMethodName(arg0.getTestMethod())));
    }

    // This will return method names to the calling function
    private String returnMethodName(ITestNGMethod method) {
        return method.getRealClass().getSimpleName() + "." + method.getMethodName();
    }

}