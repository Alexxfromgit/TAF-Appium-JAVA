package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testcases.BaseTest;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtility implements ITestListener {

    public void onStart(ITestContext tr) {

    }

    @Override
    public void onTestStart(ITestResult result) {

    }

    public void onFinish(ITestContext tr) {

    }

    public void onTestSuccess(ITestResult tr){
        captureScreenShot(tr, "pass");
    }

    public void onTestFailure(ITestResult tr) {
        captureScreenShot(tr, "fail");
    }

    public void onTestSkipped(ITestResult tr) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void captureScreenShot(ITestResult result, String status) {
        final WebDriver augmentedDriver = BaseTest.getScreenshotableWebDriver();
        String destDir = "";
        String passfailMethod = result.getMethod().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();

        File scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
        if (status.equalsIgnoreCase("fail")) {
            destDir = "screenshots/Failures";
        } else if (status.equalsIgnoreCase("pass")) {
            destDir = "screenshots/Success";
        }
        new File(destDir).mkdirs();

        String destFile = passfailMethod + " - " + dateFormat.format(new Date()) + ".png";

        try {
            FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
