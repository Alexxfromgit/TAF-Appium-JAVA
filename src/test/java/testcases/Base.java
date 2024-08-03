package testcases;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import utils.PropertyUtils;
import utils.ScreenshotUtility;
import utils.WaitUtils;

import java.io.File;

@Listeners({ScreenshotUtility.class})
public abstract class Base {

    public static AppiumDriver driver;
    public static final String APPIUM_SERVER_URL = PropertyUtils.getProperty("appium.server.url", "http://127.0.0.1:4723/wd/hub");
    public static final int IMPLICIT_WAIT = PropertyUtils.getIntegerProperty("implicitWait", 30);
    public static WaitUtils waitUtils = new WaitUtils();

    @AfterSuite
    public void tearDownAppium() {
        quitDriver();
    }

    protected void setDesiredCapabilitiesForAndroid(DesiredCapabilities desiredCapabilities) {
        String PLATFORM_NAME = PropertyUtils.getProperty("android.platform");
        String PLATFORM_VERSION = PropertyUtils.getProperty("android.platform.version");
        String APP_NAME = PropertyUtils.getProperty("android.app.name");
        String APP_RELATIVE_PATH = PropertyUtils.getProperty("android.app.location") + APP_NAME;
        String APP_PATH = getAbsolutePath(APP_RELATIVE_PATH);
        String DEVICE_NAME = PropertyUtils.getProperty("android.device.name");
        String APP_PACKAGE_NAME = PropertyUtils.getProperty("android.app.packageName");
        String APP_ACTIVITY_NAME = PropertyUtils.getProperty("android.app.activityName");
        String APP_FULL_RESET = PropertyUtils.getProperty("android.app.full.reset");
        String APP_NO_RESET = PropertyUtils.getProperty("android.app.no.reset");

        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiAutomator1");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        desiredCapabilities.setCapability(MobileCapabilityType.APP, APP_PATH);
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, APP_PACKAGE_NAME);
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, APP_ACTIVITY_NAME);
        desiredCapabilities.setCapability(MobileCapabilityType.FULL_RESET, APP_FULL_RESET);
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, APP_NO_RESET);
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
    }

    protected void setDesiredCapabilitiesForAndroidWeb(DesiredCapabilities desiredCapabilities) {
        String PLATFORM_NAME = PropertyUtils.getProperty("android.platform");
        String PLATFORM_VERSION = PropertyUtils.getProperty("android.platform.version");
        String DEVICE_NAME = PropertyUtils.getProperty("android.device.name");

        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
        desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_VERSION, "40.0.2214");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiAutomator1");
    }

    public static WebDriver getScreenshotableWebDriver() {
        return new Augmenter().augment(driver);
    }

    private static String getAbsolutePath(String appRelativePath) {
        return new File(appRelativePath).getAbsolutePath();
    }

    private void quitDriver(){
        try {
            this.driver.quit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}