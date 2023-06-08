package com.iobuilders.user.domain;

import com.iobuilders.user.domain.dto.JwtToken;
import com.iobuilders.user.domain.dto.LoginRequest;
import com.iobuilders.user.domain.dto.UserDTO;
import com.iobuilders.user.domain.dto.UserID;

public interface UserService {

    UserID create(UserDTO user);

    void delete(Long id);

    UserDTO update(Long id, UserDTO user);

    JwtToken login(LoginRequest loginRequest);
}
