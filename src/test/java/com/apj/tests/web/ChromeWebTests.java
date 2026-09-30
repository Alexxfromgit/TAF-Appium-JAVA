package com.apj.tests.web;

import com.apj.framework.BaseTest;
import com.apj.framework.config.Config;
import com.apj.framework.driver.Target;
import com.apj.tests.pages.web.LoginPage;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChromeWebTests extends BaseTest {

    @Override
    protected Target target() {
        return Target.browser(Config.get("web.browser", "Chrome"));
    }

    @Test(description = "Mobile browser session opens a page")
    public void browserSessionOpensPage() {
        driver().get(Config.get("web.base.url"));

        assertThat(driver().getTitle()).isEqualTo("The Internet");
    }

    @Test(description = "Valid credentials log the user in")
    public void validLoginSucceeds() {
        LoginPage loginPage = new LoginPage().open()
                .loginAs(Config.get("web.valid.username"), Config.get("web.valid.password"));

        assertThat(loginPage.flashMessage()).contains("You logged into a secure area!");
        assertThat(driver().getCurrentUrl()).endsWith("/secure");
    }

    @Test(description = "Invalid credentials are rejected")
    public void invalidLoginIsRejected() {
        LoginPage loginPage = new LoginPage().open().loginAs("wrong-user", "wrong-password");

        assertThat(loginPage.flashMessage()).contains("Your username is invalid!");
    }
}
