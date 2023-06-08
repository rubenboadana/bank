package com.iobuilders.user.domain;

import com.iobuilders.user.domain.dto.UserDTO;

public class UserObjectMother {

    public static final String DEFAULT_USER_NAME = "Ruben";
    public static final String DEFAULT_USER_SURNAME = "Boada";

    public static UserDTO basic() {
        return UserDTO.builder()
                .name(DEFAULT_USER_NAME)
                .surname(DEFAULT_USER_SURNAME)
                .build();
    }


}
