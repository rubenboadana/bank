package com.iobuilders.domain;

import com.iobuilders.domain.dto.JwtToken;
import com.iobuilders.domain.dto.User;

public interface JwtService {
    JwtToken generateToken(User user);

    boolean validate(String token);

    String getUsername(String token);

}
