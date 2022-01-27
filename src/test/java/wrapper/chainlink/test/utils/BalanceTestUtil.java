package wrapper.chainlink.test.utils;

import wrapper.chainlink.web.models.responce.BalanceResponseDto;

public class BalanceTestUtil {

    public static final String BALANCE_URL = "/api/chainlink/balance";
    public static final Double EXPECTED_BALANCE = 599.5;

    public static BalanceResponseDto getDefaulChainlinkAccountBalanceResult() {
        return BalanceResponseDto.builder()
            .amount(599.5)
            .build();
    }

}
