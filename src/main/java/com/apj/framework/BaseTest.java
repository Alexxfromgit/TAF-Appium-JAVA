package com.apj.framework;

import com.apj.framework.driver.AppiumServerManager;
import com.apj.framework.driver.DeviceSettings;
import com.apj.framework.driver.DriverFactory;
import com.apj.framework.driver.DriverManager;
import com.apj.framework.driver.Target;
import com.apj.framework.listeners.ScreenshotListener;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 * Opens one Appium session per test class and binds it to the current thread.
 * <p>
 * Without suite parameters the device comes from config.properties. A suite can override the device
 * per {@code <test>} block, which together with {@code parallel="tests"} runs the same classes on
 * several devices at once (see {@code suites/parallel.xml}).
 */
@Listeners(ScreenshotListener.class)
public abstract class BaseTest {

    /** The application or browser this class tests. */
    protected abstract Target target();

    @BeforeClass(alwaysRun = true)
    @Parameters({"platform", "deviceName", "platformVersion", "udid", "appiumPort", "systemPort"})
    public void startSession(@Optional String platform,
                             @Optional String deviceName,
                             @Optional String platformVersion,
                             @Optional String udid,
                             @Optional String appiumPort,
                             @Optional String systemPort) {
        DeviceSettings device = DeviceSettings.fromConfig(platform)
                .override(deviceName, platformVersion, udid, appiumPort, systemPort);
        DriverManager.setDriver(DriverFactory.create(device, target(), getClass().getSimpleName()));
    }

    @AfterClass(alwaysRun = true)
    public void stopSession() {
        DriverManager.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void stopAppiumServers() {
        AppiumServerManager.stopAll();
    }

    protected AppiumDriver driver() {
        return DriverManager.getDriver();
    }
}
