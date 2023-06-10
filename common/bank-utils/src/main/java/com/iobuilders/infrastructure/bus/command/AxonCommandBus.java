package com.iobuilders.infrastructure.bus.command;

import com.iobuilders.domain.bus.command.Command;
import com.iobuilders.domain.bus.command.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AxonCommandBus implements CommandBus {

    private final CommandGateway commandGateway;

    @Autowired
    public AxonCommandBus(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<Void> send(Command command) {
        return commandGateway.send(command);
    }
}
