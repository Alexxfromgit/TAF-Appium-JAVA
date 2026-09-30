package com.apj.framework.driver;

import com.apj.framework.config.Config;

/**
 * Device level session settings. Defaults come from {@link Config}; TestNG suite parameters can override
 * any of them, which is how one test class runs on several devices in parallel.
 *
 * @param appiumPort port of the local Appium server serving this device ({@code null} = default / remote)
 * @param systemPort UiAutomator2 system port (Android) or WDA local port (iOS); must be unique per parallel device
 */
public record DeviceSettings(
        Platform platform,
        String deviceName,
        String platformVersion,
        String udid,
        String automationName,
        Integer appiumPort,
        Integer systemPort) {

    public static DeviceSettings fromConfig(String platformOverride) {
        Platform platform = Platform.of(firstNonBlank(platformOverride, Config.get("platform", "android")));
        String prefix = platform.configPrefix();
        return new DeviceSettings(
                platform,
                Config.get(prefix + ".device.name"),
                Config.find(prefix + ".platform.version").orElse(null),
                Config.find(prefix + ".udid").orElse(null),
                Config.get(prefix + ".automation.name", platform == Platform.IOS ? "XCUITest" : "UiAutomator2"),
                Config.find("appium.port").map(Integer::valueOf).orElse(null),
                null);
    }

    /** Returns a copy where every non-blank argument replaces the configured value. */
    public DeviceSettings override(String deviceName, String platformVersion, String udid,
                                   String appiumPort, String systemPort) {
        return new DeviceSettings(
                platform,
                firstNonBlank(deviceName, this.deviceName),
                firstNonBlank(platformVersion, this.platformVersion),
                firstNonBlank(udid, this.udid),
                automationName,
                isBlank(appiumPort) ? this.appiumPort : Integer.valueOf(appiumPort.trim()),
                isBlank(systemPort) ? this.systemPort : Integer.valueOf(systemPort.trim()));
    }

    private static String firstNonBlank(String value, String fallback) {
        return isBlank(value) ? fallback : value.trim();
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
