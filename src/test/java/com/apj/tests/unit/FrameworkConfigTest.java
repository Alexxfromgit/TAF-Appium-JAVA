package com.apj.tests.unit;

import com.apj.framework.config.Config;
import com.apj.framework.driver.DeviceSettings;
import com.apj.framework.driver.Platform;
import com.apj.framework.driver.Target;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** Device-free checks of the framework plumbing; this is what CI runs. */
public class FrameworkConfigTest {

    @AfterMethod(alwaysRun = true)
    public void clearOverrides() {
        System.clearProperty("android.device.name");
    }

    @Test
    public void readsValuesFromConfigFile() {
        assertThat(Config.get("platform")).isEqualTo("android");
        assertThat(Config.getSeconds("wait.explicit", 1)).isEqualTo(Duration.ofSeconds(15));
    }

    @Test
    public void systemPropertyOverridesConfigFile() {
        System.setProperty("android.device.name", "emulator-9999");

        assertThat(Config.get("android.device.name")).isEqualTo("emulator-9999");
    }

    @Test
    public void missingRequiredValueHasHelpfulMessage() {
        assertThatThrownBy(() -> Config.get("does.not.exist"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("-Ddoes.not.exist")
                .hasMessageContaining("DOES_NOT_EXIST");
    }

    @Test
    public void defaultsAreUsedForMissingValues() {
        assertThat(Config.getInt("does.not.exist", 7)).isEqualTo(7);
        assertThat(Config.getBoolean("does.not.exist", true)).isTrue();
    }

    @Test
    public void suiteParametersOverrideConfiguredDevice() {
        DeviceSettings configured = DeviceSettings.fromConfig(null);
        DeviceSettings overridden = configured.override("emulator-5556", "12", null, "4724", "8201");

        assertThat(configured.platform()).isEqualTo(Platform.ANDROID);
        assertThat(configured.automationName()).isEqualTo("UiAutomator2");
        assertThat(overridden.deviceName()).isEqualTo("emulator-5556");
        assertThat(overridden.platformVersion()).isEqualTo("12");
        assertThat(overridden.appiumPort()).isEqualTo(4724);
        assertThat(overridden.systemPort()).isEqualTo(8201);
        assertThat(overridden.udid()).isEqualTo(configured.udid());
    }

    @Test
    public void blankParametersKeepConfiguredValues() {
        DeviceSettings configured = DeviceSettings.fromConfig(null);

        assertThat(configured.override(" ", null, "", null, null)).isEqualTo(configured);
    }

    @Test
    public void iosPlatformUsesXcuiTestByDefault() {
        System.setProperty("ios.device.name", "iPhone 16");
        try {
            assertThat(DeviceSettings.fromConfig("ios").automationName()).isEqualTo("XCUITest");
        } finally {
            System.clearProperty("ios.device.name");
        }
    }

    @Test
    public void targetIsEitherAppOrBrowser() {
        assertThat(Target.app("mentoring").isBrowser()).isFalse();
        assertThat(Target.browser("Chrome").isBrowser()).isTrue();
        assertThatThrownBy(() -> new Target("app", "Chrome")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Target(null, null)).isInstanceOf(IllegalArgumentException.class);
    }
}
