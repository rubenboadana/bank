package com.iobuilders.domain;

import com.iobuilders.domain.dto.JwtToken;
import com.iobuilders.domain.dto.LoginRequest;

public interface JwtService {
    JwtToken generateToken(LoginRequest loginRequest);

    boolean validate(String token);

    String getUsername(String token);

}
