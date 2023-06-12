package com.iobuilders.application;

import com.iobuilders.domain.dto.RegisterRequest;
import com.iobuilders.domain.dto.RegisterRequestObjectMother;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceMock implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        RegisterRequest registerRequest = RegisterRequestObjectMother.basic();
        return UserDetailsImpl.build(registerRequest);
    }

}
