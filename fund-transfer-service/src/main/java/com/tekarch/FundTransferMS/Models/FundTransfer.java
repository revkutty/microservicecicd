package com.tekarch.FundTransferMS.Models;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tekarch.FundTransferMS.DTO.AccountDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundTransfer {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long transferId;

    /*    @ManyToOne
        @JoinColumn(name = "sender_account_id", nullable = false)
        private AccountDTO senderAccount;

        @ManyToOne
        @JoinColumn(name = "receiver_account_id", nullable = false)
        private AccountDTO receiverAccount;
*/

        @Column(name = "sender_account_id", nullable = false)
        private Long senderAccount;

        @Column(name = "receiver_account_id", nullable = false)
        private Long receiverAccount;

        @Column(nullable = false)
        private BigDecimal amount;

        @Column(length = 20)
        private String status = "PENDING";

        private LocalDateTime initiatedAt = LocalDateTime.now();
        private LocalDateTime completedAt;


}

