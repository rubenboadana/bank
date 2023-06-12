package com.iobuilders.application;

import com.iobuilders.domain.JwtService;
import com.iobuilders.domain.UserRepository;
import com.iobuilders.domain.UserService;
import com.iobuilders.domain.dto.JwtToken;
import com.iobuilders.domain.dto.LoginRequest;
import com.iobuilders.domain.dto.RegisterRequest;
import com.iobuilders.domain.dto.WalletOwnerUsername;
import com.iobuilders.domain.exceptions.InvalidCredentialsException;
import com.iobuilders.domain.exceptions.UserAlreadyExistsException;
import com.iobuilders.domain.exceptions.UserNotFoundException;
import com.iobuilders.domain.exceptions.WalletNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
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
    public void create(RegisterRequest registerRequest) {
        log.info("UserServiceImpl:create: Starting to create the new user " + registerRequest);
        checkUserNotAlreadyExist(registerRequest);

        repository.create(registerRequest);
        log.info("UserServiceImpl:create: User [" + registerRequest.userName() + "] successfully created");

    }

    private void checkUserNotAlreadyExist(RegisterRequest registerRequest) {
        repository.findByUserName(registerRequest.userName()).ifPresent(userFound -> {
            log.error("UserServiceImpl:create: Username already exists: " + registerRequest.userName());
            throw new UserAlreadyExistsException(userFound.userName());
        });
    }

    @Override
    public JwtToken login(LoginRequest loginRequest) {
        RegisterRequest registerRequest = repository.findByUserName(loginRequest.username())
                .orElseThrow(() -> {
                    log.error("UserServiceImpl:login: User not found: " + loginRequest.username());
                    return new UserNotFoundException(loginRequest.username());
                });

        if (!passwordEncoder.matches(loginRequest.password(), registerRequest.password())) {
            log.error("UserServiceImpl:login: Credentials are invalid for the user: " + registerRequest.userName());
            throw new InvalidCredentialsException(loginRequest.username());
        }

        return jwtService.generateToken(registerRequest);
    }

    @Override
    public void bindWallet(String userName, String walletId) {
        log.info("UserServiceImpl:bindWallet: Starting to bind the wallet [" + walletId + "] to the user [" + userName + "]");
        RegisterRequest registerRequest = repository.findByUserName(userName).orElseThrow(() -> new UserNotFoundException(userName));
        repository.bindWallet(registerRequest, walletId);
        log.info("UserServiceImpl:bindWallet: The wallet [" + walletId + "] was successfully bind to the user [" + userName + "]");
    }

    @Override
    public WalletOwnerUsername findByWalletId(String walletId) {
        RegisterRequest registerRequest = repository.findByWalletId(walletId).orElseThrow(() -> new WalletNotFoundException(walletId));
        return new WalletOwnerUsername(registerRequest.userName());
    }


}
