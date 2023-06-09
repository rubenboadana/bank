package com.iobuilders.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;

public record User(@JsonIgnore Long id, @NotNull String userName, @NotNull String password, @NotNull String name,
                   @NotNull String surname) {
}
