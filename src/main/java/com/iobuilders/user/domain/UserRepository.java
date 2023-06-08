package com.iobuilders.user.domain;

import com.iobuilders.user.domain.dto.UserDTO;
import com.iobuilders.user.domain.dto.UserID;

public interface UserRepository {

    UserID create(UserDTO user);

    void delete(Long id);

    UserDTO update(Long id, UserDTO user);

    UserDTO findById(Long id);

}
