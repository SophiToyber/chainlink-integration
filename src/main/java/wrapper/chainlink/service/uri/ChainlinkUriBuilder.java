package wrapper.chainlink.service.uri;

import java.net.URI;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import wrapper.chainlink.service.properties.ChainlinkApiProperties;

@Service
@RequiredArgsConstructor
public class ChainlinkUriBuilder {

    private final ChainlinkApiProperties chainlinkProperties;

    public URI getAddressUri() {
        return UriComponentsBuilder.fromUriString(chainlinkProperties.getUrl()
            .getAddress())
            .build()
            .encode()
            .toUri();
    }

    public URI getBalanseUri() {
        return UriComponentsBuilder.fromUriString(chainlinkProperties.getUrl()
            .getBalance())
            .path(chainlinkProperties.getWallet()
                .getAddress())
            .build()
            .encode()
            .toUri();
    }

    public URI getBalanseUri(String address) {
        return UriComponentsBuilder.fromUriString(chainlinkProperties.getUrl()
            .getBalance())
            .path(address)
            .build()
            .encode()
            .toUri();
    }

    public URI getTransactionUri(String hash) {
        return UriComponentsBuilder.fromUriString(chainlinkProperties.getUrl()
            .getTransaction())
            .path(hash)
            .build()
            .encode()
            .toUri();
    }

    public URI getWithdrawUri() {
        return UriComponentsBuilder.fromUriString(chainlinkProperties.getUrl()
            .getWithdraw())
            .build()
            .encode()
            .toUri();
    }

    public URI getWithdrawEthereumUri() {
        return UriComponentsBuilder.fromUriString(chainlinkProperties.getUrl()
            .getWithdrawEth())
            .build()
            .encode()
            .toUri();
    }

    public URI getTransactionsListUri(String address, Integer fromBlock) {
        return UriComponentsBuilder.fromUriString(chainlinkProperties.getUrl()
            .getTransactionsList())
            .path(String.format("%s/%d", address, fromBlock))
            .build()
            .encode()
            .toUri();
    }

}