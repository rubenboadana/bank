package com.iobuilders.user.domain;

import com.iobuilders.user.domain.dto.JwtToken;
import com.iobuilders.user.domain.dto.UserDTO;

public interface JwtGeneratorService {
    JwtToken generateToken(UserDTO user);
}
