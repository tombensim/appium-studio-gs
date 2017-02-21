package web.tests;


import framework.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import web.pages.GooglePage;

/**
 * Created by tom.ben-simhon on 1/11/2017.
 */
public class SearchGoogleTest  extends TestBase {

    public static final String GOOGLE_URL = "https://www.google.com";
    GooglePage googlePage;
    @BeforeMethod
    public void setUpGooglePageTests() {
        driver.get(GOOGLE_URL);
        driver.context("WEBVIEW_1");
        System.out.println(driver.getContext());
        googlePage = new GooglePage(this.driver);
    }

    @Test
    public void testSearchForGoogle()
    {
        googlePage.search("Google");
    }
}
