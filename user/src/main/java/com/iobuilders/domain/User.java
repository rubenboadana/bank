package com.iobuilders.domain;

import com.iobuilders.domain.dto.UserID;
import jakarta.validation.constraints.NotNull;

public record User(@NotNull UserID id, @NotNull String userName, @NotNull String password,
                   @NotNull String name,
                   @NotNull String surname) {

}