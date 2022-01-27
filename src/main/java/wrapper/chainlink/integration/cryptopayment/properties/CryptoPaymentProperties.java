package wrapper.chainlink.integration.cryptopayment.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ms.crypto.payment")
public class CryptoPaymentProperties {

    private String uri;
    private Path path;
    private Timeout timeout;

    @Getter
    @Setter
    public static class Path {
        private Transaction transaction;
        private Account account;

        @Getter
        @Setter
        public static class Transaction {
            private Chainlink chainlink;

            @Getter
            @Setter
            public static class Chainlink {
                private String newDepositList;
            }
        }

        @Getter
        @Setter
        public static class Account {
            private Chainlink chainlink;

            @Getter
            @Setter
            public static class Chainlink {
                private String processedData;
            }
        }

    }

    @Getter
    @Setter
    public static class Timeout {
        private Integer connect;
        private Integer read;
    }

}
