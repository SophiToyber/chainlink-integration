package wrapper.chainlink.integration.cryptopayment.service;

import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.GET;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import wrapper.chainlink.exeption.InnerServiceException;
import wrapper.chainlink.integration.cryptopayment.dto.AccountDataDto;
import wrapper.chainlink.integration.cryptopayment.dto.ResponsePageDto;
import wrapper.chainlink.integration.cryptopayment.properties.CryptoPaymentProperties;
import wrapper.chainlink.web.models.responce.TransactionResponseDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class CryptoPaymentCommunicationServiceImpl implements CryptoPaymentCommunicationService {

    private final RestTemplate cryptoPaymentRestTemplate;
    private final CryptoPaymentProperties urls;

    @Override
    public ResponsePageDto<AccountDataDto> getListOfAccountDepositData(int page, int size) {

        try {
            log.info("Request get accounts data to cryptopayment. page: {}, size: {}", page, size);
            ResponsePageDto<AccountDataDto> accountDepositDataDtos = cryptoPaymentRestTemplate
                    .exchange(urls.getUri(), GET, EMPTY, new ParameterizedTypeReference<ResponsePageDto<AccountDataDto>>() {}).getBody();
            log.info("Response from cryptopayment on accounts:"
                    + " total elements = {}", accountDepositDataDtos.getTotalElements());
            return accountDepositDataDtos;
        } catch (ResourceAccessException e) {
            throw new InnerServiceException(String.format("No response from cryptopayment on get accounts data. Error: %s ", e.getMessage()));
        }
    }

    @Override
    public void sendNewDepositTransactionsToProcess(List<TransactionResponseDto> transactionResponseDtos) {
        HttpEntity<List<TransactionResponseDto>> entity = new HttpEntity<>(transactionResponseDtos);

        try {
            log.info("Request post new deposit transaction list to cryptopayment: Transactions list size = {}.",
                    transactionResponseDtos.size());
            HttpStatus httpStatus = cryptoPaymentRestTemplate
                .exchange(urls.getUri(), HttpMethod.POST, entity, Object.class).getStatusCode();
            log.info("Response from cryptopayment on post new deposit transaction list:"
                    + " Status = {}, transactions list size = {}.", httpStatus, transactionResponseDtos.size());
        } catch (ResourceAccessException e) {
            throw new InnerServiceException(String.format("No response from cryptopayment on post new deposit transactions:"
                    + " Transactions list size = %s. Error: %s.", transactionResponseDtos.size(), e.getMessage()));
        }
    }

}