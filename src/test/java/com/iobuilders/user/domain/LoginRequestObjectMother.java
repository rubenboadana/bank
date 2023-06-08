package com.iobuilders.user.domain;

import com.iobuilders.user.domain.dto.LoginRequest;

public class LoginRequestObjectMother {
    public static LoginRequest basic() {
        return new LoginRequest(UserObjectMother.DEFAULT_USERNAME, UserObjectMother.DEFAULT_PASSWORD);
    }

}
