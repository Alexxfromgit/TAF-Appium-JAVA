package com.apj.framework.waits;

import com.apj.framework.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Explicit waits. Use these instead of static sleeps.
 */
public final class Waits {

    private final WebDriver driver;
    private final Duration timeout;

    public Waits(WebDriver driver) {
        this(driver, Config.getSeconds("wait.explicit", 15));
    }

    public Waits(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.timeout = timeout;
    }

    public <T> T until(ExpectedCondition<T> condition) {
        return new WebDriverWait(driver, timeout).until(condition);
    }

    public WebElement clickable(WebElement element) {
        return until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement clickable(By locator) {
        return until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement visible(WebElement element) {
        return until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement visible(By locator) {
        return until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement present(By locator) {
        return until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public List<WebElement> allVisible(List<WebElement> elements) {
        return until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public boolean invisible(By locator) {
        return until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public boolean allInvisible(List<WebElement> elements) {
        return until(ExpectedConditions.invisibilityOfAllElements(elements));
    }

    public boolean notPresent(By locator) {
        return until(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(locator)));
    }

    public WebElement nestedPresent(WebElement parent, By childLocator) {
        return until(ExpectedConditions.presenceOfNestedElementLocatedBy(parent, childLocator));
    }

    public boolean textToBe(WebElement element, String text) {
        return until(ExpectedConditions.textToBePresentInElement(element, text));
    }
}
