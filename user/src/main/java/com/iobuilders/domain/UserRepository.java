package com.iobuilders.domain;

import com.iobuilders.domain.dto.User;
import com.iobuilders.domain.dto.UserID;

public interface UserRepository {

    UserID create(User user);

    void delete(String id);

    User update(String id, User user);

    User findById(String id);

    User findByUserNameAndPassword(String userName, String password);


}
