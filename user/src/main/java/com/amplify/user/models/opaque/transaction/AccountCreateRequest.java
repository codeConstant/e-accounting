package com.amplify.user.models.opaque.transaction;

import com.amplify.common.enums.AccountStatus;
import com.amplify.common.enums.AccountType;
import com.amplify.common.enums.Messages;
import com.amplify.common.errors.DetailedError;
import com.amplify.user.models.entities.User;
import com.amplify.user.models.entities.UserAccount;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

@Data
public class AccountCreateRequest {
    @NotNull
    private Long userId;
    @NotNull
    private String accountNumber;
    @NotNull
    private AccountType accountType;
    @NotNull
    private BigDecimal balance;

    public UserAccount buildAccount(User user){

        if(balance.compareTo(BigDecimal.ZERO) < 0){
            throw new DetailedError(HttpStatus.NOT_ACCEPTABLE, Messages.BALANCE_SHOULD_GREATER_THEN_ZERO);
        }

        return UserAccount.builder()
                .fullName(user.getFullName())
                .dateOfBirth(user.getDateOfBirth())
                .balance(balance)
                .accountType(accountType)
                .accountStatus(AccountStatus.INACTIVE)
                .accountNumber(accountNumber)
                .userId(user.getId())
                .build();
    }
}
