package com.winfox.testapp.ui.main.models;

import org.json.JSONObject;

import java.io.Serializable;

public class RegisterUserResponseModel implements Serializable {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    public String getEmail() {
        return (email != null) ? email : "";
    }

    public static RegisterUserResponseModel mapping(JSONObject data) {

        RegisterUserResponseModel item = new RegisterUserResponseModel();
        if(data.opt("id") instanceof String) item.id = data.optString("id");
        if(data.opt("firstname") instanceof String) item.firstname = data.optString("firstname");
        if(data.opt("lastname") instanceof String) item.lastname = data.optString("lastname");
        if(data.opt("email") instanceof String) item.email = data.optString("email");
        if(data.opt("password") instanceof String) item.password = data.optString("password");
        return item;

    }

}
