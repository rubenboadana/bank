package com.iobuilders.user.application;

import com.iobuilders.user.domain.JwtGeneratorService;
import com.iobuilders.user.domain.UserRepository;
import com.iobuilders.user.domain.UserService;
import com.iobuilders.user.domain.dto.JwtToken;
import com.iobuilders.user.domain.dto.LoginRequest;
import com.iobuilders.user.domain.dto.UserDTO;
import com.iobuilders.user.domain.dto.UserID;
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
    public UserID create(UserDTO user) {
        return repository.create(user);
    }

    @Override
    public void delete(Long id) {
        checkIfExists(id);
        repository.delete(id);
    }

    @Override
    public UserDTO update(Long id, UserDTO user) {
        return repository.update(id, user);
    }

    @Override
    public JwtToken login(LoginRequest loginRequest) {
        UserDTO user = repository.findByUserNameAndPassword(loginRequest.username(), loginRequest.password());
        return jwtGeneratorService.generateToken(user);
    }

    private void checkIfExists(Long id) {
        repository.findById(id);
    }
}
