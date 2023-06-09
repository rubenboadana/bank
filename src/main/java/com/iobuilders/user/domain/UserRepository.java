package com.iobuilders.user.domain;

import com.iobuilders.user.domain.dto.User;
import com.iobuilders.user.domain.dto.UserID;

public interface UserRepository {

    UserID create(User user);

    void delete(Long id);

    User update(Long id, User user);

    User findById(Long id);

    User findByUserNameAndPassword(String userName, String password);


}
