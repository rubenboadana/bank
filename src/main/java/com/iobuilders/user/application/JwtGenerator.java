package com.iobuilders.user.application;

import com.iobuilders.user.domain.dto.JwtToken;
import com.iobuilders.user.domain.dto.UserDTO;

public interface JwtGenerator {
    JwtToken generateToken(UserDTO user);
}
