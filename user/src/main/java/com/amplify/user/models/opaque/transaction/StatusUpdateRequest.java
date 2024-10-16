package com.amplify.user.models.opaque.transaction;

import com.amplify.common.enums.AccountStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateRequest {
    @NotNull
    private Long id;
    @NotNull
    private AccountStatus accountStatus;
}
