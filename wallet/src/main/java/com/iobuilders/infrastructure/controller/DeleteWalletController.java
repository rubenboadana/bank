package com.iobuilders.infrastructure.controller;

import com.iobuilders.domain.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Wallets")
public class DeleteWalletController {
    private final WalletService walletService;

    @Autowired
    public DeleteWalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @Operation(summary = "Delete a wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Wallet not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Wallet deletion failure",
                    content = @Content)})
    @DeleteMapping(value = "/wallets/{id}")
    public ResponseEntity deleteWallet(@PathVariable(value = "id") Long id) {
        walletService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
