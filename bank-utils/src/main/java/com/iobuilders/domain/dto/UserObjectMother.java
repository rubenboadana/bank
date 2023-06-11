package com.iobuilders.domain.dto;

public class UserObjectMother {

    public static final String DEFAULT_ID = "26929514-237c-11ed-861d-0242ac120002";
    public static final String DEFAULT_USERNAME = "rubenboada";
    public static final String DEFAULT_PASSWORD = "rubenboada";
    public static final String DEFAULT_USER_NAME = "Ruben";
    public static final String DEFAULT_USER_SURNAME = "Boada";

    public static User basic() {
        return new User(DEFAULT_ID, DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_USER_NAME, DEFAULT_USER_SURNAME);
    }

    public static User withName(String name) {
        return new User(DEFAULT_ID, DEFAULT_USERNAME, DEFAULT_PASSWORD, name, DEFAULT_USER_SURNAME);
    }

}
