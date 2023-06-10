package com.iobuilders.application;

import com.iobuilders.domain.UserRepository;
import com.iobuilders.domain.dto.User;
import com.iobuilders.domain.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return UserDetailsImpl.build(user);
    }

}
