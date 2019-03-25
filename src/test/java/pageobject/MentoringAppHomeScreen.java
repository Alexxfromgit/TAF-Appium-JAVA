package pageobject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MentoringAppHomeScreen extends BasePO {

    public MentoringAppHomeScreen(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "com.example.mentoring:id/editText1")
    AndroidElement inputField;

    @AndroidFindBy(id = "com.example.mentoring:id/button1")
    AndroidElement buttonPost;

    public void inputTextToInputField(){
        inputField.sendKeys("test");
    }

    public void clickButtonPost(){
        buttonPost.click();
    }
}
