package com.apj.framework.driver;

import java.util.Objects;

/**
 * What a session is opened for: a native application (identified by its key in config.properties,
 * e.g. {@code app.mentoring.path}) or a mobile browser.
 */
public record Target(String appKey, String browserName) {

    public Target {
        if ((appKey == null) == (browserName == null)) {
            throw new IllegalArgumentException("Exactly one of appKey or browserName must be set");
        }
    }

    public static Target app(String appKey) {
        return new Target(Objects.requireNonNull(appKey), null);
    }

    public static Target browser(String browserName) {
        return new Target(null, Objects.requireNonNull(browserName));
    }

    public boolean isBrowser() {
        return browserName != null;
    }

    @Override
    public String toString() {
        return isBrowser() ? "browser:" + browserName : "app:" + appKey;
    }
}
