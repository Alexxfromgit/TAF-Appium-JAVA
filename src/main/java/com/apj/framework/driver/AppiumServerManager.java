package com.apj.framework.driver;

import com.apj.framework.config.Config;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Resolves the Appium server a session should talk to.
 * <ul>
 *     <li>{@code appium.server.mode=local} (default) - starts an Appium server on the requested port,
 *     one per port, shared by every session using that port and stopped by {@link #stopAll()}</li>
 *     <li>{@code appium.server.mode=remote} - uses {@code appium.server.url} as is
 *     (an already running server, Selenium Grid or a cloud provider such as Sauce Labs)</li>
 * </ul>
 */
public final class AppiumServerManager {

    private static final Logger LOG = LoggerFactory.getLogger(AppiumServerManager.class);
    private static final int DEFAULT_PORT = 4723;
    private static final Map<Integer, AppiumDriverLocalService> SERVICES = new ConcurrentHashMap<>();

    private AppiumServerManager() {
    }

    public static URL serverUrl(Integer port) {
        if (isRemote()) {
            return toUrl(Config.get("appium.server.url"));
        }
        int effectivePort = port != null ? port : DEFAULT_PORT;
        return SERVICES.computeIfAbsent(effectivePort, AppiumServerManager::start).getUrl();
    }

    public static boolean isRemote() {
        return "remote".equalsIgnoreCase(Config.get("appium.server.mode", "local"));
    }

    public static void stopAll() {
        SERVICES.values().forEach(service -> {
            if (service.isRunning()) {
                service.stop();
            }
        });
        SERVICES.clear();
    }

    private static AppiumDriverLocalService start(int port) {
        File logDir = new File("build/logs");
        logDir.mkdirs();

        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .usingPort(port)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.LOG_LEVEL, Config.get("appium.log.level", "info"))
                // lets UiAutomator2 fetch a Chromedriver that matches the device browser
                .withArgument(GeneralServerFlag.ALLOW_INSECURE, "uiautomator2:chromedriver_autodownload")
                .withLogFile(new File(logDir, "appium-" + port + ".log"));
        Config.find("appium.node.path").ifPresent(node -> builder.usingDriverExecutable(new File(node)));
        Config.find("appium.js.path").ifPresent(js -> builder.withAppiumJS(new File(js)));

        AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
        LOG.info("Starting local Appium server on port {}", port);
        service.start();
        return service;
    }

    private static URL toUrl(String url) {
        try {
            return URI.create(url).toURL();
        } catch (MalformedURLException | IllegalArgumentException e) {
            throw new IllegalStateException("Invalid appium.server.url: " + url, e);
        }
    }
}
