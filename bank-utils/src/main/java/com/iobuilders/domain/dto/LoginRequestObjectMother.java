package com.iobuilders.domain.dto;

public class LoginRequestObjectMother {

    public static final String DEFAULT_USERNAME = "rubenboada";

    public static LoginRequest withPassword(String password) {
        return new LoginRequest(DEFAULT_USERNAME, password);
    }

}
