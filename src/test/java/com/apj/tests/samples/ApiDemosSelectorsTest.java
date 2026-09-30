package com.apj.tests.samples;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/** Demonstrates the locator strategies available on Android. */
public class ApiDemosSelectorsTest extends ApiDemosTest {

    @BeforeMethod(alwaysRun = true)
    public void openMainActivity() {
        startActivity(".ApiDemos");
    }

    @Test(description = "Accessibility id maps to 'content-desc'")
    public void findByAccessibilityId() {
        assertThat(driver().findElements(AppiumBy.accessibilityId("Content"))).hasSize(1);
    }

    @Test(description = "Id maps to 'resource-id'")
    public void findById() {
        assertThat(driver().findElements(By.id("android:id/action_bar_container"))).hasSize(1);
    }

    @Test(description = "Class name maps to the Java class of the view")
    public void findByClassName() {
        assertThat(driver().findElements(By.className("android.widget.FrameLayout"))).hasSizeGreaterThan(1);
    }

    @Test(description = "XPath works on the native view hierarchy")
    public void findByXPath() {
        assertThat(driver().findElements(By.xpath("//*[@class='android.widget.FrameLayout']"))).hasSizeGreaterThan(1);
    }

    @Test(description = "UiAutomator selector is the fastest Android native strategy")
    public void findByAndroidUiAutomator() {
        assertThat(driver().findElements(AppiumBy.androidUIAutomator("new UiSelector().text(\"Views\")"))).hasSize(1);
    }
}
