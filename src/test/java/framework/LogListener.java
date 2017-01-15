package framework;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.util.logging.Logger;

public class LogListener implements ITestListener, ISuiteListener, IInvokedMethodListener {


    public static final String AFTER_METHOD = "Finished Execution for test method : %s";
    public static final String BEFORE_METHOD = "Executing Test Method : %s \n";
    public static final String ON_TEST_START = "Starting test execution for methods in class %s  \n";
    public static final String ON_TEST_FINISHED = "Finished test execution for methods in class %s  \n";
    public static final String ON_SUITE_FINISH = "Finished execution for SUITE : %s \n";
    public static final String ON_SUITE_START = "Starting SUITE : %s execution \n";

    public static final boolean LOG_TO_SOUT = true;

    // This belongs to ISuiteListener and will execute before the Suite start
    @Override
    public void onStart(ISuite suite) {
        Reporter.log(String.format(ON_SUITE_START,suite.getName() ), LOG_TO_SOUT);
    }

    // This belongs to ISuiteListener and will execute, once the Suite is finished
    @Override
    public void onFinish(ISuite suite) {
        Reporter.log(String.format(ON_SUITE_FINISH,suite.getName()), LOG_TO_SOUT);
    }

    // This belongs to ITestListener and will execute before starting of Test set/batch
    public void onStart(ITestContext context) {
        Reporter.log(String.format(ON_TEST_START,context.getName()),LOG_TO_SOUT);
    }

    // This belongs to ITestListener and will execute, once the Test set/batch is finished
    public void onFinish(ITestContext context) {
        Reporter.log(String.format(ON_TEST_FINISHED,context.getName()),LOG_TO_SOUT);
    }
    // This belongs to ITestListener and will execute only when the test is pass

    public void onTestSuccess(ITestResult arg0) {
        // This is calling the printTestResults method
        printTestResults(arg0);
    }

    // This belongs to ITestListener and will execute only on the event of fail test
    public void onTestFailure(ITestResult arg0) {
        // This is calling the printTestResults method
        printTestResults(arg0);
    }
    // This belongs to ITestListener and will execute before the main test start (@Test)
    public void onTestStart(ITestResult arg0) {
        Reporter.log(String.format(BEFORE_METHOD,arg0.getMethod().getMethodName()),LOG_TO_SOUT);
    }
    // This belongs to ITestListener and will execute only if any of the main test(@Test) get skipped
    public void onTestSkipped(ITestResult arg0) {
        printTestResults(arg0);
    }
    // This is just a piece of shit, ignore this
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

    }
    // This is the method which will be executed in case of test pass or fail
    // This will provide the information on the test
    private void printTestResults(ITestResult result) {

        Reporter.log(String.format(AFTER_METHOD,result.getTestClass().getName()), LOG_TO_SOUT);

        if (result.getParameters().length != 0) {
            String params = null;
            for (Object parameter : result.getParameters()) {
                params += parameter.toString() + ",";
            }
            Reporter.log("Test Method had the following parameters : " + params, true);
        }

        String status = null;

        switch (result.getStatus()) {

            case ITestResult.SUCCESS:
                status = "Pass";
                break;
            case ITestResult.FAILURE:
                status = "Failed";
                break;
            case ITestResult.SKIP:
                status = "Skipped";
        }
        Reporter.log("Test Status: " + status, true);
    }

    // This belongs to IInvokedMethodListener and will execute before every method including @Before @After @Test
    public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
//        Reporter.log(String.format(BEFORE_METHOD,returnMethodName(arg0.getTestMethod())), LOG_TO_SOUT);
    }

    // This belongs to IInvokedMethodListener and will execute after every method including @Before @After @Test
    public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
//        Reporter.log(String.format(AFTER_METHOD,returnMethodName(arg0.getTestMethod())), LOG_TO_SOUT);
    }
    // This will return method names to the calling function
    private String returnMethodName(ITestNGMethod method) {
        return method.getRealClass().getSimpleName() + "." + method.getMethodName();
    }

}