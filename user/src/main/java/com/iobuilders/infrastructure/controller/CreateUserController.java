package com.iobuilders.infrastructure.controller;

import com.iobuilders.domain.bus.command.CommandBus;
import com.iobuilders.domain.command.CreateUserCommand;
import com.iobuilders.domain.dto.User;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
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
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid user information supplied",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "User creation failure",
                    content = @Content)})
    @PostMapping(value = "/users/register")
    public CompletableFuture<ResponseEntity<Void>> createUser(@Valid @RequestBody User user) throws InterruptedException {
        log.info("CreateUserController:createUser: POST /users/register received");

        return commandBus.send(new CreateUserCommand(user.id(), user.userName(), user.password(), user.name(), user.surname()))
                .thenApply(response -> {
                    log.info("CreateUserController:createUser: POST /users/register dispatched");
                    return ResponseEntity.status(HttpStatus.CREATED).build();
                });
    }
}
