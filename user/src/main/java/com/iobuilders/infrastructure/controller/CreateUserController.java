package com.iobuilders.infrastructure.controller;

import com.iobuilders.application.handler.CreateUserCommand;
import com.iobuilders.domain.bus.command.CommandBus;
import com.iobuilders.domain.dto.User;
import com.iobuilders.domain.dto.UserID;
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

import java.util.concurrent.CompletableFuture;

@RestController
@Tag(name = "Users")
public class CreateUserController {
    private final CommandBus commandBus;

    @Autowired
    public CreateUserController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserID.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid user information supplied",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "User creation failure",
                    content = @Content)})
    @PostMapping(value = "/users/register")
    public CompletableFuture<ResponseEntity<Void>> createUser(@Valid @RequestBody User user) throws InterruptedException {
        return commandBus.send(new CreateUserCommand(user.userName(), user.password(), user.name(), user.surname()))
                .thenApply(response -> ResponseEntity.status(HttpStatus.CREATED).build());
    }
}
