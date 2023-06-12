package com.iobuilders.domain.bus.command;

import java.util.concurrent.ExecutionException;

public interface CommandHandler<T extends Command> {
    void handle(T command) throws InterruptedException, ExecutionException;
}
