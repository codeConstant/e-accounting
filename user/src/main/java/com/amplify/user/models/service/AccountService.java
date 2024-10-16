package com.amplify.user.models.service;

import com.amplify.common.enums.AccountStatus;
import com.amplify.common.enums.Messages;
import com.amplify.common.errors.DetailedError;
import com.amplify.common.errors.NotFoundError;
import com.amplify.common.models.BalanceUpdateRequest;
import com.amplify.common.models.TransferAmountRequest;
import com.amplify.user.models.entities.UserAccount;
import com.amplify.user.models.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public UserAccount saveAccount(UserAccount account) {
        return accountRepository.save(account);
    }

    public UserAccount findById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new NotFoundError("User Id Missing"));
    }

    public List<UserAccount> findByAll() {
        return accountRepository.findAll();
    }


    public UserAccount findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new NotFoundError("Account Number Not Found"));
    }

    public UserAccount balanceUpdate(BalanceUpdateRequest balanceUpdateRequest) {
        UserAccount account = accountRepository.findByAccountNumber(balanceUpdateRequest.getAccountNumber())
                .orElseThrow(() -> new NotFoundError(Messages.SENDER_ACC_NOT_FOUND));
        account.setBalance(account.getBalance().add(balanceUpdateRequest.getAmount()));
        account.setLastTransactionDate(LocalDate.now());
        return accountRepository.save(account);
    }

    @Transactional
    public boolean transferAmount(TransferAmountRequest transferAmountRequest ) {

        UserAccount sender = accountRepository.findByAccountNumber(transferAmountRequest.getSenderAccountNumber())
                .orElseThrow(() -> new NotFoundError(Messages.SENDER_ACC_NOT_FOUND));

        UserAccount recipient = accountRepository.findByAccountNumber(transferAmountRequest.getRecipientsAccountNumber())
                .orElseThrow(() -> new NotFoundError(Messages.RECIPIENT_ACC_NOT_FOUND));

        if (AccountStatus.ACTIVE != sender.getAccountStatus() ||
                AccountStatus.ACTIVE != recipient.getAccountStatus()) {
            throw new DetailedError(HttpStatus.BAD_REQUEST, Messages.BOTH_ACC_MUST_BE_ACTIVE);
        }

        if (sender.getBalance().compareTo(transferAmountRequest.getAmount()) < 0) {
            throw new NotFoundError(Messages.INSUFFICIENT_BALANCE);
        }

        sender.setBalance(sender.getBalance().subtract(transferAmountRequest.getAmount()));
        sender.setLastTransactionDate(LocalDate.now());
        recipient.setBalance(recipient.getBalance().add(transferAmountRequest.getAmount()));
        recipient.setLastTransactionDate(LocalDate.now());

        accountRepository.save(sender);
        accountRepository.save(recipient);

        return true;
    }
}
