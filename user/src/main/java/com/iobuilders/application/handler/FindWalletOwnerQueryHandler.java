package com.iobuilders.application.handler;

import com.iobuilders.domain.UserService;
import com.iobuilders.domain.bus.query.FindWalletOwnerQuery;
import com.iobuilders.domain.bus.query.QueryHandler;
import com.iobuilders.domain.dto.WalletOwnerUsername;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FindWalletOwnerQueryHandler implements QueryHandler<FindWalletOwnerQuery, WalletOwnerUsername> {

    private final UserService userService;

    @Autowired
    public FindWalletOwnerQueryHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    @org.axonframework.queryhandling.QueryHandler
    public WalletOwnerUsername handle(FindWalletOwnerQuery query) throws InterruptedException {
        log.info("FindWalletOwnerQueryHandler:handle: Query received");
        WalletOwnerUsername owner = userService.findByWalletId(query.getWalletId());
        log.info("FindWalletOwnerQueryHandler:handle: Query processed");

        return owner;
    }
}
