package com.iobuilders.user.domain;

import com.iobuilders.user.domain.dto.JwtToken;
import com.iobuilders.user.domain.dto.LoginRequest;
import com.iobuilders.user.domain.dto.User;
import com.iobuilders.user.domain.dto.UserID;

public interface UserService {

    UserID create(User user);

    void delete(Long id);

    User update(Long id, User user);

    JwtToken login(LoginRequest loginRequest);
}
