package com.apj.framework.driver;

import java.util.Locale;

public enum Platform {
    ANDROID("android"),
    IOS("ios");

    private final String configPrefix;

    Platform(String configPrefix) {
        this.configPrefix = configPrefix;
    }

    /** Prefix of the platform specific keys in config.properties, e.g. {@code android.device.name}. */
    public String configPrefix() {
        return configPrefix;
    }

    public static Platform of(String value) {
        return valueOf(value.trim().toUpperCase(Locale.ROOT));
    }
}
