package com.apj.framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.Duration;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;

/**
 * Layered configuration. A key is resolved in this order (first match wins):
 * <ol>
 *     <li>JVM system property ({@code -Dandroid.device.name=...})</li>
 *     <li>environment variable ({@code ANDROID_DEVICE_NAME=...})</li>
 *     <li>device profile {@code devices/<device>.properties}, selected with {@code -Ddevice=<device>}</li>
 *     <li>base file {@code config.properties}</li>
 * </ol>
 * All files are loaded from the classpath.
 */
public final class Config {

    private static final Logger LOG = LoggerFactory.getLogger(Config.class);
    private static final String BASE_FILE = "config.properties";
    private static final String DEVICE_KEY = "device";

    private static final Properties PROPS = load();

    private Config() {
    }

    public static Optional<String> find(String key) {
        String value = System.getProperty(key);
        if (isBlank(value)) {
            value = System.getenv(toEnvName(key));
        }
        if (isBlank(value)) {
            value = PROPS.getProperty(key);
        }
        return isBlank(value) ? Optional.empty() : Optional.of(value.trim());
    }

    public static String get(String key) {
        return find(key).orElseThrow(() -> new IllegalStateException(
                "Missing configuration value '" + key + "' (set it in " + BASE_FILE
                        + ", as -D" + key + " or as env " + toEnvName(key) + ")"));
    }

    public static String get(String key, String defaultValue) {
        return find(key).orElse(defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return find(key).map(Integer::parseInt).orElse(defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return find(key).map(Boolean::parseBoolean).orElse(defaultValue);
    }

    public static Duration getSeconds(String key, int defaultSeconds) {
        return Duration.ofSeconds(getInt(key, defaultSeconds));
    }

    static String toEnvName(String key) {
        return key.toUpperCase(Locale.ROOT).replace('.', '_').replace('-', '_');
    }

    private static Properties load() {
        Properties props = new Properties();
        loadInto(props, BASE_FILE);
        String device = System.getProperty(DEVICE_KEY, System.getenv(toEnvName(DEVICE_KEY)));
        if (!isBlank(device)) {
            loadInto(props, "devices/" + device + ".properties");
        }
        return props;
    }

    private static void loadInto(Properties props, String resource) {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalStateException("Configuration file '" + resource + "' not found on the classpath");
            }
            props.load(in);
            LOG.debug("Loaded configuration from {}", resource);
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to read " + resource, e);
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
