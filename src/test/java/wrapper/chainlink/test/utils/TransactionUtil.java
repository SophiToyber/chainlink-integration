package wrapper.chainlink.test.utils;

import wrapper.chainlink.web.models.responce.TransactionResponseDto;

public class TransactionUtil {
    public static final String TRANSACTION_URL = "/api/wrapper/transaction?hash=0x597a7ff88ce267d235d624c1d6c2c8c755d6aab27e8671655048b5b6aabd55af";
    public static final String TRANSACTION_HASH = "0x597a7ff88ce267d235d624c1d6c2c8c755d6aab27e8671655048b5b6aabd55af";
    public static final String TO = "0xe18b0bf2a1bd5a32ae42d7009d5bb2adbee713c5";
    public static final String FROM = "0x5f21E53799360342c244FDA5FE72C6a6E9804f18";
    public static final Integer EXPECTED_BLOCK_NUMBER = 7598679;
    public static final Double EXPECTED_AMOUNT = 0.79;

    public static TransactionResponseDto getDefaultTransactionResponse() {
        return TransactionResponseDto.builder()
            .transactionHash(TRANSACTION_HASH)
            .to(TO)
            .from(FROM)
            .amount(EXPECTED_AMOUNT)
            .blockNumber(EXPECTED_BLOCK_NUMBER)
            .build();
    }

}
