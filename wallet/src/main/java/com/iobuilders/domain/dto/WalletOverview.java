package com.iobuilders.domain.dto;

import com.iobuilders.domain.bus.query.Response;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WalletOverview implements Response {
    private double quantity;
    private List<WalletTransaction> transactions;
}
