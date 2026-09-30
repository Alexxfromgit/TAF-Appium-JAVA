package com.apj.tests.samples;

import com.apj.framework.BaseTest;
import com.apj.framework.driver.Target;
import io.appium.java_client.android.AndroidDriver;

import java.util.Map;

/**
 * Base for the Appium "ApiDemos" samples. Get the app with {@code ./gradlew downloadApiDemos}.
 */
abstract class ApiDemosTest extends BaseTest {

    static final String PACKAGE = "io.appium.android.apis";

    @Override
    protected Target target() {
        return Target.app("apidemos");
    }

    AndroidDriver android() {
        return (AndroidDriver) driver();
    }

    void startActivity(String activity) {
        android().executeScript("mobile: startActivity", Map.of("intent", PACKAGE + "/" + activity));
    }
}
