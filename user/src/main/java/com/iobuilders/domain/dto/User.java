package com.iobuilders.domain.dto;

import jakarta.validation.constraints.NotNull;

public record User(@NotNull String id, @NotNull String userName, @NotNull String password, @NotNull String name,
                   @NotNull String surname) {
}
