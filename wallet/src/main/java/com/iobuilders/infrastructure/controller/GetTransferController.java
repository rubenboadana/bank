package com.iobuilders.infrastructure.controller;

import com.iobuilders.domain.bus.query.QueryBus;
import com.iobuilders.domain.dto.WalletOverview;
import com.iobuilders.domain.query.FindWalletTransactionsQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@Tag(name = "Wallets")
public class GetTransferController {

    private final QueryBus queryBus;

    @Autowired
    public GetTransferController(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @Operation(summary = "List wallet transactions and balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = WalletOverview.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid wallet information supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Wallet not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Wallet update failure",
                    content = @Content)})
    @GetMapping(value = "/wallets/{id}/transactions")
    public ResponseEntity<WalletOverview> getTransactions(@PathVariable(value = "id") String originWalletId) throws InterruptedException, ExecutionException {
        WalletOverview overview = (WalletOverview) queryBus.get(new FindWalletTransactionsQuery(originWalletId));

        return ResponseEntity.status(HttpStatus.OK).body(overview);
    }
}
