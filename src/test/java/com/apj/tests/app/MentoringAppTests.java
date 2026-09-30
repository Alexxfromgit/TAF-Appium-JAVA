package com.apj.tests.app;

import com.apj.framework.BaseTest;
import com.apj.framework.driver.Target;
import com.apj.tests.pages.MentoringHomeScreen;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MentoringAppTests extends BaseTest {

    private MentoringHomeScreen homeScreen;

    @Override
    protected Target target() {
        return Target.app("mentoring");
    }

    @BeforeMethod(alwaysRun = true)
    public void openHomeScreen() {
        homeScreen = new MentoringHomeScreen();
    }

    @AfterMethod(alwaysRun = true)
    public void resetScreen() {
        if (homeScreen.isRevertChecked()) {
            homeScreen.toggleRevert();
        }
        homeScreen.clearInput().tapPost();
    }

    @Test(description = "Posted text is shown in the output field")
    public void postShowsEnteredText() {
        assertThat(homeScreen.post("test")).isEqualTo("test");
    }

    @Test(description = "Posted text is reversed when 'revert' is checked")
    public void revertCheckboxReversesText() {
        String posted = homeScreen.enterText("testing").toggleRevert().tapPost().output();

        assertThat(posted).isEqualTo("gnitset");
    }

    @DataProvider
    public Object[][] sections() {
        return new Object[][]{{0, "1"}, {1, "0"}, {2, "3"}};
    }

    @Test(dataProvider = "sections", description = "Each section in the action bar drop-down shows its own value")
    public void sectionDropDownChangesOutput(int sectionIndex, String expectedOutput) {
        assertThat(homeScreen.selectSection(sectionIndex).output()).isEqualTo(expectedOutput);
    }
}
