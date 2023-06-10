package com.iobuilders.application.handler;

import com.iobuilders.domain.UserService;
import com.iobuilders.domain.bus.command.CommandHandler;
import com.iobuilders.domain.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand> {

    private final UserService userservice;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CreateUserCommandHandler(UserService userservice, PasswordEncoder passwordEncode) {
        this.userservice = userservice;
        this.passwordEncoder = passwordEncode;
    }

    @Override
    @org.axonframework.commandhandling.CommandHandler
    public void handle(CreateUserCommand command) {
        User user = new User(command.getId(), command.getUserName(), passwordEncoder.encode(command.getPassword()), command.getName(), command.getSurname());
        userservice.create(user);
    }
}
