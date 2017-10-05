package test;

import io.appium.java_client.AppiumDriver;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import pages.SplashScreenKePage;
import providers.KeSplashScreenStringProviders;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertTrue;

public class SplashScreenKeTest extends TestBase{
    KeSplashScreenStringProviders keSplashScreenStringProviders = new KeSplashScreenStringProviders();

    @Test
    public void testSplashScreen() {
        SplashScreenKePage splashScreenKePage = new SplashScreenKePage(driver);
        assertThat(splashScreenKePage.getTitleText().getText(), is(keSplashScreenStringProviders.getTitleString()));
        System.out.println("\nThe title is : " + splashScreenKePage.getTitleText().getText());
        assertTrue(splashScreenKePage.getLogoImage().isDisplayed());
        assertThat(splashScreenKePage.getDescriptionText().getText(), is(keSplashScreenStringProviders.getDescriptionTestString()));
        assertThat(splashScreenKePage.getSignUpButton().getText(), is(keSplashScreenStringProviders.getSignUpButtonString()));
        assertThat(splashScreenKePage.getSignInButton().getText(), is(keSplashScreenStringProviders.getSignInbuttonString()));
    }

}
