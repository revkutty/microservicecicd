package com.tekarch.FundTransferMS.Service;

import com.tekarch.FundTransferMS.DTO.AccountDTO;
import com.tekarch.FundTransferMS.Models.FundTransfer;
import com.tekarch.FundTransferMS.Repository.FundTransferRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class FundTransferServiceImpl implements FundTransferServiceInterface{

    private static final Logger logger = LogManager.getLogger(FundTransferServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private final FundTransferRepository fundTransferRepository;

    public FundTransferServiceImpl(FundTransferRepository fundTransferRepository) {
        this.fundTransferRepository = fundTransferRepository;
    }

    @Value("${app.account-service.url}")
    String accounturl;

    @Value("${app.account-service.update-balance.url}")
    String updatebalance;

    @Override
    public List<FundTransfer> getTransfersBySender(Long senderAccount) {
        return fundTransferRepository.findBySenderAccount(senderAccount);
    }

    @Override
    public List<FundTransfer> getTransfersByReceiver(Long receiverAccount) {
        return fundTransferRepository.findByReceiverAccount(receiverAccount);
    }

    @Override
    public List<FundTransfer> getTransfersByStatus(String status) {
        return fundTransferRepository.findByStatus(status);
    }

    @Override
    public List<FundTransfer> getAllFundTransfer() {
        return fundTransferRepository.findAll();
    }

    @Override
    public Optional<FundTransfer> getFundTransferByTranferId(Long transferId) {
        return fundTransferRepository.findById(transferId);
    }

    @Override
    public List<FundTransfer> getPendingTransfers() {
        return fundTransferRepository.findByStatus("PENDING");
    }

    @Override
    public FundTransfer initiateTransfer(FundTransfer fundTransfers) {
        // Fetch sender and receiver account details
        AccountDTO senderAccount = getAccountById(fundTransfers.getSenderAccount());
        AccountDTO receiverAccount = getAccountById(fundTransfers.getReceiverAccount());

        // Validate sufficient balance
        if (senderAccount.getBalance().compareTo(fundTransfers.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance in sender's account.");
        }

        // Create a new FundTransfer entity
        FundTransfer fundTransfer = new FundTransfer();
        fundTransfer.setSenderAccount(fundTransfers.getSenderAccount());
        fundTransfer.setReceiverAccount(fundTransfers.getReceiverAccount());
        fundTransfer.setAmount(fundTransfers.getAmount());
        fundTransfer.setStatus("COMPLETED");
        fundTransfer.setInitiatedAt(LocalDateTime.now());
        fundTransfer.setCompletedAt(LocalDateTime.now());

        // Update balances in Account Microservice
        updateAccountBalance(senderAccount.getAccountId(), senderAccount.getBalance().subtract(fundTransfers.getAmount()));
        updateAccountBalance(receiverAccount.getAccountId(), receiverAccount.getBalance().add(fundTransfers.getAmount()));

        // Save the fund transfer record
        return fundTransferRepository.save(fundTransfer);
    }


    private AccountDTO getAccountById(Long accountId) {
     //   String url = "http://localhost:8082/api/account/" + accountId;
        System.out.println("Get Account ID URL in FundTransfer:" + accounturl + accountId);
        return restTemplate.getForObject(accounturl + accountId, AccountDTO.class);
    }

    private void updateAccountBalance(Long accountId, BigDecimal newBalance) {
     //   String url = "http://localhost:8082/api/account/" + accountId + "/update-balance";
          String url = accounturl + accountId + updatebalance;
        // Create the AccountDTO object with the updated balance
        AccountDTO accountDTO = new AccountDTO(accountId, newBalance);

        // Make the PUT request using RestTemplate
        restTemplate.put(url, accountDTO);

    }
}


