package com.winfox.testapp.api;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestModel{

    private static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public String url;
    public RequestType type;
    public RequestBody body;

    public RequestModel(String url, RequestType type, RequestBody body) {

        this.url = url;
        this.type = type;
        this.body = body;

    }

    public static RequestBody createRequestBody(JSONObject json) {
        return RequestBody.create(JSON, json.toString());
    }

}
