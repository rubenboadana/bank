package com.iobuilders.domain;

import com.iobuilders.domain.dto.RegisterRequest;
import com.iobuilders.domain.dto.UserID;

import java.util.Optional;

public interface UserRepository {

    UserID create(RegisterRequest registerRequest);

    Optional<RegisterRequest> findByUserName(String userName);

    Optional<RegisterRequest> findByWalletId(String walletId);

    void bindWallet(RegisterRequest registerRequest, String walletId);
}
