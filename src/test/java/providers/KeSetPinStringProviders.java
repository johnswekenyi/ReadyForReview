package providers;

public class KeSetPinStringProviders implements SetPinProviders {
    @Override
    public String getTitleTextString() {
        return "SIGN UP";
    }

    @Override
    public String getHeaderTextString() {
        return "Set a 4 digit PIN";
    }

    @Override
    public String getDetailsTextString() {
        return "Please choose a PIN you will remember. You will need it every time you open Tala.";
    }

    @Override
    public String getBackButtonString() {
        return "BACK";
    }

    @Override
    public String getNextButtonString() {
        return "NEXT";
    }

    @Override
    public String getSwitchMaskTextString() {
        return "SHOW";
    }
}
