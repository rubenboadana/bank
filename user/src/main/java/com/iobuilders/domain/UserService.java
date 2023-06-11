package com.iobuilders.domain;

import com.iobuilders.domain.dto.JwtToken;
import com.iobuilders.domain.dto.LoginRequest;
import com.iobuilders.domain.dto.User;

public interface UserService {

    void create(User user);

    JwtToken login(LoginRequest loginRequest);

    void bindWallet(String username, String walletId);

}
