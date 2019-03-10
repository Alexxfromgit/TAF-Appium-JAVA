package pageobject;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.PropertyUtils;

public class FacebookMainPage extends BasePO {

    public FacebookMainPage(AppiumDriver driver) {
        super(driver);
    }

    @FindBy(id = "m_login_email")
    WebElement loginEmail;

    @FindBy(id = "m_login_password")
    WebElement loginPassword;

    @FindBy(id = "u_0_5")
    WebElement loginButton;

    public void fillLoginForm() {
        loginEmail.sendKeys(PropertyUtils.getProperty("valid.email"));
        loginPassword.sendKeys(PropertyUtils.getProperty("valid.password"));
        loginButton.click();
    }
}
