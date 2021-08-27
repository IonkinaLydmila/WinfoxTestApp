package com.winfox.testapp.api;

import com.winfox.testapp.BuildConfig;

public class API {

    private static final String MAIN_URL = BuildConfig.API;

    public static String registration() {
        return MAIN_URL + "/registerUser";
    }

    public static String authorization() {
        return MAIN_URL + "/checkLogin";
    }

}
