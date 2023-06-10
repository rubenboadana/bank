package com.iobuilders.infrastructure.bus.event;


import com.iobuilders.domain.bus.event.Event;
import com.iobuilders.domain.bus.event.EventBus;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AxonEventBus implements EventBus {

    private final EventGateway eventGateway;

    @Autowired
    public AxonEventBus(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }

    @Override
    public void publish(Event event) {
        eventGateway.publish(event);
    }
}
