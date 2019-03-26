package testcases;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobject.HomeScreenPO;

import java.net.MalformedURLException;
import java.net.URL;

public class TestCasesGameF extends BaseTest {

    @BeforeTest
    @Override
    public void setUpPage() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        setDesiredCapabilitiesForAndroid(capabilities);
        driver = new AppiumDriver<>(new URL(APPIUM_SERVER_URL), capabilities);
    }

    @Test
    public void test_application_game_f() throws InterruptedException {
        Thread.sleep(6000);
        HomeScreenPO homeScreenPO = new HomeScreenPO(driver);
        homeScreenPO.tapOnLoginScreenTextView();
    }
}
