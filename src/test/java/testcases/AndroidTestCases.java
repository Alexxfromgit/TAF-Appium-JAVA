package testcases;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import pageobject.MentoringAppHomeScreen;
import utils.PropertyUtils;

import java.net.MalformedURLException;

import static testcases.BaseTest.getAbsolutePath;
import static testcases.BaseTest.waitUtils;

public class AndroidTestCases {

    protected AppiumDriverLocalService service;
    public AndroidDriver<AndroidElement> driver;

    @Parameters({"platformVersion", "deviceName", "port", "automationName"})
    @BeforeTest
    public void setUp(String platformVersion, String deviceName, String port, String automationName) throws MalformedURLException {
        service = new AppiumServiceBuilder().usingPort(Integer.parseInt(port)).build();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException("Appium service node not started");
        }

        String APP_NAME = PropertyUtils.getProperty("android.app.name");
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

    @AfterMethod
    public void cleanUp() throws MalformedURLException {
        MentoringAppHomeScreen appHomeScreen = new MentoringAppHomeScreen(driver);
        appHomeScreen.getInputField().clear();
        appHomeScreen.clickButtonPost();
    }

    @Test
    public void test_application_first() {
        MentoringAppHomeScreen appHomeScreen = new MentoringAppHomeScreen(driver);
        appHomeScreen.inputTextToInputField("test");
        appHomeScreen.clickButtonPost();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
    }
}
