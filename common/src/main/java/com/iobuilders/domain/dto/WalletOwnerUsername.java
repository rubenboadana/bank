package com.iobuilders.domain.dto;

import com.iobuilders.domain.bus.query.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Builder
@Getter
@Setter
public class WalletOwnerUsername implements Response {
    private final String value;
}
