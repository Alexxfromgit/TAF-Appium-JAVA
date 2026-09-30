package com.apj.tests.pages.web;

import com.apj.framework.config.Config;
import com.apj.framework.pages.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/** Login form of https://the-internet.herokuapp.com/login - a public practice site for UI automation. */
public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(id = "flash")
    private WebElement flashMessage;

    @Step("Open login page")
    public LoginPage open() {
        driver.get(Config.get("web.base.url") + "/login");
        return this;
    }

    @Step("Log in as '{username}'")
    public LoginPage loginAs(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        tap(loginButton);
        return this;
    }

    public String flashMessage() {
        return textOf(flashMessage);
    }
}
