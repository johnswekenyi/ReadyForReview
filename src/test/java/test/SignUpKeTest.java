package test;

import org.testng.annotations.Test;
import pages.SignUpKePage;
import pages.SplashScreenKePage;
import providers.KeSignUpPageStringProviders;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertTrue;

public class SignUpKeTest extends TestBase {
    KeSignUpPageStringProviders keSignUpPageStringProviders = new KeSignUpPageStringProviders();

    @Test
    public void testSignUp_fromSplashScreen(){
        SignUpKePage signUpKePage = new SignUpKePage(driver);
        SplashScreenKePage splashScreenKePage = new SplashScreenKePage(driver);
        splashScreenKePage.clickSignUpButton();
        assertThat(signUpKePage.getTitleText().getText(), is(keSignUpPageStringProviders.getTitleString()));
        assertThat(signUpKePage.getDescriptionText().getText(), is(keSignUpPageStringProviders.getDescriptionTestString()));
        assertThat(signUpKePage.getSecondaryDescriptionText().getText(),
                is(keSignUpPageStringProviders.getSecondaryDescriptionString()));
        assertThat(signUpKePage.getCodeEdit().getText(), is(keSignUpPageStringProviders.getCodeString()));
        assertTrue(signUpKePage.getNumberEdit().isDisplayed());
        signUpKePage.getNumberEdit().sendKeys(generateRandomKePhoneNumber());
        System.out.println("\nGenerated number is " + generateRandomKePhoneNumber());
        signUpKePage.clickNextButton();

        assertThat(signUpKePage.getBackButon().getText(), is(keSignUpPageStringProviders.getBackButonString()));
        assertThat(signUpKePage.getNextButon().getText(),is(keSignUpPageStringProviders.getNextButonString()));
    }
}
