package com.iobuilders.application;

import com.iobuilders.domain.JwtGeneratorService;
import com.iobuilders.domain.UserRepository;
import com.iobuilders.domain.UserService;
import com.iobuilders.domain.dto.JwtToken;
import com.iobuilders.domain.dto.LoginRequest;
import com.iobuilders.domain.dto.User;
import com.iobuilders.domain.dto.UserID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final JwtGeneratorService jwtGeneratorService;

    @Autowired
    public UserServiceImpl(UserRepository repository, JwtGeneratorService jwtGeneratorService) {
        this.repository = repository;
        this.jwtGeneratorService = jwtGeneratorService;
    }

    @Override
    public UserID create(User user) {
        return repository.create(user);
    }

    @Override
    public void delete(Long id) {
        checkIfExists(id);
        repository.delete(id);
    }

    @Override
    public User update(Long id, User user) {
        return repository.update(id, user);
    }

    @Override
    public JwtToken login(LoginRequest loginRequest) {
        User user = repository.findByUserNameAndPassword(loginRequest.username(), loginRequest.password());
        return jwtGeneratorService.generateToken(user);
    }

    private void checkIfExists(Long id) {
        repository.findById(id);
    }
}
