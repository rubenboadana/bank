package com.iobuilders.application.handler;

import com.iobuilders.domain.WalletService;
import com.iobuilders.domain.bus.query.QueryHandler;
import com.iobuilders.domain.dto.WalletOverview;
import com.iobuilders.domain.query.FindWalletTransactionsQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

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
    public WalletOverview handle(FindWalletTransactionsQuery query) throws InterruptedException, ExecutionException {
        log.info("FindWalletTransactionsQueryHandler:handle: Query received");
        Pageable pageable = PageRequest.of(query.getPage(), query.getSize());
        WalletOverview walletOverview = walletService.findTransactionsByWalletId(query.getRequestedBy(), query.getWalletId(), pageable);
        log.info("FindWalletTransactionsQueryHandler:handle: Query processed");

        return walletOverview;
    }
}
