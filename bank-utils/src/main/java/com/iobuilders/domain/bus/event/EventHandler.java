package com.iobuilders.domain.bus.event;


public interface EventHandler<T extends Event> {
    void on(T event);
}
