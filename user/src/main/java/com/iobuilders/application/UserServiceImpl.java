package com.iobuilders.application;

import com.iobuilders.domain.JwtService;
import com.iobuilders.domain.UserRepository;
import com.iobuilders.domain.UserService;
import com.iobuilders.domain.dto.JwtToken;
import com.iobuilders.domain.dto.LoginRequest;
import com.iobuilders.domain.dto.User;
import com.iobuilders.domain.dto.UserID;
import com.iobuilders.domain.exceptions.InvalidCredentialsException;
import com.iobuilders.domain.exceptions.UserAlreadyExistsException;
import com.iobuilders.domain.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository repository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserID create(User user) {
        checkUserNotAlreadyExist(user);
        return repository.create(user);
    }

    private void checkUserNotAlreadyExist(User user) {
        repository.findByUserName(user.userName()).ifPresent(userFound -> {
            throw new UserAlreadyExistsException(userFound.userName());
        });
    }

    @Override
    public JwtToken login(LoginRequest loginRequest) {
        User user = repository.findByUserName(loginRequest.username())
                .orElseThrow(() -> new UserNotFoundException(loginRequest.username()));

        if (!passwordEncoder.matches(loginRequest.password(), user.password())) {
            throw new InvalidCredentialsException(loginRequest.username());
        }

        return jwtService.generateToken(user);
    }

    @Override
    public void bindWallet(String userName, String walletId) {
        User user = repository.findByUserName(userName).orElseThrow(() -> new UserNotFoundException(userName));
        repository.bindWallet(user, walletId);
    }


}
