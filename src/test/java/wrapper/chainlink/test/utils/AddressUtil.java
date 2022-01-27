package wrapper.chainlink.test.utils;

import wrapper.chainlink.web.models.responce.AddressResponseDto;

public class AddressUtil {
    public static final String ADDRESS_URL = "/api/chainlink/address";
    public static final String EXPECTED_ADDRESS = "0x1c183871D62c419B0dE8e509020E262644C83bD2";
    public static final String PRIVATE_KEY = "0x889cdd4ac5924f373bdbbbf9ab58042aa64f9535e35b50c12f313fa934184b9a";

    public static AddressResponseDto getDefaultAddressResponse() {
        return AddressResponseDto.builder()
            .address(EXPECTED_ADDRESS)
            .privateKey(PRIVATE_KEY)
            .build();
    }
}
