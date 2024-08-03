package testcases;

import static testcases.BaseTest.getAbsolutePath;
import static testcases.BaseTest.waitUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageobject.HomeScreenPO;
import utils.PropertyUtils;

import java.util.Objects;

public class TestCasesGameF {

    protected AppiumDriverLocalService service;
    public AndroidDriver<AndroidElement> driver;

    @BeforeTest
    @Parameters({"platformVersion", "deviceName", "port", "automationName"})
    public void setUpPage(String platformVersion, String deviceName, String port, String automationName) {
        service = new AppiumServiceBuilder().usingPort(Integer.parseInt(port)).build();
        service.start();

        if (Objects.isNull(service) || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException("Appium service node not started");
        }

        String APP_NAME = PropertyUtils.getProperty("android.app.f.name");
        String APP_RELATIVE_PATH = PropertyUtils.getProperty("android.app.location") + APP_NAME;
        String APP_PATH = getAbsolutePath(APP_RELATIVE_PATH);

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        dc.setCapability(MobileCapabilityType.APP, APP_PATH);
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);

        driver = new AndroidDriver<AndroidElement>(service.getUrl(), dc);
        waitUtils.staticWait(4000);
    }

//    @BeforeTest
//    @Override
//    public void setUpPage() throws MalformedURLException {
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        setDesiredCapabilitiesForAndroid(capabilities);
//        driver = new AppiumDriver<>(new URL(APPIUM_SERVER_URL), capabilities);
//    }

    @Test
    public void test_application_game_f() throws InterruptedException {
        Thread.sleep(6000);
        HomeScreenPO homeScreenPO = new HomeScreenPO(driver);
        homeScreenPO.tapOnLoginScreenTextView();
    }
}
