package com.iobuilders.application.handler;

import com.iobuilders.domain.UserService;
import com.iobuilders.domain.bus.command.CommandHandler;
import com.iobuilders.domain.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand> {

    private final UserService userservice;

    @Autowired
    public CreateUserCommandHandler(UserService userservice) {
        this.userservice = userservice;
    }

    @Override
    @org.axonframework.commandhandling.CommandHandler
    public void handle(CreateUserCommand command) {
        User user = new User(command.getId(), command.getUserName(), command.getPassword(), command.getName(), command.getSurname());
        userservice.create(user);
    }
}
