package com.apj.tests.pages;

import com.apj.framework.pages.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

import java.util.List;

/** Main screen of Mentoring.apk (package {@code com.example.mentoring}). */
public class MentoringHomeScreen extends BasePage {

    @AndroidFindBy(id = "com.example.mentoring:id/editText1")
    private WebElement inputField;

    @AndroidFindBy(id = "com.example.mentoring:id/button1")
    private WebElement postButton;

    @AndroidFindBy(id = "android:id/action_bar_spinner")
    private WebElement sectionDropDown;

    @AndroidFindBy(xpath = "//android.widget.ListView/android.widget.TextView")
    private List<WebElement> sectionItems;

    @AndroidFindBy(id = "com.example.mentoring:id/textView1")
    private WebElement outputField;

    @AndroidFindBy(id = "com.example.mentoring:id/checkBox1")
    private WebElement revertCheckbox;

    @Step("Enter '{text}'")
    public MentoringHomeScreen enterText(String text) {
        type(inputField, text);
        return this;
    }

    @Step("Clear the input field")
    public MentoringHomeScreen clearInput() {
        waits.visible(inputField).clear();
        return this;
    }

    @Step("Tap Post")
    public MentoringHomeScreen tapPost() {
        tap(postButton);
        return this;
    }

    @Step("Toggle 'revert' checkbox")
    public MentoringHomeScreen toggleRevert() {
        tap(revertCheckbox);
        return this;
    }

    public boolean isRevertChecked() {
        return Boolean.parseBoolean(waits.visible(revertCheckbox).getAttribute("checked"));
    }

    @Step("Select section #{index}")
    public MentoringHomeScreen selectSection(int index) {
        tap(sectionDropDown);
        tap(waits.allVisible(sectionItems).get(index));
        return this;
    }

    public String output() {
        return textOf(outputField);
    }

    @Step("Post '{text}'")
    public String post(String text) {
        return enterText(text).tapPost().output();
    }
}
