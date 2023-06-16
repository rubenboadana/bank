package com.iobuilders.domain;

import com.iobuilders.domain.dto.UserID;

import java.util.Optional;

public interface UserRepository {

    UserID create(User user);

    Optional<User> findByUserName(String userName);

    Optional<User> findByWalletId(String walletId);

    void bindWallet(User user, String walletId);
}
