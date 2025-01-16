package com.tekarch.AccountServiceMS.Repositories;

import aj.org.objectweb.asm.commons.Remapper;
import com.tekarch.AccountServiceMS.Models.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long> {
    List<Accounts> findByUserId(Long userId);

    boolean existsByAccountNumber(String accountNumber);
    Accounts findByAccountNumber(String accountNumber);

    //  Remapper findByAccountNumber(String accountNumber);
}


