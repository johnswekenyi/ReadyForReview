package providers;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class KeConfirmationCodeStringProviders implements ConfirmationCodeProviders{

    public String getTitleTextString() {
        return "SIGN UP";
    }

    public String getHeaderTextString() {
        return "Enter confirmation code";
    }

    public String getDetailsTextString() {
        return "Code sent via SMS to";
    }

    public String getWrongPhoneNumberString() {
        return "Wrong phone number?";
    }

    public String getConfirmationCodeDetailsTextString() {
        return "You should receive your code in less than 2 minutes.";
    }

    public String getResendCodeCountdownTextString() {
        return "Resend code in";
    }

    public String getBackButtonString() {
        return "BACK";
    }

    public String getNextButtonString() {
        return "NEXT";
    }

}
