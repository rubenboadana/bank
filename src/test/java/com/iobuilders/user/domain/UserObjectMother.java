package com.iobuilders.user.domain;

import com.iobuilders.user.domain.dto.User;

public class UserObjectMother {

    public static final Long DEFAULT_ID = 1L;
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
