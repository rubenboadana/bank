package com.iobuilders.domain;

import com.iobuilders.domain.dto.LoginRequest;

public class LoginRequestObjectMother {
    public static LoginRequest withPassword(String password) {
        return new LoginRequest(UserObjectMother.DEFAULT_USERNAME, password);
    }

}
