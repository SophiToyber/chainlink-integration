package wrapper.chainlink.integration.cryptopayment.uri;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import wrapper.chainlink.integration.cryptopayment.properties.CryptoPaymentProperties;

@RequiredArgsConstructor
@Component
public class CryptoPaymentUriBuilder {

    private final CryptoPaymentProperties cryptoPaymentProperties;

    public URI getAccountProcessedDataByIsoUri(int page, int size) {
        return getAccountsDataUri(cryptoPaymentProperties.getPath()
            .getAccount()
            .getChainlink()
            .getProcessedData(), page, size);
    }

    public URI getAccountsDataUri(String path, int page, int size) {
        return UriComponentsBuilder.fromUriString(cryptoPaymentProperties.getUri())
            .path(path)
            .queryParam("page", page)
            .queryParam("size", size)
            .build()
            .encode()
            .toUri();
    }

    public URI getTransactionNewDepositListUri() {
        return UriComponentsBuilder.fromUriString(cryptoPaymentProperties.getUri())
            .path(cryptoPaymentProperties.getPath()
                .getTransaction()
                .getChainlink()
                .getNewDepositList())
            .build()
            .encode()
            .toUri();
    }

}
