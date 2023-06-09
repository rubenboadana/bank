package com.iobuilders.domain;

import com.iobuilders.domain.dto.User;
import com.iobuilders.domain.dto.UserID;

public interface UserRepository {

    UserID create(User user);

    void delete(Long id);

    User update(Long id, User user);

    User findById(Long id);

    User findByUserNameAndPassword(String userName, String password);


}
