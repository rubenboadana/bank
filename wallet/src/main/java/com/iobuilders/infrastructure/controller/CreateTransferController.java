package com.iobuilders.infrastructure.controller;

import com.iobuilders.domain.bus.command.CommandBus;
import com.iobuilders.domain.command.TransferMoneyCommand;
import com.iobuilders.domain.dto.Wallet;
import com.iobuilders.domain.dto.WalletTransaction;
import com.iobuilders.domain.enums.TransactionTypes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@Tag(name = "Wallets")
public class CreateTransferController {

    private final CommandBus commandBus;

    @Autowired
    public CreateTransferController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Operation(summary = "Transfer money from the wallet to the specified destiny wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Wallet.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid wallet information supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Wallet not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Wallet update failure",
                    content = @Content)})
    @PostMapping(value = "/wallets/{id}/transactions")
    public CompletableFuture<ResponseEntity<Void>> doMoneyTransaction(@PathVariable(value = "id") String originWalletId, @Valid @RequestBody WalletTransaction transaction) throws InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return commandBus.send(new TransferMoneyCommand(currentUserName, originWalletId, transaction.getDestinyWalletId(), TransactionTypes.TRANSFERENCE, transaction.getQuantity()))
                .thenApply(response -> ResponseEntity.status(HttpStatus.OK).build());
    }
}
