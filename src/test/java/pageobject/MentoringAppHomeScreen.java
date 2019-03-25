package pageobject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.testng.Assert;

import java.util.List;

public class MentoringAppHomeScreen extends BasePO {

    public MentoringAppHomeScreen(AppiumDriver driver) {
        super(driver);
    }

    @AndroidFindBy(id = "com.example.mentoring:id/editText1")
    AndroidElement inputField;

    @AndroidFindBy(id = "com.example.mentoring:id/button1")
    AndroidElement buttonPost;

    @AndroidFindBy(id = "android:id/action_bar_spinner")
    AndroidElement dropDownSectionMenu;

    @AndroidFindBy(xpath = "/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.TextView")
    List<AndroidElement> sectionElements;

    @AndroidFindBy(id = "com.example.mentoring:id/textView1")
    AndroidElement outputField;

    @AndroidFindBy(id = "com.example.mentoring:id/checkBox1")
    AndroidElement checkboxRevert;

    public AndroidElement getInputField() {
        return inputField;
    }

    public void inputTextToInputField(String inputValue){
        inputField.sendKeys(inputValue);
    }

    public void clickButtonPost(){
        buttonPost.click();
    }

    public void clickOnSectionDropDownMenu(){
        dropDownSectionMenu.click();
    }

    public void clickOnSectionItem(Integer itemNumber){
        sectionElements.get(itemNumber).click();
    }

    public void checkOutputValue(String expectedValue){
        Assert.assertEquals(outputField.getText(), expectedValue);
    }

    public void clickOnCheckboxRevert() {
        checkboxRevert.click();
    }
}
