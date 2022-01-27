package wrapper.chainlink.integration.cryptopayment.service;

import java.util.List;

import wrapper.chainlink.integration.cryptopayment.dto.AccountDataDto;
import wrapper.chainlink.integration.cryptopayment.dto.ResponsePageDto;
import wrapper.chainlink.web.models.responce.TransactionResponseDto;

public interface CryptoPaymentCommunicationService {

    ResponsePageDto<AccountDataDto> getListOfAccountDepositData(int page, int size);

    void sendNewDepositTransactionsToProcess(List<TransactionResponseDto> transactionResponseDtoList);
}
