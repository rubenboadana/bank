package com.iobuilders.domain;

import com.iobuilders.domain.dto.JwtToken;
import com.iobuilders.domain.dto.RegisterRequest;

public interface JwtService {
    JwtToken generateToken(RegisterRequest registerRequest);

    boolean validate(String token);

    String getUsername(String token);

}
