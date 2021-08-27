package com.winfox.testapp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataValidation {

    public static Boolean isFullName(String name) {

        Pattern pattern = Pattern.compile(AppPatterns.USER_FULL_NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();

    }

    public static Boolean isEmail(String email) {

        email = email.toLowerCase();
        return Pattern.compile(AppPatterns.EMAIL_PATTERN)
                .matcher(email)
                .matches();

    }

}
