package com.amplify.common.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceUpdateRequest {
    @NotNull
    private String accountNumber;
    @NotNull
    private BigDecimal amount;
}
