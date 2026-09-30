package com.apj.framework.driver;

import com.apj.framework.config.Config;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.remote.options.BaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Builds W3C capabilities through the typed Appium option classes and opens the session.
 */
public final class DriverFactory {

    private static final Logger LOG = LoggerFactory.getLogger(DriverFactory.class);

    private DriverFactory() {
    }

    public static AppiumDriver create(DeviceSettings device, Target target, String sessionName) {
        URL serverUrl = AppiumServerManager.serverUrl(device.appiumPort());
        BaseOptions<?> options = switch (device.platform()) {
            case ANDROID -> androidOptions(device, target);
            case IOS -> iosOptions(device, target);
        };
        addCloudOptions(options, sessionName);

        LOG.info("Opening {} session for {} on '{}' via {}", device.platform(), target, device.deviceName(), serverUrl);
        return switch (device.platform()) {
            case ANDROID -> new AndroidDriver(serverUrl, options);
            case IOS -> new IOSDriver(serverUrl, options);
        };
    }

    private static UiAutomator2Options androidOptions(DeviceSettings device, Target target) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setAutomationName(device.automationName())
                .setDeviceName(device.deviceName())
                .setNewCommandTimeout(Config.getSeconds("appium.new.command.timeout", 120))
                .setAutoGrantPermissions(true);
        if (device.platformVersion() != null) {
            options.setPlatformVersion(device.platformVersion());
        }
        if (device.udid() != null) {
            options.setUdid(device.udid());
        }
        if (device.systemPort() != null) {
            options.setSystemPort(device.systemPort());
        }

        if (target.isBrowser()) {
            options.withBrowserName(target.browserName());
        } else {
            String key = appKey(device, target);
            Config.find(key + ".path").ifPresent(path -> options.setApp(absolutePath(path)));
            Config.find(key + ".package").ifPresent(options::setAppPackage);
            Config.find(key + ".activity").ifPresent(options::setAppActivity);
            options.setNoReset(Config.getBoolean("app.no.reset", false));
            options.setFullReset(Config.getBoolean("app.full.reset", false));
        }
        return options;
    }

    private static XCUITestOptions iosOptions(DeviceSettings device, Target target) {
        XCUITestOptions options = new XCUITestOptions()
                .setAutomationName(device.automationName())
                .setDeviceName(device.deviceName())
                .setNewCommandTimeout(Config.getSeconds("appium.new.command.timeout", 120))
                .setAutoAcceptAlerts(true);
        if (device.platformVersion() != null) {
            options.setPlatformVersion(device.platformVersion());
        }
        if (device.udid() != null) {
            options.setUdid(device.udid());
        }
        if (device.systemPort() != null) {
            options.setWdaLocalPort(device.systemPort());
        }

        if (target.isBrowser()) {
            options.withBrowserName(target.browserName());
        } else {
            String key = appKey(device, target);
            Config.find(key + ".path").ifPresent(path -> options.setApp(absolutePath(path)));
            Config.find(key + ".bundle.id").ifPresent(options::setBundleId);
            options.setNoReset(Config.getBoolean("app.no.reset", false));
            options.setFullReset(Config.getBoolean("app.full.reset", false));
        }
        return options;
    }

    /** Adds vendor options when running against Sauce Labs (credentials usually come from env variables). */
    private static void addCloudOptions(BaseOptions<?> options, String sessionName) {
        Config.find("sauce.username").ifPresent(username -> {
            Map<String, Object> sauce = new HashMap<>();
            sauce.put("username", username);
            sauce.put("accessKey", Config.get("sauce.access.key"));
            sauce.put("name", sessionName);
            Config.find("sauce.build").ifPresent(build -> sauce.put("build", build));
            Config.find("sauce.appium.version").ifPresent(version -> sauce.put("appiumVersion", version));
            options.setCapability("sauce:options", sauce);
        });
    }

    /** Config key prefix of an application, e.g. {@code android.app.mentoring}. */
    private static String appKey(DeviceSettings device, Target target) {
        return device.platform().configPrefix() + ".app." + target.appKey();
    }

    private static String absolutePath(String path) {
        // Remote URLs (e.g. storage:filename=... on Sauce Labs) are passed through untouched
        return path.contains(":") && !new File(path).isAbsolute() ? path : new File(path).getAbsolutePath();
    }
}
