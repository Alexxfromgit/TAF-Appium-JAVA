package com.apj.framework.driver;

import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Holds one driver per thread, so TestNG can run {@code <test>} blocks (devices) in parallel.
 */
public final class DriverManager {

    private static final Logger LOG = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<>();

    private DriverManager() {
    }

    public static AppiumDriver getDriver() {
        return current().orElseThrow(() -> new IllegalStateException("No Appium session is bound to this thread"));
    }

    public static Optional<AppiumDriver> current() {
        return Optional.ofNullable(DRIVER.get());
    }

    public static void setDriver(AppiumDriver driver) {
        DRIVER.set(driver);
    }

    public static void quitDriver() {
        current().ifPresent(driver -> {
            try {
                driver.quit();
            } catch (RuntimeException e) {
                LOG.warn("Failed to quit the Appium session cleanly", e);
            } finally {
                DRIVER.remove();
            }
        });
    }
}
