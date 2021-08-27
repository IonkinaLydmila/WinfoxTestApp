package com.winfox.testapp.ui.authorization.models;

import org.json.JSONObject;

import java.io.Serializable;

public class AuthUserResponseModel implements Serializable {

    private String id;
    private String email;
    private String password;

    public static AuthUserResponseModel mapping(JSONObject data) {

        AuthUserResponseModel item = new AuthUserResponseModel();
        if(data.opt("id") instanceof String) item.id = data.optString("id");
        if(data.opt("email") instanceof String) item.email = data.optString("email");
        if(data.opt("password") instanceof String) item.password = data.optString("password");
        return item;

    }

}
