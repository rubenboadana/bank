package com.iobuilders.user.domain;

import com.iobuilders.user.domain.dto.JwtToken;
import com.iobuilders.user.domain.dto.User;

public interface JwtGeneratorService {
    JwtToken generateToken(User user);
}
