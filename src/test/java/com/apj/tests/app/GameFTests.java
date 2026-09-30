package com.apj.tests.app;

import com.apj.framework.BaseTest;
import com.apj.framework.driver.Target;
import com.apj.tests.pages.GameFHomeScreen;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameFTests extends BaseTest {

    @Override
    protected Target target() {
        return Target.app("gamef");
    }

    @Test(description = "Game starts in the Unity player and accepts a tap on the login screen")
    public void gameStartsAndAcceptsTap() {
        GameFHomeScreen homeScreen = new GameFHomeScreen();
        assertThat(homeScreen.isLoaded()).isTrue();

        homeScreen.tapLoginScreen();

        assertThat(((AndroidDriver) driver()).currentActivity()).endsWith("UnityPlayerActivity");
    }
}
