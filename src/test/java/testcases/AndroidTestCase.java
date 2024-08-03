package testcases;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import pageobject.MentoringAppHomeScreen;
import utils.PropertyUtils;

import java.util.Objects;

import static testcases.BaseTest.getAbsolutePath;
import static testcases.BaseTest.waitUtils;

public class AndroidTestCase {

    protected AppiumDriverLocalService service;
    public AndroidDriver<AndroidElement> driver;

    @Parameters({"platformVersion", "deviceName", "port", "automationName"})
    @BeforeTest
    public void setUpPage(String platformVersion, String deviceName, String port, String automationName) {
        service = new AppiumServiceBuilder().usingPort(Integer.parseInt(port)).build();
        service.start();

        if (Objects.isNull(service) || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException("Appium service node not started");
        }

        String appName = PropertyUtils.getProperty("android.app.name");
        String appRelativePath = PropertyUtils.getProperty("android.app.location") + appName;
        String appPath = getAbsolutePath(appRelativePath);

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
        dc.setCapability(MobileCapabilityType.APP, appPath);
        dc.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, automationName);

        driver = new AndroidDriver<AndroidElement>(service.getUrl(), dc);
        waitUtils.staticWait(4000);
    }

    @AfterMethod
    public void cleanUp() {
        MentoringAppHomeScreen appHomeScreen = new MentoringAppHomeScreen(driver);
        appHomeScreen.getInputField().clear();
        appHomeScreen.clickButtonPost();
    }

    @Test
    public void test_application_first() {
        MentoringAppHomeScreen appHomeScreen = new MentoringAppHomeScreen(driver);
        appHomeScreen.inputTextToInputField("test");
        appHomeScreen.clickButtonPost();
    }

    @Test
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

    @Test
    public void test_checkbox_functionality() {
        MentoringAppHomeScreen appHomeScreen = new MentoringAppHomeScreen(driver);

        appHomeScreen.checkPostFunctionality("test");
        appHomeScreen.checkRevertFunctionality("testing");
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
    }
}
