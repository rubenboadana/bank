package com.iobuilders.infrastructure.controller;

import com.iobuilders.domain.bus.command.CommandBus;
import com.iobuilders.domain.command.DepositMoneyInWalletCommand;
import com.iobuilders.domain.dto.Quantity;
import com.iobuilders.domain.dto.Wallet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@Tag(name = "Wallets")
public class UpdateWalletController {

    private final CommandBus commandBus;

    @Autowired
    public UpdateWalletController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Operation(summary = "Deposit money into the specified wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Wallet.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid deposit value",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Credentials not provided",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Wallet update failure",
                    content = @Content)})
    @PutMapping(value = "/wallets/{id}")
    public CompletableFuture<ResponseEntity<Void>> updateWallet(@PathVariable(value = "id") String walletId, @Valid @RequestBody Quantity quantity) throws InterruptedException {
        log.info("UpdateWalletController:updateWallet: PUT /wallets/" + walletId + " received");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        return commandBus.send(new DepositMoneyInWalletCommand(walletId, currentUserName, quantity.getValue()))
                .thenApply(response -> {
                    log.info("UpdateWalletController:updateWallet: PUT /wallets/" + walletId + " dispatched");
                    return ResponseEntity.status(HttpStatus.OK).build();
                });
    }
}
