package com.amplify.common.models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferAmountRequest {
    @NotNull
    private String senderAccountNumber;
    @NotNull
    private String recipientsAccountNumber;
    @NotNull
    private BigDecimal amount;
}
