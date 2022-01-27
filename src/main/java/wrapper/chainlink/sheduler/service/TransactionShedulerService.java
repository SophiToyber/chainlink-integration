package wrapper.chainlink.sheduler.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wrapper.chainlink.integration.cryptopayment.dto.AccountDataDto;
import wrapper.chainlink.integration.cryptopayment.dto.ResponsePageDto;
import wrapper.chainlink.integration.cryptopayment.service.CryptoPaymentCommunicationService;
import wrapper.chainlink.service.ChainlinkCommunicationService;
import wrapper.chainlink.service.properties.ChainlinkApiProperties;
import wrapper.chainlink.sheduler.process.CustomerChainlinkProcessService;
import wrapper.chainlink.web.models.responce.TransactionResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionShedulerService {

    private final ChainlinkCommunicationService chainlinkService;
    private final CryptoPaymentCommunicationService cryptoPaymentCommunicationService;
    private final ChainlinkApiProperties chainlinkBlockchainProperties;

    private ExecutorService executor;

    @Value("${wrapper.chainlink.settings.deposit-account-data-page-size}")
    private int depositAccountDataPageSize;

    @PostConstruct
    public void init() {
        this.executor = Executors.newFixedThreadPool(chainlinkBlockchainProperties.getSettings()
            .getThreadPoolSize());
    }

    public List<TransactionResponseDto> getChainlinkDepositTransactions() {
        log.info("Request to chainlink trasaction sheduler service");
        Function<AccountDataDto, Callable<List<TransactionResponseDto>>> callableMapper = accountDataDto -> new CustomerChainlinkProcessService(
                accountDataDto, chainlinkService, chainlinkBlockchainProperties);
        return getNewTransactions(callableMapper);
    }

    private List<TransactionResponseDto> getNewTransactions(
            Function<AccountDataDto, Callable<List<TransactionResponseDto>>> callableMapper) {
        ResponsePageDto<AccountDataDto> accountsData = cryptoPaymentCommunicationService.getListOfAccountDepositData(1,
                depositAccountDataPageSize);

        List<TransactionResponseDto> chainlinkTransactions = getTransactionsFromBlockchain(callableMapper,
                accountsData.getContent());
        List<TransactionResponseDto> newTransactions = new ArrayList<>(chainlinkTransactions);

        for (int page = 1; page < accountsData.getTotalPages(); page++) {
            accountsData = cryptoPaymentCommunicationService.getListOfAccountDepositData(page,
                    depositAccountDataPageSize);
            chainlinkTransactions = getTransactionsFromBlockchain(callableMapper, accountsData.getContent());
            newTransactions.addAll(chainlinkTransactions);
        }

        return newTransactions;
    }

    private List<TransactionResponseDto> getTransactionsFromBlockchain(
            Function<AccountDataDto, Callable<List<TransactionResponseDto>>> callableMapper,
            List<AccountDataDto> accountsData) {

        List<Future<List<TransactionResponseDto>>> accountTransactionFutures = accountsData.stream()
            .map(accountDataDto -> {
                Callable<List<TransactionResponseDto>> transactionService = callableMapper.apply(accountDataDto);
                return executor.submit(transactionService);
            })
            .collect(Collectors.toList());

        List<TransactionResponseDto> transactionResponseDtos = new ArrayList<>();
        accountTransactionFutures.forEach(accountTransactionFuture -> {
            try {
                List<TransactionResponseDto> results = accountTransactionFuture.get();
                transactionResponseDtos.addAll(results);
            } catch (Exception e) {
                log.error("Error while processing future result for chainlink accountDataDto. Error: {}.",
                        e.getMessage());
            }
        });
        return transactionResponseDtos;
    }

}