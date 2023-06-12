package com.iobuilders.domain.dto;

public class RegisterRequestObjectMother {

    public static final String DEFAULT_ID = "26929514-237c-11ed-861d-0242ac120002";
    public static final String ALIEN_DEFAULT_ID = "26929514-237c-11ed-861d-0242ac120004";
    public static final String DEFAULT_USERNAME = "rubenboada";
    public static final String ALIEN_USERNAME = "rubenboada22222";
    public static final String DEFAULT_PASSWORD = "rubenboada";
    public static final String DEFAULT_USER_NAME = "Ruben";
    public static final String DEFAULT_USER_SURNAME = "Boada";

    public static RegisterRequest basic() {
        return new RegisterRequest(DEFAULT_ID, DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_USER_NAME, DEFAULT_USER_SURNAME);
    }

    public static RegisterRequest external() {
        return new RegisterRequest(ALIEN_DEFAULT_ID, ALIEN_USERNAME, DEFAULT_PASSWORD, DEFAULT_USER_NAME, DEFAULT_USER_SURNAME);
    }

}
