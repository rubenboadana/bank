package com.iobuilders.domain;

import com.iobuilders.domain.dto.JwtToken;
import com.iobuilders.domain.dto.LoginRequest;
import com.iobuilders.domain.dto.RegisterRequest;
import com.iobuilders.domain.dto.WalletOwnerUsername;

public interface UserService {

    void create(RegisterRequest registerRequest);

    JwtToken login(LoginRequest loginRequest);

    void bindWallet(String username, String walletId);

    WalletOwnerUsername findByWalletId(String walletId);
}
