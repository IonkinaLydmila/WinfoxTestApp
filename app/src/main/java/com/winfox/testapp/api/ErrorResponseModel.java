package com.winfox.testapp.api;

import org.json.JSONObject;

public class ErrorResponseModel {

    private String error;

    public String getError() {
        return (error != null) ? error : "";
    }

    public static ErrorResponseModel mapping(JSONObject data) {

        ErrorResponseModel item = new ErrorResponseModel();
        if(data.opt("message") instanceof String) item.error = data.optString("error");
        return item;

    }

}
