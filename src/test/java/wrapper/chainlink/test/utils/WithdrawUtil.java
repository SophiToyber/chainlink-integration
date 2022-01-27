package wrapper.chainlink.test.utils;

import wrapper.chainlink.service.entity.WithdrawRequest;
import wrapper.chainlink.web.models.responce.TransactionResponseDto;

public class WithdrawUtil {

    public static final String WITHDRAW_URL = "/api/wrapper/withdraw/";
    public static final String TRANSACTION_HASH = "0xced92e8ea522d4d10e18463c31cbff3e48d5d99a34791c0cfc60fb5d4011a17c";
    public static final String FROM = "0x5f21E53799360342c244FDA5FE72C6a6E9804f18";
    public static final String TO = "0xdb45165B4475AB9f0ED6c1143d2C7B3Ad6Cf2DE8";
    public static final Double AMOUNT = 0.1;
    public static final Integer EXPECTED_BLOCK_NUMBER = 7963562;

    public static WithdrawRequest getDefaultWithdrawRequestDto() {
        return WithdrawRequest.builder()
            .from("0x5f21E53799360342c244FDA5FE72C6a6E9804f18")
            .to("0xdb45165B4475AB9f0ED6c1143d2C7B3Ad6Cf2DE8")
            .privateKey("03a4f927537bd45f2dd26927c63f96f0bb47bd7db12ef4eb208b24c0db754867")
            .quantity(0.1)
            .build();
    }

    public static TransactionResponseDto getDefaultTransactionResponse() {
        return TransactionResponseDto.builder()
            .transactionHash(TRANSACTION_HASH)
            .from(FROM)
            .to(TO)
            .amount(AMOUNT)
            .blockNumber(EXPECTED_BLOCK_NUMBER)
            .build();
    }
}
