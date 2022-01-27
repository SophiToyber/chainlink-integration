package wrapper.chainlink.service;

import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static wrapper.chainlink.service.transformer.WithdrawTransformer.transform;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wrapper.chainlink.exeption.InnerServiceException;
import wrapper.chainlink.service.entity.WithdrawRequest;
import wrapper.chainlink.service.properties.ChainlinkApiProperties;
import wrapper.chainlink.service.uri.ChainlinkUriBuilder;
import wrapper.chainlink.web.models.responce.AddressResponseDto;
import wrapper.chainlink.web.models.responce.BalanceResponseDto;
import wrapper.chainlink.web.models.responce.TransactionResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChainlinkCommunicationServiceImpl implements ChainlinkCommunicationService {

    private final ChainlinkUriBuilder uri;
    private final RestTemplate chainlinkApiRestTemplate;
    private final ChainlinkApiProperties chainlinkProperties;

    @Override
    public BalanceResponseDto getWalletBalance(String address) {
        try {
            log.info("Request get Chainlink balance. Address = {}", address);
            BalanceResponseDto balanceResponse = chainlinkApiRestTemplate
                .exchange(uri.getBalanseUri(address), GET, EMPTY, BalanceResponseDto.class)
                .getBody();
            log.info("Response from Chainlink blockchain on get balance request."
                    + " Address = {}, {}.", address, balanceResponse);
            return balanceResponse;
        } catch (ResourceAccessException e) {
            throw new InnerServiceException(String.format("No response from Chainlink blockchain on get balance request."
                    + " Address = %s. Error: %s ", address, e.getMessage()));
        }
    }

    @Override
    public BalanceResponseDto getWalletBalance() {
        try {
            log.info("Request get Chainlink balance on main address");
            BalanceResponseDto balanceResponse = chainlinkApiRestTemplate
                .exchange(uri.getBalanseUri(), GET, EMPTY, BalanceResponseDto.class)
                .getBody();
            log.info("Response from Chainlink blockchain on get balance request."
                    + " Address = main address, {}.", balanceResponse);
            return balanceResponse;
        } catch (ResourceAccessException e) {
            throw new InnerServiceException(String.format("No response from Chainlink blockchain on get balance request."
                    + " Address = main address. Error: %s ", e.getMessage()));
        }
    }

    @Override
    public AddressResponseDto getWalletAddress() {
        try {
            log.info("Request get Chainlink address");
            AddressResponseDto addressResponce = chainlinkApiRestTemplate
                .exchange(uri.getAddressUri(), GET, EMPTY, AddressResponseDto.class)
                .getBody();
            log.info("Response from Chainlink blockchain on get address. Address = {}", addressResponce.getAddress());
            return addressResponce;
        } catch (ResourceAccessException e) {
            throw new InnerServiceException(String.format("No response from Chainlink blockchain on get address."
                    + " Error: %s ", e.getMessage()));
        }
    }

    @Override
    public TransactionResponseDto getWalletTransaction(String hash) {
        try {
            log.info("Request get Chainlink transaction");
            TransactionResponseDto transactionResponce = chainlinkApiRestTemplate
                .exchange(uri.getTransactionUri(hash), GET, EMPTY, TransactionResponseDto.class)
                .getBody();
            return transactionResponce;
        } catch (ResourceAccessException e) {
            throw new InnerServiceException(String.format("No response from Chainlink blockchain on get transaction."
                    + " Error: %s ", e.getMessage()));
        }
    }

    @Override
    public TransactionResponseDto withdraw(WithdrawRequest withdrawRequest) {
        HttpEntity<WithdrawRequest> httpEntity = new HttpEntity<WithdrawRequest>(
                transform(withdrawRequest,
                          chainlinkProperties.getWallet().getAddress(),
                          chainlinkProperties.getWallet().getPrivateKey()));
        try {
            log.info("Request get Chainlink transaction");
            TransactionResponseDto transactionResponce = chainlinkApiRestTemplate
                .exchange(uri.getWithdrawUri(), POST, httpEntity, TransactionResponseDto.class)
                .getBody();
            log.info("Response from Chainlink blockchain on withdraw request."
                    + " TransactionResponce = {}", transactionResponce.toString());
            return transactionResponce;
        } catch (ResourceAccessException e) {
            throw new InnerServiceException(String.format("No response from Chainlink blockchain on get transaction."
                    + " Error: %s ", e.getMessage()));
        }
    }

    @Override
    public TransactionResponseDto withdrawETH(WithdrawRequest withdrawRequest) {
        HttpEntity<WithdrawRequest> httpEntity = new HttpEntity<WithdrawRequest>(withdrawRequest);
        try {
            log.info("Request get Ethereum transaction");
            TransactionResponseDto transactionResponce = chainlinkApiRestTemplate
                .exchange(uri.getWithdrawEthereumUri(), POST, httpEntity, TransactionResponseDto.class)
                .getBody();
            log.info("Response from Chainlink blockchain on withdraw Ethereum request."
                    + " TransactionResponce = {}", transactionResponce.toString());
            return transactionResponce;
        } catch (ResourceAccessException e) {
            throw new InnerServiceException(String.format("No response from Ehtereum blockchain on get transaction."
                    + " Error: %s ", e.getMessage()));
        }
    }

    @Override
    public List<TransactionResponseDto> getWalletTransactionsList(String address, Integer fromBlock) {
        try {
            log.info("Request get Chainlink transaction");
            List<TransactionResponseDto> transactionResponce = chainlinkApiRestTemplate
                .exchange(uri.getTransactionsListUri(address, fromBlock), GET, EMPTY,
                        new ParameterizedTypeReference<List<TransactionResponseDto>>() {}).getBody();
            return transactionResponce;
        } catch (ResourceAccessException e) {
            throw new InnerServiceException(String.format("No response from Chainlink blockchain on get transaction."
                    + " Error: %s ", e.getMessage()));
        }
    }

}