package com.winfox.testapp.ui.authorization.presenters;

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
import com.winfox.testapp.ui.authorization.interfaces.AuthorizationInterface;
import com.winfox.testapp.ui.authorization.models.AuthUserResponseModel;
import com.winfox.testapp.utils.UserDataValidation;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthorizationPresenter extends BasePresenter implements APIDataCallback {

    private AuthorizationInterface authorizationInterface;
    private KeyboardInterface keyboardInterface;

    private String email = "";
    private String password = "";

    public AuthorizationPresenter(AuthorizationInterface authorizationInterface, KeyboardInterface keyboardInterface) {
        this.authorizationInterface = authorizationInterface;
        this.keyboardInterface = keyboardInterface;
    }

    public void onEmailChanged(String email) {
        this.email = email;
        checkEnterData();
    }

    public void onPasswordChanged(String password) {
        this.password = password;
        checkEnterData();
    }

    public void checkEnterData() {

        if(checkEmail() && (!password.isEmpty())) authorizationInterface.authorizationBtnEnable();
        else authorizationInterface.authorizationBtnDisable();

    }

    private Boolean checkEmail() {
        return (UserDataValidation.isEmail(email));
    }

    public void authorization() {

        keyboardInterface.hideKeyboard();
        authorizationInterface.showProgress();

        try {

            RequestModel model = new RequestModel(API.authorization(), RequestType.POST, RequestModel.createRequestBody(authorizationJSONObject()));
            new AppRequest(model, this).init();

        } catch (JSONException e) {

            e.printStackTrace();
            authorizationInterface.showMessage(e.getMessage());

        }

    }

    private JSONObject authorizationJSONObject() throws JSONException {

        return new JSONObject()
                .put("email", email)
                .put("password", password);

    }

    @Override
    public void serverResponse(Boolean success, String result) {

        Log.d("TEST", "Authorization result: " + result);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {

            @Override
            public void run() {

                authorizationInterface.hideProgress();

                if(success) {

                    try {

                        AuthUserResponseModel model = AuthUserResponseModel.mapping(new JSONObject(result));
                        // ...

                    } catch (JSONException e) { e.printStackTrace(); }

                    authorizationInterface.showProfileScreen();

                } else authorizationInterface.showMessage(result);

            }
        });
    }

}
