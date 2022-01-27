package wrapper.chainlink.service;

import java.util.List;

import wrapper.chainlink.service.entity.WithdrawRequest;
import wrapper.chainlink.web.models.responce.AddressResponseDto;
import wrapper.chainlink.web.models.responce.BalanceResponseDto;
import wrapper.chainlink.web.models.responce.TransactionResponseDto;

public interface ChainlinkCommunicationService {
    BalanceResponseDto getWalletBalance(String address);

    BalanceResponseDto getWalletBalance();

    AddressResponseDto getWalletAddress();

    TransactionResponseDto getWalletTransaction(String hash);

    TransactionResponseDto withdraw(WithdrawRequest withdrawRequest);

    TransactionResponseDto withdrawETH(WithdrawRequest withdrawRequest);

    List<TransactionResponseDto> getWalletTransactionsList(String address, Integer fromBlock);

}
