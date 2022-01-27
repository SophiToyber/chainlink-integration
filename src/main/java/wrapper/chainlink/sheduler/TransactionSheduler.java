package wrapper.chainlink.sheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wrapper.chainlink.integration.cryptopayment.service.CryptoPaymentCommunicationService;
import wrapper.chainlink.sheduler.service.TransactionShedulerService;
import wrapper.chainlink.web.models.responce.TransactionResponseDto;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionSheduler {

    private final TransactionShedulerService transactionSchedulerService;
    private final CryptoPaymentCommunicationService cryptoPaymentCommunicationService;

    @Scheduled(fixedDelayString = "${fixed-delay}")
    public void getNewChainlinkDepositTransactions() {
        try {
            log.info("Start sheduling process");
            List<TransactionResponseDto> newDepositTransactionsToProcess = transactionSchedulerService
                .getChainlinkDepositTransactions();
            if (!newDepositTransactionsToProcess.isEmpty()) {
                cryptoPaymentCommunicationService.sendNewDepositTransactionsToProcess(newDepositTransactionsToProcess);
            }
        } catch (Exception e) {
            log.error("Error while deposit transaction scheduler processing: {}.", e.getMessage());
        }
    }

}