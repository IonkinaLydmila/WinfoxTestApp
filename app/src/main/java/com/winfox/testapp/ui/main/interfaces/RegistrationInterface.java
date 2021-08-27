package com.winfox.testapp.ui.main.interfaces;

import com.winfox.testapp.base.BaseInterface;

public interface RegistrationInterface extends BaseInterface {

    void validName();
    void invalidName();

    void validEmail();
    void invalidEmail();

    void validPassword();
    void invalidPassword();

    void passwordsNotEquals();
    void passwordsEquals();

    void registrationBtnEnable();
    void registrationBtnDisable();

    void showAuthorizationScreen(String email);

}
