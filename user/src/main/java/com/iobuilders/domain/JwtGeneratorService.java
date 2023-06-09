package com.iobuilders.domain;

import com.iobuilders.domain.dto.JwtToken;
import com.iobuilders.domain.dto.User;

public interface JwtGeneratorService {
    JwtToken generateToken(User user);
}
