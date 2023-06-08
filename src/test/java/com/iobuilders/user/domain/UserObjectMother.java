package com.iobuilders.user.domain;

import com.iobuilders.user.domain.dto.UserDTO;

public class UserObjectMother {

    public static final String DEFAULT_USERNAME = "rubenboada";
    public static final String DEFAULT_PASSWORD = "rubenboada";
    public static final String DEFAULT_USER_NAME = "Ruben";
    public static final String DEFAULT_USER_SURNAME = "Boada";

    public static UserDTO basic() {
        return UserDTO.builder()
                .userName(DEFAULT_USERNAME)
                .password(DEFAULT_PASSWORD)
                .name(DEFAULT_USER_NAME)
                .surname(DEFAULT_USER_SURNAME)
                .build();
    }


}
