package com.amplify.user.models.entities;

import com.amplify.common.enums.AccountStatus;
import com.amplify.common.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "CUSTOMER_ACCOUNT_TBL")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Column(nullable = false, unique = true)
    private String accountNumber;
    private String fullName;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private BigDecimal balance;
    private LocalDate lastTransactionDate;
}
