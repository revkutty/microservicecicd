package com.tekarch.FundTransferMS.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountDTO {

        private Long accountId;
        private Long userId;
        private String accountNumber;
        private String accountType;
        private BigDecimal balance;
        private String currency;
        private LocalDateTime createdAt = LocalDateTime.now();


        public AccountDTO(Long accountId, BigDecimal balance) {
                this.accountId = accountId;
                this.balance = balance;
        }

}







