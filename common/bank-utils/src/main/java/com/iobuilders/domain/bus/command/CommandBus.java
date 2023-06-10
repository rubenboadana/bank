package com.iobuilders.domain.bus.command;

import java.util.concurrent.CompletableFuture;

public interface CommandBus {
    CompletableFuture<Void> send(Command command) throws InterruptedException;
}
