package com.iobuilders.infrastructure.controller;

import com.iobuilders.domain.bus.command.CommandBus;
import com.iobuilders.domain.command.CreateWalletCommand;
import com.iobuilders.domain.dto.Wallet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@Tag(name = "Wallets")
public class CreateWalletController {
    private final CommandBus commandBus;

    @Autowired
    public CreateWalletController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Operation(summary = "Create a new wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Wallet created",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid wallet information supplied",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Wallet creation failure",
                    content = @Content)})
    @PostMapping(value = "/wallets")
    public CompletableFuture<ResponseEntity<Void>> createWallet(@Valid @RequestBody Wallet wallet) throws InterruptedException {
        log.info("CreateWalletController:createWallet: POST /wallets received");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return commandBus.send(new CreateWalletCommand(wallet.getId(), currentUserName, wallet.getQuantity()))
                .thenApply(response -> {
                    log.info("CreateWalletController:createWallet: POST /wallets dispatched");
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                });
    }
}
