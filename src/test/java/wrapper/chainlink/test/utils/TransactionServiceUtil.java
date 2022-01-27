package wrapper.chainlink.test.utils;

import wrapper.chainlink.web.models.responce.BalanceResponseDto;
import wrapper.chainlink.web.models.responce.TransactionResponseDto;

public class TransactionServiceUtil {

    public static Double EXPECTED_BALANCE = 100.0;
    public static String EXPECTED_ADDRESS = "0xe18b0Bf2a1BD5a32aE42d7009d5Bb2AdbEe713c5";
    public static String EXPECTED_PRIVATE_KEY = "4d98add50d0875440471788096170eba7fd96e0bed27a1c2135701390cf3511e";
    public static String TRANSACTION_HASH = "0xef434ad4267e2a05f1a4f65f4601c89bee12e0a1cf2580087a7780fca3398520";
    public static String TO = "0xdb45165b4475ab9f0ed6c1143d2c7b3ad6cf2de8";
    public static String FROM = "0x5f21e53799360342c244fda5fe72c6a6e9804f18";
    public static Double AMOUNT = 0.0001;
    public static Integer BLOCK_NUMBER = 7963647;

    public static TransactionResponseDto getDefaultTransactionResponseDto() {
        return TransactionResponseDto.builder()
            .transactionHash(TRANSACTION_HASH)
            .to(TO)
            .from(FROM)
            .amount(AMOUNT)
            .blockNumber(BLOCK_NUMBER)
            .build();
    }

    public static BalanceResponseDto getDefaultBalance() {
        return BalanceResponseDto.builder().amount(EXPECTED_BALANCE).build();
    }

}
