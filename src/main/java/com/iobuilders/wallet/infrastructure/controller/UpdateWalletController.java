package com.iobuilders.wallet.infrastructure.controller;

import com.iobuilders.wallet.domain.WalletService;
import com.iobuilders.wallet.domain.dto.Wallet;
import com.iobuilders.wallet.domain.dto.WalletID;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Wallets")
public class UpdateWalletController {
    private final WalletService walletService;

    @Autowired
    public UpdateWalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @Operation(summary = "Update the wallet information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = WalletID.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid wallet information supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Wallet not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Wallet update failure",
                    content = @Content)})
    @PutMapping(value = "/wallets/{id}")
    public ResponseEntity updateWallet(@PathVariable(value = "id") Long id, @Valid @RequestBody Wallet wallet) {
        Wallet updatedWallet = walletService.update(id, wallet);
        return ResponseEntity.status(HttpStatus.OK).body(updatedWallet);
    }
}
