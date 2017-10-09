package test;

import client.ApiClientException;
import client.ConfirmationCodeApiClient;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import pages.ConfirmationCodePage;
import pages.SetPinKePage;
import pages.SignUpKePage;
import pages.SplashScreenKePage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class TestBase {

    public static String KE_PREFIX = "712";
    public static String KE_COUNTRY_CODE = "254";
    AndroidDriver<MobileElement> driver;
    private String phoneNumber = generateRandomKePhoneNumber();
    private String numberWithCountryCode = generatedNumberWithCountryCode();
    private static String setPin = "1111";

    public String generateRandomKePhoneNumber(){
        String num = RandomStringUtils.randomNumeric(6);
        String generatedNumber = KE_PREFIX.concat(num);
        return generatedNumber;
    }

    public String generatedNumberWithCountryCode(){
        String numberWithCountryCode = KE_COUNTRY_CODE.concat(phoneNumber);
        return numberWithCountryCode;
    }

    @BeforeSuite
    public void setUpAppium() throws MalformedURLException {
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        File appDir = new File("src/main/resources");
        File appSrc = new File(appDir, "app-KE_QA-release.apk");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.APP, appSrc.getAbsolutePath());
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Device");

        driver = new AndroidDriver<MobileElement>(url, desiredCapabilities);
    }

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        setUpAppium();
    }

    @AfterMethod
    public void tearDownAppium(){
        driver.closeApp();
    }

    public void splashScreenToSignUpPage(){
        SplashScreenKePage splashScreenKePage = new SplashScreenKePage(driver);
        splashScreenKePage.clickSignUpButton();
    }

    public void signUpPageToConfirmationCodePage(){
        SignUpKePage signUpKePage = new SignUpKePage(driver);
        //signUpKePage.getNumberEdit().sendKeys(generateRandomKePhoneNumber());
        signUpKePage.getNumberEdit().sendKeys(phoneNumber);
        System.out.println("\nGenerated number is " + phoneNumber);

        signUpKePage.clickNextButton();
        System.out.println("\nThe passed phone number is:" + phoneNumber);
    }

    public void retrieveConfirmationCode() throws IOException, ApiClientException {
        ConfirmationCodePage confirmationCodePage = new ConfirmationCodePage(driver);
        MobileElement firstDigitEditText = confirmationCodePage.getFirstDigit();
        MobileElement secondDigitEditText = confirmationCodePage.getSecondDigit();
        MobileElement thirdDigitEditText = confirmationCodePage.getThirdDigit();
        MobileElement fourthDigitEditText = confirmationCodePage.getFourthDigit();

        ConfirmationCodeApiClient confirmationCodeApiClient = new ConfirmationCodeApiClient();
        //confirmationCodeApiClient.retrieveConfirmationCode(phoneNumber);
        String confirmationCode =  confirmationCodeApiClient.retrieveConfirmationCode(numberWithCountryCode);
        System.out.println("\n The number with the Country Code is: "+ numberWithCountryCode);
        System.out.println("\n The Confirmation Code is: "+ confirmationCode);

        if (null != confirmationCode) {
            int length = confirmationCode.length();
            if (length > 0) {
                firstDigitEditText.setValue(Character.toString(confirmationCode.charAt(0)));
            }
            if (length > 1) {
                secondDigitEditText.setValue(Character.toString(confirmationCode.charAt(1)));
            }
            if (length > 2) {
                thirdDigitEditText.setValue(Character.toString(confirmationCode.charAt(2)));
            }
            if (length > 3) {
                fourthDigitEditText.setValue(Character.toString(confirmationCode.charAt(3)));
            }
            driver.hideKeyboard();
        }
        confirmationCodePage.clickNextButton();
    }

    public void set_ConfirmFourDigitPin(){
        SetPinKePage setPinKePage = new SetPinKePage(driver);
        MobileElement firstPinDigitEditText = setPinKePage.getFirstDigitEdit();
        MobileElement secondPinDigitEditText = setPinKePage.getSecondDigitEdit();
        MobileElement thirdPinDigitEditText = setPinKePage.getThirdDigitEdit();
        MobileElement fourthPinDigitEditText = setPinKePage.getFourthDigitEdit();
        if (null != setPin) {
            int length = setPin.length();
            if (length > 0) {
                firstPinDigitEditText.setValue(Character.toString(setPin.charAt(0)));
            }
            if (length > 1) {
                secondPinDigitEditText.setValue(Character.toString(setPin.charAt(1)));
            }
            if (length > 2) {
                thirdPinDigitEditText.setValue(Character.toString(setPin.charAt(2)));
            }
            if (length > 3) {
                fourthPinDigitEditText.setValue(Character.toString(setPin.charAt(3)));
            }
            driver.hideKeyboard();
        }
        setPinKePage.clickNextButton();
    }
}
