package test;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class TestBase {

    public static String KE_PREFIX = "712";

    public String generateRandomKePhoneNumber(){
        String num = RandomStringUtils.randomNumeric(6);
        String generatedNumber = KE_PREFIX.concat(num);
        return generatedNumber;
    }

    AndroidDriver<MobileElement> driver;

    @BeforeSuite
    public void setUpAppium() throws MalformedURLException {
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        File appSrc = new File("/Users/swekenyi/Documents/Tala/Projects/Android_Native/app/build/outputs/apk/app-KE_QA-release.apk");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.APP, appSrc);
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Device");

        driver = new AndroidDriver<MobileElement>(url, desiredCapabilities);
    }

    @AfterMethod
    public void tearDownAppium(){
        driver.closeApp();
    }
}
