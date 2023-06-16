package com.iobuilders.domain.bus.event;

public interface EventBus {
    void publish(Event Event) throws InterruptedException;
}
