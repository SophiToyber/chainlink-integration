package wrapper.chainlink.sheduler.process;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;
import wrapper.chainlink.exeption.InnerServiceException;
import wrapper.chainlink.integration.cryptopayment.dto.AccountDataDto;
import wrapper.chainlink.service.ChainlinkCommunicationService;
import wrapper.chainlink.service.entity.WithdrawRequest;
import wrapper.chainlink.service.properties.ChainlinkApiProperties;
import wrapper.chainlink.web.models.responce.TransactionResponseDto;

@Slf4j
public class CustomerChainlinkProcessService implements Callable<List<TransactionResponseDto>> {

    private final ChainlinkCommunicationService chainlinkCommunicationService;

    private final Double minAllowedBalanceSendTransaction;
    private final Double fee;

    private final String accountWalletAddress;
    private final String accountWalletPrivateKey;
    private final Integer accountLastProcessedTransactionBlock;

    private final String cryptexWalletAddress;
    private final String cryptexWalletPrivateKey;

    public CustomerChainlinkProcessService(AccountDataDto accountDataDto,
            ChainlinkCommunicationService chainlinkService, ChainlinkApiProperties chainlinkBlockchainProperties) {

        this.accountWalletAddress = accountDataDto.getWalletAddress();
        this.accountWalletPrivateKey = accountDataDto.getWalletKey();
        this.accountLastProcessedTransactionBlock = accountDataDto.getBlockNumber();

        this.chainlinkCommunicationService = chainlinkService;

        this.minAllowedBalanceSendTransaction = chainlinkBlockchainProperties.getBlockchain()
            .getMinAllowedBalanceSendTransaction();
        this.fee = chainlinkBlockchainProperties.getBlockchain()
            .getFee();

        this.cryptexWalletAddress = chainlinkBlockchainProperties.getWallet()
            .getAddress();
        this.cryptexWalletPrivateKey = chainlinkBlockchainProperties.getWallet()
            .getPrivateKey();
    }

    @Override
    public List<TransactionResponseDto> call() {
        try {
            Double balance = chainlinkCommunicationService.getWalletBalance(accountWalletAddress)
                .getAmount();
            if (balance.compareTo(minAllowedBalanceSendTransaction) < 0) {
                return Collections.emptyList();
            }

            TransactionResponseDto feeResult = chainlinkCommunicationService.withdrawETH(
                    new WithdrawRequest(cryptexWalletAddress, cryptexWalletPrivateKey, accountWalletAddress, fee));

            if (Objects.isNull(feeResult)) {
                throw new InnerServiceException(
                        String.format("Error while processing send fee to address = %s", accountWalletAddress));
            }
            log.info("Succesfull transfer fee ethereum on account: {}", accountWalletAddress);

            TransactionResponseDto withdrawResult = chainlinkCommunicationService.withdraw(
                    new WithdrawRequest(accountWalletAddress, accountWalletPrivateKey, cryptexWalletAddress, balance));

            if (Objects.isNull(withdrawResult)) {
                throw new InnerServiceException(
                        String.format("Error while processing send balance to general wallet from address = %s.",
                                accountWalletAddress));
            }
            log.info("Succesfull transfer LINK to main address: {}", withdrawResult.getTransactionHash());
            return chainlinkCommunicationService.getWalletTransactionsList(accountWalletAddress,
                    accountLastProcessedTransactionBlock);

        } catch (Exception e) {
            log.error("Error while processing account deposit transactions: address = {}. Error: {}.",
                    accountWalletAddress, e.getMessage());
            return Collections.emptyList();
        }
    }

}