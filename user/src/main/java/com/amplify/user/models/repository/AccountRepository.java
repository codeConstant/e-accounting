package com.amplify.user.models.repository;


import com.amplify.user.models.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByAccountNumber(String accountNumber);
}
