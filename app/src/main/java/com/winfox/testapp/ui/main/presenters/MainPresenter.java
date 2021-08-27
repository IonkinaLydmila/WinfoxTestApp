package com.winfox.testapp.ui.main.presenters;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.winfox.testapp.api.API;
import com.winfox.testapp.api.APIDataCallback;
import com.winfox.testapp.api.AppRequest;
import com.winfox.testapp.api.RequestModel;
import com.winfox.testapp.api.RequestType;
import com.winfox.testapp.base.BasePresenter;
import com.winfox.testapp.base.KeyboardInterface;
import com.winfox.testapp.ui.main.interfaces.RegistrationInterface;
import com.winfox.testapp.ui.main.models.RegisterUserResponseModel;
import com.winfox.testapp.utils.UserDataValidation;

import org.json.JSONException;
import org.json.JSONObject;

public class MainPresenter extends BasePresenter implements APIDataCallback {

    private RegistrationInterface registrationInterface;
    private KeyboardInterface keyboardInterface;

    private String name = "";
    private String email = "";
    private String firstPassword = "";
    private String secondPassword = "";

    public MainPresenter(RegistrationInterface registrationInterface, KeyboardInterface keyboardInterface) {
        this.registrationInterface = registrationInterface;
        this.keyboardInterface = keyboardInterface;
    }

    public void onNameChanged(String name) {
        this.name = name;
        checkEnterData();
    }

    public void onEmailChanged(String email) {
        this.email = email;
        checkEnterData();
    }

    public void onFirstPasswordChanged(String firstPassword) {
        this.firstPassword = firstPassword;
        checkEnterData();
    }

    public void onSecondPasswordChanged(String secondPassword) {
        this.secondPassword = secondPassword;
        checkEnterData();
    }

    public void checkEnterData() {

        if(checkName() & checkEmail() & checkFirstPassword() & isPasswordsEquals()) registrationInterface.registrationBtnEnable();
        else registrationInterface.registrationBtnDisable();

    }

    private Boolean checkName() {

        if(UserDataValidation.isFullName(name)) {

            registrationInterface.validName();
            return true;

        } else {

            if(!name.isEmpty()) registrationInterface.invalidName();
            return false;
        }

    }

    private Boolean checkEmail() {

        if(UserDataValidation.isEmail(email)) {

            registrationInterface.validEmail();
            return true;

        } else {

            if(!email.isEmpty()) registrationInterface.invalidEmail();
            return false;
        }
    }

    private Boolean checkFirstPassword() {

        if(!firstPassword.isEmpty()) {

            registrationInterface.validPassword();
            return true;

        } else {

            //registrationInterface.invalidPassword();
            return false;
        }

    }

    private Boolean isPasswordsEquals() {

        if(!firstPassword.isEmpty() && firstPassword.equals(secondPassword)) {

            registrationInterface.passwordsEquals();
            return true;

        } else {

            if(!firstPassword.isEmpty() && !secondPassword.isEmpty()) registrationInterface.passwordsNotEquals();
            return false;
        }

    }

    public void registration() {

        keyboardInterface.hideKeyboard();
        registrationInterface.showProgress();

        try {

            String[] nameParts = name.split(" ");
            if(nameParts.length < 3) throw new JSONException("Name not valid");

            RequestModel model = new RequestModel(API.registration(), RequestType.POST, RequestModel.createRequestBody(registrationJSONObject()));
            new AppRequest(model, this).init();

        } catch (JSONException e) {

            e.printStackTrace();
            registrationInterface.showMessage(e.getMessage());

        }

    }

    private JSONObject registrationJSONObject() throws JSONException {

        String[] nameParts = name.split(" ");
        if(nameParts.length < 3) throw new JSONException("Name not valid");

        return new JSONObject()
                    .put("email", email)
                    .put("firstname", nameParts[1])
                    .put("lastname", nameParts[0])
                    .put("password", firstPassword);

    }

    @Override
    public void serverResponse(Boolean success, String result) {

        Log.d("TEST", "Registration result: " + result);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {

                registrationInterface.hideProgress();

                if(success) {

                    try {

                        RegisterUserResponseModel model = RegisterUserResponseModel.mapping(new JSONObject(result));
                        // ...

                    } catch (JSONException e) { e.printStackTrace(); }

                    registrationInterface.showAuthorizationScreen(email);

                } else registrationInterface.showMessage(result);

            }
        });
    }

}
