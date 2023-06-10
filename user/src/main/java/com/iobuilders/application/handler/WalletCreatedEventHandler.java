package com.iobuilders.application.handler;

import com.iobuilders.domain.UserService;
import com.iobuilders.domain.bus.event.EventHandler;
import com.iobuilders.domain.bus.event.WalletCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletCreatedEventHandler implements EventHandler<WalletCreatedEvent> {

    private final UserService userService;

    @Autowired
    public WalletCreatedEventHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    @org.axonframework.eventhandling.EventHandler
    public void on(WalletCreatedEvent event) {
        userService.bindWallet(event.getOwnerUsername(), event.getWalletId());
    }

}
