package com.apj.framework.listeners;

import com.apj.framework.config.Config;
import com.apj.framework.driver.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Captures a screenshot of the current thread's session after each test: always on failure,
 * on success only when {@code screenshots.on.success=true}. Screenshots are stored under
 * {@code build/screenshots/} and attached to the Allure report.
 */
public class ScreenshotListener implements ITestListener {

    private static final Logger LOG = LoggerFactory.getLogger(ScreenshotListener.class);
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    @Override
    public void onTestFailure(ITestResult result) {
        capture(result, "failures");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if (Config.getBoolean("screenshots.on.success", false)) {
            capture(result, "success");
        }
    }

    private void capture(ITestResult result, String folder) {
        DriverManager.current().ifPresent(driver -> {
            String name = result.getTestClass().getRealClass().getSimpleName() + "." + result.getName();
            try {
                byte[] png = driver.getScreenshotAs(OutputType.BYTES);
                Path dir = Path.of("build", "screenshots", folder);
                Files.createDirectories(dir);
                Path file = dir.resolve(name + "_" + LocalDateTime.now().format(TIMESTAMP) + ".png");
                Files.write(file, png);
                Allure.addAttachment(name, "image/png", new ByteArrayInputStream(png), "png");
                LOG.info("Screenshot saved: {}", file.toAbsolutePath());
            } catch (IOException | RuntimeException e) {
                LOG.warn("Could not capture screenshot for {}", name, e);
            }
        });
    }
}
