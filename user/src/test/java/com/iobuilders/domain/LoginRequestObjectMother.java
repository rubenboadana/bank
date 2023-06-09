package com.iobuilders.domain;

import com.iobuilders.domain.dto.LoginRequest;

public class LoginRequestObjectMother {
    public static LoginRequest basic() {
        return new LoginRequest(UserObjectMother.DEFAULT_USERNAME, UserObjectMother.DEFAULT_PASSWORD);
    }

}
