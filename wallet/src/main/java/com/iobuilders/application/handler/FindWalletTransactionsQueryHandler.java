package com.iobuilders.application.handler;

import com.iobuilders.domain.WalletService;
import com.iobuilders.domain.bus.query.QueryHandler;
import com.iobuilders.domain.dto.WalletOverview;
import com.iobuilders.domain.query.FindWalletTransactionsQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FindWalletTransactionsQueryHandler implements QueryHandler<FindWalletTransactionsQuery, WalletOverview> {

    private final WalletService walletService;

    @Autowired
    public FindWalletTransactionsQueryHandler(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    @org.axonframework.queryhandling.QueryHandler
    public WalletOverview handle(FindWalletTransactionsQuery query) throws InterruptedException {
        log.info("FindWalletTransactionsQueryHandler:handle: Query received");
        WalletOverview walletOverview = walletService.findTransactionsByWalletId(query.getWalletId());
        log.info("FindWalletTransactionsQueryHandler:handle: Query processed");

        return walletOverview;
    }
}
