package com.iobuilders.domain.bus.command;

public interface CommandBus {
    void send(Command command);
}
