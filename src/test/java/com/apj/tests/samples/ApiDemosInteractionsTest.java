package com.apj.tests.samples;

import com.apj.framework.waits.Waits;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiDemosInteractionsTest extends ApiDemosTest {

    @Test(priority = -1, description = "Session starts on the ApiDemos main activity")
    public void sessionStartsOnMainActivity() {
        assertThat(android().getCurrentPackage()).isEqualTo(PACKAGE);
        assertThat(android().currentActivity()).isEqualTo(".ApiDemos");
    }

    @Test(description = "Text typed into the search box is passed to the search dialog")
    public void sendKeysToSearch() {
        startActivity(".app.SearchInvoke");
        Waits waits = new Waits(driver());

        waits.visible(By.id("txt_query_prefill")).sendKeys("Hello world!");
        waits.clickable(By.id("btn_start_search")).click();

        assertThat(waits.visible(By.id("android:id/search_src_text")).getText()).isEqualTo("Hello world!");
    }

    @Test(description = "Alert dialog opens and can be dismissed")
    public void opensAndClosesAlert() {
        startActivity(".app.AlertDialogSamples");
        Waits waits = new Waits(driver());

        waits.clickable(By.id("two_buttons")).click();

        assertThat(waits.visible(By.id("android:id/alertTitle")).getText())
                .isEqualTo("Lorem ipsum dolor sit aie consectetur adipiscing\nPlloaso mako nuto siwuf cakso dodtos anr koop.");
        waits.clickable(By.id("android:id/button1")).click();
        assertThat(waits.invisible(By.id("android:id/alertTitle"))).isTrue();
    }
}
