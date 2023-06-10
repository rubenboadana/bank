package com.iobuilders.infrastructure.controller;

import com.iobuilders.domain.UserService;
import com.iobuilders.domain.dto.JwtToken;
import com.iobuilders.domain.dto.LoginRequest;
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
@Tag(name = "Users")
public class LoginUserController {
    private final UserService userService;

    @Autowired
    public LoginUserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Login a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User logged in",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtToken.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid user information supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal failure",
                    content = @Content)})
    @PostMapping(value = "/users/login")
    public ResponseEntity loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtToken token = userService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
