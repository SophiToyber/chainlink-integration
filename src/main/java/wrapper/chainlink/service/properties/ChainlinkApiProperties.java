package wrapper.chainlink.service.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "wrapper.chainlink")
public class ChainlinkApiProperties {
    private Url url;
    private Wallet wallet;
    private Settings settings;
    private Blockchain blockchain;
    private Timeout timeout;

    @Getter
    @Setter
    public static class Url {
        private String balance;
        private String address;
        private String transaction;
        private String withdraw;
        private String withdrawEth;
        private String transactionsList;
    }

    @Getter
    @Setter
    public static class Wallet {
        private String address;
        private String privateKey;
    }

    @Getter
    @Setter
    public static class Blockchain {
        private Double fee;
        private Double minAllowedBalanceSendTransaction;
    }

    @Getter
    @Setter
    public static class Settings {
        private Integer threadPoolSize;
    }

    @Getter
    @Setter
    public static class Timeout {
        private Integer connect;
        private Integer read;
    }

}