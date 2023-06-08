package com.iobuilders.wallet.infrastructure.controller;

import com.iobuilders.wallet.domain.WalletService;
import com.iobuilders.wallet.domain.dto.ErrorResponse;
import com.iobuilders.wallet.domain.dto.WalletDTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Wallets")
public class CreateWalletController {
    private final WalletService walletService;

    @Autowired
    public CreateWalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @Operation(summary = "Create a new wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Wallet created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = WalletID.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid wallet information supplied",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Wallet creation failure",
                    content = @Content)})
    @PostMapping(value = "/wallets")
    public ResponseEntity createWallet(@Valid @RequestBody WalletDTO wallet) {
        WalletID id;
        try {
            id = walletService.create(wallet);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().message(ex.getMessage()).build());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}
