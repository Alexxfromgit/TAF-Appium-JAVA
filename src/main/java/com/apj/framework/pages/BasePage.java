package com.apj.framework.pages;

import com.apj.framework.config.Config;
import com.apj.framework.driver.DriverManager;
import com.apj.framework.waits.Waits;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Base for page / screen objects. Fields annotated with {@code @AndroidFindBy}, {@code @iOSXCUITFindBy}
 * or {@code @FindBy} (web) are initialised lazily by {@link AppiumFieldDecorator}.
 */
public abstract class BasePage {

    protected final AppiumDriver driver;
    protected final Waits waits;

    protected BasePage() {
        this(DriverManager.getDriver());
    }

    protected BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.waits = new Waits(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver, Config.getSeconds("wait.implicit", 10)), this);
    }

    protected void tap(WebElement element) {
        waits.clickable(element).click();
    }

    protected void type(WebElement element, String text) {
        WebElement visible = waits.visible(element);
        visible.clear();
        visible.sendKeys(text);
    }

    protected String textOf(WebElement element) {
        return waits.visible(element).getText();
    }
}
