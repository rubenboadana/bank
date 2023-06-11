package com.iobuilders.application;

import com.iobuilders.domain.dto.User;
import com.iobuilders.domain.dto.UserObjectMother;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceMock implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = UserObjectMother.basic();
        return UserDetailsImpl.build(user);
    }

}
