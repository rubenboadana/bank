package com.iobuilders.user.infrastructure.controller;

import com.iobuilders.user.domain.UserService;
import com.iobuilders.user.domain.dto.User;
import com.iobuilders.user.domain.dto.UserID;
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
@Tag(name = "Users")
public class UpdateUserController {
    private final UserService userService;

    @Autowired
    public UpdateUserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Update the user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserID.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid user information supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "User update failure",
                    content = @Content)})
    @PutMapping(value = "/users/{id}")
    public ResponseEntity updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User user) {
        User updatedUser = userService.update(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }
}
