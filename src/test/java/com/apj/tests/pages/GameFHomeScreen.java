package com.apj.tests.pages;

import com.apj.framework.pages.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

/**
 * GameF.apk is a Unity game: the whole UI is rendered into a single {@code android.view.View},
 * so interactions are taps on that surface.
 */
public class GameFHomeScreen extends BasePage {

    @AndroidFindBy(className = "android.view.View")
    private WebElement gameSurface;

    public boolean isLoaded() {
        return waits.visible(gameSurface).isDisplayed();
    }

    @Step("Tap the login screen")
    public GameFHomeScreen tapLoginScreen() {
        tap(gameSurface);
        return this;
    }
}
