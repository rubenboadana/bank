package com.iobuilders.domain;

import com.iobuilders.domain.dto.User;
import com.iobuilders.domain.dto.UserID;

import java.util.Optional;

public interface UserRepository {

    UserID create(User user);

    Optional<User> findByUserNameAndPassword(String userName, String password);

    Optional<User> findByUserName(String userName);
}
