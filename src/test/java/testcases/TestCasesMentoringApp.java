package testcases;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import pageobject.MentoringAppHomeScreen;

import java.net.MalformedURLException;
import java.net.URL;

public class TestCasesMentoringApp extends BaseTest {

    @BeforeTest
    @Override
    public void setUpPage() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        setDesiredCapabilitiesForAndroid(capabilities);
        driver = new AppiumDriver<>(new URL(APPIUM_SERVER_URL), capabilities);
    }

    @AfterMethod
    public void cleanUp() {
        MentoringAppHomeScreen appHomeScreen = new MentoringAppHomeScreen(driver);
        appHomeScreen.getInputField().clear();
        appHomeScreen.clickButtonPost();
    }

    @Test(priority = 2)
    public void test_application_first() {
        MentoringAppHomeScreen appHomeScreen = new MentoringAppHomeScreen(driver);
        appHomeScreen.inputTextToInputField("test");
        appHomeScreen.clickButtonPost();
    }

    @Test(priority = 1)
    public void test_dropdown_section_items() {
        MentoringAppHomeScreen appHomeScreen = new MentoringAppHomeScreen(driver);

        appHomeScreen.clickOnSectionDropDownMenu();
        appHomeScreen.clickOnSectionItem(0);
        appHomeScreen.checkOutputValue("1");
        appHomeScreen.clickOnSectionDropDownMenu();
        appHomeScreen.clickOnSectionItem(1);
        appHomeScreen.checkOutputValue("0");
        appHomeScreen.clickOnSectionDropDownMenu();
        appHomeScreen.clickOnSectionItem(2);
        appHomeScreen.checkOutputValue("3");
    }

    @Test(priority = 3)
    public void test_checkbox_functionality() {
        MentoringAppHomeScreen appHomeScreen = new MentoringAppHomeScreen(driver);

        appHomeScreen.inputTextToInputField("test");
        appHomeScreen.clickButtonPost();
        appHomeScreen.checkOutputValue("test");
        appHomeScreen.inputTextToInputField("testing");
        appHomeScreen.clickOnCheckboxRevert();
        appHomeScreen.clickButtonPost();
        appHomeScreen.checkOutputValue("gnitset");
    }
}
