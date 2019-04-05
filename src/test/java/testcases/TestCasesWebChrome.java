package testcases;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobject.FacebookMainPage;

import java.net.MalformedURLException;
import java.net.URL;

public class TestCasesWebChrome extends BaseTest {

    @BeforeMethod
    @Override
    public void setUpPage() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        setDesiredCapabilitiesForAndroidWeb(capabilities);
        driver = new AppiumDriver<>(new URL(APPIUM_SERVER_URL), capabilities);
    }

    @Test
    public void test_chrome_facebook_login() {
        driver.get("https://m.facebook.com/");
        waitUtils.staticWait(2000);

        FacebookMainPage facebookMainPage = new FacebookMainPage(driver);
        facebookMainPage.fillLoginForm();
        waitUtils.staticWait(2000);
    }
}
