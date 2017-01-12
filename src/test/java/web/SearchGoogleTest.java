package web;


import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.webPages.GooglePage;

/**
 * Created by tom.ben-simhon on 1/11/2017.
 */
public class SearchGoogleTest  extends TestBaseWeb{

    GooglePage googlePage;
    @BeforeTest
    public void setUpGooglePageTests() {
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
