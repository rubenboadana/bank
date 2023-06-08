package com.iobuilders.user.infrastructure.controller;

import com.iobuilders.user.domain.dto.ErrorResponse;
import com.iobuilders.user.domain.UserService;
import com.iobuilders.user.domain.dto.UserDTO;
import com.iobuilders.user.domain.dto.UserID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@Tag(name = "Users")
public class CreateUserController {
    private final UserService userService;

    @Autowired
    public CreateUserController(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity createUser(@Valid @RequestBody UserDTO user) {
        UserID id;
        try {
            id = userService.create(user);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder().message(ex.getMessage()).build());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

}
