package com.iobuilders.domain.dto;

public class LoginRequestObjectMother {

    public static LoginRequest withUsernameAndPassword(String username, String password) {
        return new LoginRequest(username, password);
    }


}
